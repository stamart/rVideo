package pl.orangeretail.gofiber.server.methods;

import BazaDanych.Parametry_Pracy;
import pl.orangeretail.gofiber.session.AntyBruteForce.BruteForceMap;
import BazaDanych.Stale_Slownikowe;
import BazaDanych.WynikSerweraSerwer;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.time.DateUtils;
import pl.gofiber.ftth.Dictionary;
import pl.orangeretail.gofiber.error.BruteForceError;
import pl.orangeretail.gofiber.error.VersionError;
import pl.orangeretail.gofiber.session.*;
import pl.orangeretail.gofiber.server.logi.*;
import pl.orangeretail.gofiber.server.objects.*;
import pl.orangeretail.gofiber.session.AntyBruteForce.AntyBruteForce;

public class Login extends ToolBox {
    
    LoginResponse response = new LoginResponse();
    
    private int userId;
    private int accountType;
    private int deviceID;
    private int loginType;
    private int idKampa = 0;
    
    private boolean isUserLogedIn = false;
    
    private boolean isAccountTypeBasic = false;
    private boolean isAccountTypePro = false;
    private boolean isAccountTypeHunter = false;
    
    private String sessionId;
    private String ipAddress;
    private String imei;
    private String login;
    private String hash;                 
    private String osVersion;
    private String email;
    private String appVersion;
    
    public LoginResponse zaloguj(LoginRequest request, HttpServletRequest servletRequest) {
        login = Anty_Iniect(request.getLogin());
        hash = Anty_Iniect(request.getHash());                 
        imei = Anty_Iniect(request.getImei());
        System.out.println("imei: " + imei);
        ipAddress = servletRequest.getRemoteAddr();
        
        loginType = request.getLoginType();
        email = Anty_Iniect(request.getEmail());
        appVersion = Anty_Iniect(request.getAppVersion());
        
        try {
            checkVersion(appVersion);
            checkBruteForce(login);
            if(czyDanePoprawne(request)){            
                zalogujUzytkownika(request);
            }
        } catch (VersionError e) {
            response.setError(version);
        } catch (BruteForceError e) {
            new AntyBruteForce().sendNotification(request, login);
            response.setError(lock);
        }
        saveSessionData();
        return response;                
    }
    
    private boolean czyDanePoprawne(LoginRequest request) {
        if(isNullOrEmpty(request.getLogin()) || isNullOrEmpty(request.getHash())){            
            response.setError("Brak loginu albo hasła");
            return false;
        }
        
        return true;
    }          

    private void zalogujUzytkownika(LoginRequest request) {
        String query = "";

        switch(loginType){
            case Dictionary.SOCIAL_MEDIA_ACCOUNT_TYPE_FACEBOOK: {
                query = "SELECT us_id as idUsera, us_email as email, us_rodzaj_konta as rodzajKonta FROM users WHERE us_facebook_id = '" + login + "'";
                break;
            }
            case Dictionary.SOCIAL_MEDIA_ACCOUNT_TYPE_GOOGLEPLUS: {
                query = "SELECT us_id as idUsera, us_email as email, us_rodzaj_konta as rodzajKonta FROM users WHERE us_googleplus_id = '" + login + "'";
                break;
            }
            default: {
                query = "SELECT us_id as idUsera, us_rodzaj_konta as rodzajKonta FROM users WHERE (us_login = '" + login + "' OR us_email = '" + login + "') AND us_passwd = '" + hash + "'";
            }
        }

        System.out.println("login query : " + query);
                
        WynikSerweraSerwer wynikTmp = Baza_Query(Get_Polaczenie_Z_Baza_Danych(), query);
        if(wynikTmp.Get_Kod_Bledu() != Stale_Slownikowe.Operacja_Zakonczona_Sukcesem){
            response.setError("Błąd pobierania danych o użytkowniku");
        }
        userId = wynikTmp.Daj_Int_Po_Nazwie(0, "idUsera");
        accountType = setAccountType(wynikTmp);
        deviceID = getDeviceID();
        
        if(wynikTmp.Get_Ilosc_Wierszy() == 0){
            System.out.println("Niepoprawny login lub hasło: " + hash);
            logStatus("fail: " + login + " - bad credentials",  Dictionary.STATUS_LOGON_ERROR);
            switch(new AntyBruteForce().countAttempts(login, BruteForceMap.BLOCKING_COUNT)){
                case 2: {response.setError(warning);break;}
                case 3: {
                    response.setError(lock);
                    new AntyBruteForce().sendNotification(request, login);
                    break;
                }
                default:  response.setError("Niepoprawny login lub hasło: ");
            }
         }else{
            switch(loginType){
                case Dictionary.SOCIAL_MEDIA_ACCOUNT_TYPE_FACEBOOK:
                case Dictionary.SOCIAL_MEDIA_ACCOUNT_TYPE_GOOGLEPLUS: {
                    String userEmail = wynikTmp.Daj_String_Po_Nazwie(0, "email");
                    if(!userEmail.equalsIgnoreCase(email)){
                        ORA_Start_Transakcji(Get_Polaczenie_Z_Baza_Danych());

                        String queryUpdate = "UPDATE users SET us_email = '" + email + "' WHERE us_id = " + userId;
                        WynikSerweraSerwer wynikUpdate = Baza_Update(Get_Polaczenie_Z_Baza_Danych(), queryUpdate);
                        System.out.println(queryUpdate);

                        if (wynikUpdate.Get_Kod_Bledu() != Stale_Slownikowe.Operacja_Zakonczona_Sukcesem) {
                            response.setError("Błąd aktualizacji email");
                        }
                        ORA_Koniec_Transakcji(Get_Polaczenie_Z_Baza_Danych());
                    }
                    break;
                }
            }
            createSessionID();
            response.setSessionId(sessionId);
            response.setAccountType(accountType);
            isUserLogedIn = true;
            BruteForceMap.getInstance().removeValue(login);
            logStatus("success: " + login,Dictionary.STATUS_LOGON);
        }

    }

    private int getDeviceID() {
        String query = "SELECT * FROM menedzer_urzadzen WHERE mu_imei = '" + imei + "' and mu_user_id = " + userId;

        WynikSerweraSerwer wynikTmp = Baza_Query(Get_Polaczenie_Z_Baza_Danych(), query);
        if (wynikTmp.Get_Kod_Bledu() != Stale_Slownikowe.Operacja_Zakonczona_Sukcesem) {
            response.setError("Błąd pobierania danych o użytkowniku");
        }
        
        if(wynikTmp.Get_Ilosc_Wierszy() == 0){
            deviceID = 0;
            osVersion = "nie znana";
        }else{
            deviceID = wynikTmp.Daj_Int_Po_Nazwie(0, "mu_id");
            osVersion = wynikTmp.Daj_String_Po_Nazwie(0, "mu_wersja_systemu_operacyjnego");
        }
        return deviceID;
    }

    private void createSessionID() {
        SessionIDGenerator sessionIdGenerator = new SessionIDGenerator(this, userId, imei, ipAddress, appVersion, accountType);
        sessionId = sessionIdGenerator.generateSessionID();
        System.out.println("IPAddress= " + ipAddress);
    }
    
    private void logStatus(String description, int status) {
        applicationLogs = new ApplicationLogs();
        applicationLogs.setIdSession(getIdSession(sessionId));
        applicationLogs.setTypID(status);
        applicationLogs.setUserId(userId);
        applicationLogs.setImei(imei);
        applicationLogs.setDeviceID(deviceID);
        applicationLogs.setAppType(osVersion);
        applicationLogs.setIpAddress(ipAddress);
        applicationLogs.setAppVersion(appVersion);
        applicationLogs.setDescription("LOGIN: "+ description);
        
        ConnectionsList.getInstance().addValue(userId, applicationLogs);
        new Logi().logEvent(applicationLogs);
    }
    
    private void saveSessionData() {
        if(userId == 0){return;}
        Date data = new Date();
        SessionData sessionData = new SessionData();
        sessionData.setUserID(userId);
        sessionData.setAccountType(accountType);
        sessionData.setDeviceID(deviceID);
        sessionData.setSessionID(sessionId);
        sessionData.setLogin(login);
        sessionData.setHash(hash);
        sessionData.setImei(imei);
        sessionData.setIPAddress(ipAddress);
        sessionData.setDateLogin(data);
        sessionData.setIsAccountTypePro(isAccountTypePro);
        sessionData.setIsAccountTypeBasic(isAccountTypeBasic);
        sessionData.setIsAccountTypeHunter(isAccountTypeHunter);
        sessionData.setIsUserLogedIn(isUserLogedIn);
        if(accountType == Dictionary.ACCOUNT_TYPE_HUNTER){
            sessionData.setIdKampa(idKampa);
            sessionData.setIdKampaFilled(idKampa != 0);

        }
        sessionData.setExpirationDate(DateUtils.addMinutes(data, SessionStatus.SESSION_LENGHT));
        
        SessionMap.getInstance().addValue(sessionId, sessionData);
    }

    private int setAccountType(WynikSerweraSerwer wynikTmp) {
        int wynik = wynikTmp.Daj_Int_Po_Nazwie(0, "rodzajKonta");
        switch(wynik){
            case Dictionary.ACCOUNT_TYPE_SWIETLIK:      {isAccountTypeBasic  = true    ;break;}
            case Dictionary.ACCOUNT_TYPE_SWIETLIK_PLUS: {isAccountTypePro    = true    ;break;}
            case Dictionary.ACCOUNT_TYPE_HUNTER:        {isAccountTypeHunter = true    ; getKampaID(); break;}
        }
       return wynik;
    }

    private void getKampaID() {
        String query = "select * from "+ Parametry_Pracy.Get_Config().dajSchematOracleLogistyka()+".users_orange where uo_id_gofiber = " + userId + " and uo_id_opl > 0";
        
        WynikSerweraSerwer wynikTmp = Baza_Query(Get_Polaczenie_Z_Baza_Danych(), query);
        if (wynikTmp.Get_Kod_Bledu() != Stale_Slownikowe.Operacja_Zakonczona_Sukcesem) {
            response.setError("Błąd pobierania danych o użytkowniku");
        }
        
        if(wynikTmp.Get_Ilosc_Wierszy() >0 ){
            idKampa = wynikTmp.Daj_Int_Po_Nazwie(0, "uo_id_opl");
            response.setIdKampaFilled(true);
        }
    }

}
