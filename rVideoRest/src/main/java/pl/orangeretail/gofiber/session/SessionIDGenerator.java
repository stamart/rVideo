/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.orangeretail.gofiber.session;

import BazaDanych.Environment;
import BazaDanych.Stale_Slownikowe;
import BazaDanych.WynikSerweraSerwer;
import java.security.SecureRandom;
import java.util.UUID;
import pl.orangeretail.gofiber.server.methods.Login;
import pl.orangeretail.gofiber.server.objects.StandardResponse;

/**
 *
 * @author staszek.martowicz
 */
public class SessionIDGenerator extends  Environment {

    
    public StandardResponse response = new StandardResponse();
    
    private SecureRandom random = new SecureRandom();
    
    private String sessionId;
    private String IPAddress;
    private String imei;
    private String appVersion;
    
    private int userId;
    private int iloscWiersszy = 1;
    private int accountType;
    
    public SessionIDGenerator(String sessionId) {
        this.sessionId = sessionId;
    }
    
    public SessionIDGenerator(Environment env, int us_id, String imei, String IP, String version,int accountType){
        setEnvironmentFull(env);
        sessionId = UUID.randomUUID().toString();
        this.imei = imei;
        this.userId = us_id;
        this.IPAddress = IP;
        this.appVersion = version;
        this.accountType = accountType;
    }

   
    public String generateSessionID() {
     
        while (iloscWiersszy == 1) {

            sessionId = UUID.randomUUID().toString();

            String query = "SELECT DS_SESSION_ID FROM DANE_SESJI where DS_SESSION_ID='" + sessionId + "'";

            System.out.println("####query:" + query);

            WynikSerweraSerwer wynikTmp = Baza_Query(Get_Polaczenie_Z_Baza_Danych(), query);
            if (wynikTmp.Get_Kod_Bledu() != Stale_Slownikowe.Operacja_Zakonczona_Sukcesem) {
                System.out.println("błąd zapisywania kodu do bazy danych");
            }
            iloscWiersszy = wynikTmp.Get_Ilosc_Wierszy();

        }
        
        System.out.println("WYGENEROWANY SESSIONID: ---=== " +  sessionId + "===---");
        ORA_Start_Transakcji(Get_Polaczenie_Z_Baza_Danych());
        saveKey();
        ORA_Koniec_Transakcji(Get_Polaczenie_Z_Baza_Danych());            
        return sessionId;
    }
        
    private void saveKey() {
        String query = "INSERT INTO DANE_SESJI (DS_SESSION_ID, DS_USER_ID, DS_IMEI, DS_IP_ADDRESS, DS_RODZAJ_KONTA, DS_WERSJA_APLIKACJI, DS_DATA_UTWORZENIA, DS_DATA_WAZNOSCI) VALUES ('"
                + sessionId + "','" + userId + "','" + imei + "','" + IPAddress+"'," + accountType + ",'" + appVersion+ "',SYSDATE, SYSDATE + 1/48)";
        
        System.out.println("####query:" + query);
                
        WynikSerweraSerwer wynikTmp = Baza_Update(Get_Polaczenie_Z_Baza_Danych(), query);
        if (wynikTmp.Get_Kod_Bledu() != Stale_Slownikowe.Operacja_Zakonczona_Sukcesem) {
            System.out.println("błąd zapisywania sesji do bazy danych");
            response.setError("Błąd generowania sesji");
        }

    }
    
    public int getIdSession() {
        
        String query = "select ds_id from dane_sesji where DS_SESSION_ID = '"+ sessionId + "'";
        System.out.println("login query : " + query);
                
        WynikSerweraSerwer wynikTmp = Baza_Query(Get_Polaczenie_Z_Baza_Danych(), query);
        if(wynikTmp.Get_Kod_Bledu() != Stale_Slownikowe.Operacja_Zakonczona_Sukcesem){
            response.setError("Błąd pobierania danych o użytkowniku");
        }

        return wynikTmp.Daj_Int_Po_Nazwie(0, "ds_id");
    }
    
    
}
