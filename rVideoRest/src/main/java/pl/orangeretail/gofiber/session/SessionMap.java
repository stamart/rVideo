/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.orangeretail.gofiber.session;

import pl.orangeretail.gofiber.error.SessionError;
import BazaDanych.Environment;
import BazaDanych.Stale_Slownikowe;
import BazaDanych.WynikSerweraSerwer;
import java.util.concurrent.ConcurrentHashMap;
import pl.orangeretail.gofiber.server.logi.ApplicationLogs;
import pl.orangeretail.gofiber.server.logi.ConnectionsList;

/**
 *
 * @author staszek.martowicz
 */
public class SessionMap extends  Environment{
    
    private String osVersion;
    private String imei;
    
    private final ConcurrentHashMap <String, SessionData> session;
    
    private static SessionMap instance;
    
    public static SessionMap getInstance() {
        if(instance==null) { instance = new SessionMap(); }
        return instance;
    }
    
    private SessionMap(){
        setObiketWywolanyPrzezManageraPolaczen(true);
        session = new ConcurrentHashMap<>();
    }
    
    public void addValue(String key, SessionData value){
        session.put(key, value);
        System.out.println("############### SessionMap object added:" + session.get(key));
        ORA_Start_Transakcji(Get_Polaczenie_Z_Baza_Danych());
        saveSession(key,value);
        ORA_Koniec_Transakcji(Get_Polaczenie_Z_Baza_Danych());            
    }
    
    public SessionData getValue(String key){
        System.out.println("############### SessionData getValue (sessionId) :" + key);
        return session.get(key);
    }
    
    public void removeValue(String endKey){
        session.remove(endKey);
        System.out.println("############### Koniec Sesji object removed:" + endKey);
    }

    public void sessionIdVerification(String key) throws SessionError {
        if (!session.containsKey(key)) {
            System.out.println("############### Bad Session ID Niepoprawny klusz sesji");
            throw new SessionError("Bad Session ID", "Niepoprawny klusz sesji");
        }
    }

    private void saveSession(String key, SessionData value) {
        
        int logedIn         = (value.isIsUserLogedIn())         ? 1 : 0;
        int proUser         = (value.isIsAccountTypePro())      ? 1 : 0;
        int tokenOK         = (value.isIsTokenValidated())      ? 1 : 0;
        int devOK           = (value.isIsDeviceRegistered())    ? 1 : 0;
        int hunterUser      = (value.isIsAccountTypeHunter())   ? 1 : 0;
        int basicUser       = (value.isIsAccountTypeBasic())    ? 1 : 0;
        int idKampaFilled   = (value.isIdKampaFilled())         ? 1 : 0;
        
        String update = "UPDATE DANE_SESJI SET "
                + "ds_mu_id= " + value.getDeviceID() + ","
                + "ds_rodzaj_konta = " + value.getAccountType() + ","
                + "ds_data_potwierdzenia=SYSDATE,"
                + "ds_czy_zalogowany ="+logedIn+","
                + "ds_czy_typ_konta_pro ="+proUser+","
                + "ds_czy_typ_konta_basic ="+basicUser+","
                + "ds_czy_typ_konta_hunter ="+hunterUser+","
                + "ds_czy_posiada_kampa_id ="+idKampaFilled+","
                + "ds_kampa_id = " + value.getIdKampa() + ","
                + "ds_czy_token_ok ="+tokenOK+","
                + "ds_czy_urzadzenie_ok ="+devOK+","
                + "ds_data_waznosci=SYSDATE + "+ SessionStatus.SESSION_DB_LENGHT + " "
                + "WHERE ds_session_id = '" + key + "'";
        
        System.out.println("####query:" + update);
                
        WynikSerweraSerwer wynikTmp = Baza_Update(Get_Polaczenie_Z_Baza_Danych(), update);
        if (wynikTmp.Get_Kod_Bledu() != Stale_Slownikowe.Operacja_Zakonczona_Sukcesem) {
            System.out.println("błąd zapisywania sesji do bazy danych");
        }

    }
    public void loadSession() {
        String query = "select * from dane_sesji where ds_data_potwierdzenia >= (sysdate-1/48)";
        
        System.out.println("####query:" + query);
                
        WynikSerweraSerwer wynikTmp = Baza_Query(Get_Polaczenie_Z_Baza_Danych(), query);
        if (wynikTmp.Get_Kod_Bledu() != Stale_Slownikowe.Operacja_Zakonczona_Sukcesem) {
            System.out.println("błąd zapisywania sesji do bazy danych");                                
        }

        SessionData sessionData;        
        for (int i = 0; i < wynikTmp.Get_Ilosc_Wierszy(); i++) {
            String sessionId = wynikTmp.Daj_String_Po_Nazwie(i, "DS_SESSION_ID");
            
            sessionData = new SessionData();
            sessionData.setSessionID(sessionId);
            sessionData.setImei(wynikTmp.Daj_String_Po_Nazwie(i, "DS_IMEI"));
            sessionData.setDateLogin(wynikTmp.Daj_Date_Po_Nazwie(i, "DS_DATA_UTWORZENIA"));
            sessionData.setDateLastActivity(wynikTmp.Daj_Date_Po_Nazwie(i, "DS_DATA_POTWIERDZENIA"));
            sessionData.setExpirationDate(wynikTmp.Daj_Date_Po_Nazwie(i, "DS_DATA_WAZNOSCI"));
            sessionData.setUserID(wynikTmp.Daj_Int_Po_Nazwie(i, "DS_USER_ID"));
            sessionData.setDeviceID(wynikTmp.Daj_Int_Po_Nazwie(i, "DS_MU_ID"));
            sessionData.setIPAddress(wynikTmp.Daj_String_Po_Nazwie(i, "DS_IP_ADDRESS"));
            sessionData.setAppVersion(wynikTmp.Daj_String_Po_Nazwie(i, "DS_WERSJA_APLIKACJI"));
            sessionData.setAccountType(wynikTmp.Daj_Int_Po_Nazwie(i, "DS_RODZAJ_KONTA"));
            sessionData.setIdKampa(wynikTmp.Daj_Int_Po_Nazwie(i, "DS_KAMPA_ID"));
            setFlags(i, wynikTmp,sessionData);
            session.put(sessionId, sessionData);
            loadLogs(sessionId, sessionData);
        }
    }
    
    public void loadLogs(String sessionId, SessionData sessionData) {
        int userId = sessionData.getUserID();
        int deviceId = sessionData.getDeviceID();
        getImei(deviceId);
        ApplicationLogs applicationLogs = new ApplicationLogs();
        applicationLogs.setSessionId(sessionId);
        applicationLogs.setUserId(userId);
        applicationLogs.setDeviceID(deviceId);
        applicationLogs.setIpAddress(sessionData.getIPAddress());
        applicationLogs.setImei(imei);
        applicationLogs.setAppVersion(sessionData.getAppVersion());
        applicationLogs.setAppType(osVersion);
        ConnectionsList.getInstance().addValue(userId, applicationLogs);
        
    }
    
    private void getImei(int deviceId) {
        String query = "SELECT * FROM menedzer_urzadzen WHERE mu_id = " + deviceId ;

        WynikSerweraSerwer wynikTmp = Baza_Query(Get_Polaczenie_Z_Baza_Danych(), query);
        if (wynikTmp.Get_Kod_Bledu() != Stale_Slownikowe.Operacja_Zakonczona_Sukcesem) {
            System.out.println("Błąd pobierania danych o użytkowniku");
        }
        
        if(wynikTmp.Get_Ilosc_Wierszy() == 0){
            imei = "brak";
            osVersion = "nie znana";
        }else{
            imei = wynikTmp.Daj_String_Po_Nazwie(0, "mu_imei");
            osVersion = wynikTmp.Daj_String_Po_Nazwie(0, "MU_WERSJA_SYSTEMU_OPERACYJNEGO");
            
        }
    }

    private SessionData setFlags(int i, WynikSerweraSerwer wynikTmp, SessionData sessionData) {
        int logedIn         = wynikTmp.Daj_Int_Po_Nazwie(i, "ds_czy_zalogowany");
        int proUser         = wynikTmp.Daj_Int_Po_Nazwie(i, "ds_czy_typ_konta_pro");
        int hunterUser      = wynikTmp.Daj_Int_Po_Nazwie(i, "ds_czy_typ_konta_hunter");
        int basicUser       = wynikTmp.Daj_Int_Po_Nazwie(i, "ds_czy_typ_konta_basic");
        int kampaIdFilled   = wynikTmp.Daj_Int_Po_Nazwie(i, "ds_czy_posiada_kampa_id");
        int tokenOK         = wynikTmp.Daj_Int_Po_Nazwie(i, "ds_czy_token_ok");
        int devOK           = wynikTmp.Daj_Int_Po_Nazwie(i, "ds_czy_urzadzenie_ok");
        
        
        sessionData.setIsUserLogedIn(logedIn != 0);
        sessionData.setIsAccountTypePro(proUser != 0);
        sessionData.setIsAccountTypeBasic(basicUser != 0);
        sessionData.setIsAccountTypeHunter(hunterUser != 0);
        sessionData.setIsTokenValidated(tokenOK != 0);
        sessionData.setIsDeviceRegistered(devOK != 0);
        sessionData.setIdKampaFilled(kampaIdFilled != 0);
        return sessionData;
    }

    public void getVersion() {
        String query = "select * from aktualne_wersje";
        
        WynikSerweraSerwer wynikTmp = Baza_Query(Get_Polaczenie_Z_Baza_Danych(), query);
        if (wynikTmp.Get_Kod_Bledu() != Stale_Slownikowe.Operacja_Zakonczona_Sukcesem) {
            System.out.println("błąd pobierania wersji z bazy danych");
        }
        
        SessionStatus.minimumRequiedVersion = wynikTmp.Daj_Int_Po_Nazwie(0, "AW_ID_MINIMALNEJ_WERSJI");
        SessionStatus.actualVersion = wynikTmp.Daj_Int_Po_Nazwie(0, "AW_ID_AKTUALNEJ_WERSJI");
        SessionStatus.ALLOWED_VERSION = SessionStatus.allwedOldVersion();
        SessionStatus.BANNED_VERSION = SessionStatus.bannedVersion();
        System.out.println(" Aktualna Wersja Klienta: " + SessionStatus.actualVersion);    
        System.out.println("Minimalna Wersja Klienta: " + SessionStatus.minimumRequiedVersion);    
    }
}

            