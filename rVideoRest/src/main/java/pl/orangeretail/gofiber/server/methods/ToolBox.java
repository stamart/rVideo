package pl.orangeretail.gofiber.server.methods;

import BazaDanych.Environment;
import BazaDanych.Stale_Slownikowe;
import BazaDanych.WynikSerweraSerwer;
import java.util.Date;
import org.apache.commons.codec.binary.Base64;
import pl.gofiber.ftth.Dictionary;
import pl.orangeretail.gofiber.error.BruteForceError;
import pl.orangeretail.gofiber.error.HunterErrror;
import pl.orangeretail.gofiber.error.VersionError;
import pl.orangeretail.gofiber.session.SessionData;
import pl.orangeretail.gofiber.server.logi.ApplicationLogs;
import pl.orangeretail.gofiber.server.logi.ConnectionsList;
import pl.orangeretail.gofiber.session.AntyBruteForce.AntyBruteForce;
import pl.orangeretail.gofiber.session.AntyBruteForce.BruteForceData;
import pl.orangeretail.gofiber.session.AntyBruteForce.BruteForceMap;
import pl.orangeretail.gofiber.session.SessionIDGenerator;
import pl.orangeretail.gofiber.session.SessionMap;
import pl.orangeretail.gofiber.session.SessionStatus;

public class ToolBox extends Environment{

    public final String messageSql = "wystąpił problem z dodaniem transakcji. Spróbój ponownie później";
    public String version = "Twoja wersja Aplikacji jest już nieobsługiwana. Pobierz nową wersję ze sklepu z aplikacjami";
    public String lock = "Wykryliśmy trzy nieudane próby logowania Twoje IP zostało zablokowane na " + BruteForceMap.BLOCKING_LENGHT + " minut";
    public String warning = "Wykryliśmy dwie niepoprawne próby logowania. Ponowna nieudana próba zablokuje twoje IP na " + BruteForceMap.BLOCKING_LENGHT + " minut.";
    
    public static String Tokenlock = "Wykryliśmy trzy nieudane próby Twoja sesja została zablokowana";
    public static String Tokenwarning =  "Wykryliśmy dwie niepoprawne próby . Ponowna nieudana próba zablokuje twoją aplikację";
    public static String smsLimit = "Osiągnięto limit wysyłanych sms na godzinę";

    public BruteForceData bruteForceData;
    public SessionData sessionData;
    public ApplicationLogs applicationLogs;
    
    public int getUserId(String sessionId){
        sessionData = SessionMap.getInstance().getValue(sessionId);
        return sessionData.getUserID();
    }
    
    public SessionData getSessionData(String sessionId){
        sessionData = SessionMap.getInstance().getValue(sessionId);
        return sessionData;
    }

    public void completeApplicationLogs(int elementId, int userId) {
        applicationLogs = ConnectionsList.getInstance().getValue(userId);
        applicationLogs.setElementID(elementId);
        System.out.println("############### elementId: " + elementId);
        ConnectionsList.getInstance().addValue(userId, applicationLogs);
    }
    
    public int getNotificationsAmount(int userId) {
        String query = "select * from powiadomienia where po_user_id = " + userId +" and po_status = " + Dictionary.NOTIFICATION_STATUS_NEW;
        
        System.out.println("query: " + query);
        
        WynikSerweraSerwer wynikTmp = Baza_Query(Get_Polaczenie_Z_Baza_Danych(), query);
        if(wynikTmp.Get_Kod_Bledu() != Stale_Slownikowe.Operacja_Zakonczona_Sukcesem){
            System.out.println("Błąd pobierania ilości powiadomień");
        }
        return wynikTmp.Get_Ilosc_Wierszy();
    }
    
    public void checkVersion(String appVersion) throws VersionError{
        if(SessionStatus.BANNED_VERSION.contains(appVersion)){
          throw new VersionError("VersionCheck: Access Denied", "Sesja przerwana - Versja minimalna niezgodna");
        }
    }
    
    public void checkBruteForce(String login) throws BruteForceError{
        if(new AntyBruteForce().checkAttempts(login)){
          throw new BruteForceError("BruteForce check: Access Denied", "Sesja przerwana - AtakBruteforce");
        }
    }
    
    public String setTokenError(String sessionId, int attemps) {
        String response;
        switch(new AntyBruteForce().countAttempts(sessionId, attemps)){
                case 2: {response = Tokenwarning;break;}
                case 3: {response = Tokenlock;break;}
                default:  response = ("Kod niepoprawny!");
            }
        return response;
    }
    
    public void endSession(String sessionId){
        Date data = new Date();
        sessionData.setDateLastActivity(data);
        sessionData.setExpirationDate(data);
        SessionMap.getInstance().addValue(sessionId, sessionData);
    }
    
    public int getIdSession(String sessionId) {
        SessionIDGenerator sessionIDGenerator = new SessionIDGenerator(sessionId);
        sessionIDGenerator.setObiketWywolanyPrzezManageraPolaczen(true);
        return sessionIDGenerator.getIdSession();
    }
    
    public void sendNotification(String description, String subject, int type, int userId) throws Exception{
         String query = "insert into powiadomienia (po_typ, po_status, po_temat, po_tresc, po_user_id)"
                + "VALUES("
                + type +","
                + Dictionary.NOTIFICATION_STATUS_NEW+","
                + " '"+ subject + "',"
                + " '"+ description +"',"
                + userId 
                + ")";

        WynikSerweraSerwer update = Baza_Update(Get_Polaczenie_Z_Baza_Danych(), query);

        if (update.Get_Kod_Bledu() != Stale_Slownikowe.Operacja_Zakonczona_Sukcesem) {
            throw new Exception("Błąd generowania powiadomienia dla klienta");
        }
    }
    
    public void checkIfIsHunter(SessionData sessionData) throws HunterErrror {
        if(sessionData.getAccountType() == Dictionary.ACCOUNT_TYPE_HUNTER){
            throw new HunterErrror("Acces Denied", "Account type not allowed");
        }
    }
    
    public boolean isNotNullOrEmpty(String text){
        return !isNullOrEmpty(text);
    }
    
    public boolean isNullOrEmpty(String text){
        return text == null || text.isEmpty();
    }    
    
    protected byte[] szyfrujDaneWBase64(byte[] dane){
        return Base64.encodeBase64(dane);        
    }
    
    protected byte[] rozszyfrujDaneWBase64(byte[] dane){
        return Base64.decodeBase64(dane);
    }
    
    public static String removeLastComa(StringBuilder text){
        return removeLastComa(text.toString());
    }
    
    private static String removeLastComa(String text){
        return text.replaceAll(",+\\s$", "");
    }
}
