package pl.orangeretail.gofiber.session;

import java.util.ArrayList;

public class SessionStatus {
    public static final int SESSION_LENGHT = 30 ;
    public static final String SESSION_DB_LENGHT = SESSION_LENGHT+"/1440";
    
    public static final int STATUS_UNAUTHORIZED = 401;
    public static final int STATUS_REQUEST_TIMEOUT = 408;
    public static final int STATUS_FORBIDDEN = 506;
    public static final int STATUS_OK = 200;
    public static final int STATUS_UPGRADE_INFO = 209;
    public static final int STATUS_UPGRADE_REQUIRED = 507;
    public static final int STATUS_VERSION_IS_NULL = 508;

    public static ArrayList<String> ALLOWED_VERSION;
    public static ArrayList<String> BANNED_VERSION;

    public static int minimumRequiedVersion = 0;
    public static int actualVersion = 0;

    public static ArrayList bannedVersion(){
        ArrayList<String> array= new ArrayList<>();
        array.add("20161007.1200");
        array.add("20161006.1600");
        array.add("20161006.1100");
        array.add("20161005.2300");
        array.add("20161005.2000");
        array.add("20161005.1300");
        array.add("20161011.1800");
        System.out.println("Pobrano listę zabroninych wersji: " +array.toString());
        return array;
    }

    public static ArrayList<String> allwedOldVersion() {
        ArrayList<String> array= new ArrayList<>();
        array.add("20161011.1899"); //usunac
        System.out.println("Pobrano listę dozwolonych wersji: "+array.toString());
        return array;
    }
    
   
}
