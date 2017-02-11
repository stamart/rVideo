/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.orangeretail.gofiber.session;

import pl.orangeretail.gofiber.error.SessionError;
import java.util.Date;
import pl.gofiber.ftth.Dictionary;
import pl.orangeretail.gofiber.server.methods.ToolBox;

/**
 *
 * @author staszek.martowicz
 */
public class SessionCheck extends ToolBox{
     
        
    public  static void sessionCheckIP(SessionData sessiondata, String ip) throws SessionError{
        if (!sessiondata.getIPAddress().equals(ip)) {
            System.out.println("############### Session Check: IP Address not equals Niezgodny adress IP. Sesja Przerwana");
            throw new SessionError("Session Check: IP Address not equals", "Niezgodny adress IP. Sesja Przerwana");
        }
    }
    
    public static void sessionIsActive(SessionData sessiondata) throws SessionError{
        Date sysDate = new Date();
        Date expDate = sessiondata.getExpirationDate();
        System.out.println("############### sysDate: " + sysDate);
        System.out.println("############### expDate: " + expDate);
        if(sysDate.after(expDate)){
            System.out.println("############### Session Check: Session Expired, Twoja Sesja wygasla");
            throw new SessionError("Session Check: Session Expired", "Twoja Sesja wygasla!");
        }
    }

    public static void allowSearch(SessionData sessionData) throws SessionError {
        if(sessionData.getAccountType() == Dictionary.ACCOUNT_TYPE_SWIETLIK){
            System.out.println("############### Session Check: brak uprawnień PRO");
            throw new SessionError("Session Type Check: Access Denied", "Sesja przerwana - Niepoprawny typ sesji");
        }else if(sessionData.getAccountType() != Dictionary.ACCOUNT_TYPE_SWIETLIK_PLUS && sessionData.getAccountType() != Dictionary.ACCOUNT_TYPE_HUNTER){
            System.out.println("############### Session Check: brak uprawnień PRO");
            throw new SessionError("Session Type Check: Access Denied", "Sesja przerwana - Niepoprawny typ sesji");
        }else if(!sessionData.isIdKampaFilled() && sessionData.getAccountType() == Dictionary.ACCOUNT_TYPE_HUNTER){
            System.out.println("############### Session Check: Brak Kampa ID");
            throw new SessionError("Session Type Check: Access Denied", "Sesja przerwana - Brak Kampa ID");
            
        }
    }
    
    public static void verifyUserLoggedIn(SessionData sessionData) throws SessionError {
        if(!sessionData.isIsUserLogedIn()){
            System.out.println("############### Session Check: Użytkownik niezalogowany");
            throw new SessionError("Session Login Check: Access Denied", "Sesja przerwana - Użytkownik niezalogowany");
        }
    }
    
    public static void verifyTokenValidated(SessionData sessionData) throws SessionError {
        if(!sessionData.isIsTokenValidated()){
            System.out.println("############### Session Check:  Token niezweryfikowany");
            throw new SessionError("PasswordRecovery: Access Denied", "Sesja przerwana - Token niezweryfikowany");
        }
    }
    
    public static void verifyDeviceRegistered(SessionData sessionData) throws SessionError {
        if(!sessionData.isIsDeviceRegistered()){
            System.out.println("############### Session Check:  Urządzenie niezarejestrowane");
            throw new SessionError("Session Device Check: Access Denied", "Sesja przerwana - Urządzenie niezarejestrowane");
        }
    }

    public static void verifyMinimalVersion(String appVersion) throws SessionError {
        //============================ Magic for backward compatibilty 
        if(SessionStatus.BANNED_VERSION.contains(appVersion)|| SessionStatus.ALLOWED_VERSION.contains(appVersion)){
                return;
        }
        //============================ Magic for backward compatibilty 
        
        if (SessionStatus.actualVersion == 0 || SessionStatus.minimumRequiedVersion == 0) {
            System.out.println("############### Session Check: BŁĄD POBIERANIE WERSJI Z BAZY DANYCH");
            throw new SessionError("VersionCheck: Access Denied", "Session Check: BŁĄD POBIERANIE WERSJI Z BAZY DANYCH");
        }
        int versionLevel= getVersionLevel(appVersion);
        if (versionLevel < SessionStatus.minimumRequiedVersion) {
            System.out.println("############### Session Check:  Token niezweryfikowany");
            throw new SessionError("VersionCheck: Access Denied", "Sesja przerwana - Versja minimalna niezgodna");
        }
        System.out.println("############### Minimal Version Check: " + SessionStatus.minimumRequiedVersion + "  klient version: " + versionLevel);

    }
    
    public static int verifyActualVersion(String appVersion){
        if (SessionStatus.actualVersion == 0 || SessionStatus.minimumRequiedVersion == 0) {
            System.out.println("############### Session Check: BŁĄD POBIERANIE WERSJI Z BAZY DANYCH");
        }
        int versionLevel= getVersionLevel(appVersion);
        
        if (versionLevel < SessionStatus.actualVersion) {
            System.out.println("############### Actual Version Check: " + SessionStatus.actualVersion + "klient version: " + versionLevel);
            return SessionStatus.STATUS_UPGRADE_INFO;
        }
        System.out.println("###############  Actual Version Check: " + SessionStatus.actualVersion + "  klient version: " + versionLevel);

        return SessionStatus.STATUS_OK;
    }

    private static int getVersionLevel(String appVersion){
        int versionLevel = 0;
        try {
            versionLevel = Integer.parseInt(appVersion.replaceAll("\\.", ""));

        } catch (NumberFormatException e) {
            System.out.println("VersionCheck: Access Denied Sesja przerwana - błąd parsowania wersji");
        }
        return versionLevel;
    } 
}
