/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.orangeretail.gofiber.session.AntyBruteForce;

import BazaDanych.Stale_Slownikowe;
import BazaDanych.WynikSerweraSerwer;
import java.util.Date;
import org.apache.commons.lang3.time.DateUtils;
import pl.gofiber.ftth.Dictionary;
import pl.orangeretail.gofiber.server.methods.ToolBox;
import pl.orangeretail.gofiber.server.objects.LoginRequest;

/**
 *
 * @author staszek
 */
public class AntyBruteForce extends ToolBox {
    
    private int bruteForceCount;
               
    public int countAttempts(String key, int attempts) {
        bruteForceData = loadFromMap(key);
        System.out.println("#################### BruteForce login: " + bruteForceData.getBruteForceCount());
        bruteForceData.setBruteForceCount(bruteForceData.getBruteForceCount() + 1);
        BruteForceMap.getInstance().addValue(key, bruteForceData);
    
        bruteForceCount = bruteForceData.getBruteForceCount();

        Date data = new Date();

        if(bruteForceCount > attempts){
            bruteForceData.setBruteForceBlockTime(DateUtils.addMinutes(data, BruteForceMap.BLOCKING_LENGHT));
            bruteForceData.setBruteForceLock(true);
            System.out.println("#################### Brutforce Blokada konta do: " + bruteForceData.getBruteForceBlockTime());
            BruteForceMap.getInstance().addValue(key, bruteForceData);
        }
        return bruteForceCount;
    }
            
    public boolean checkAttempts(String key){
        if((bruteForceData = BruteForceMap.getInstance().getValue(key)) == null){
            System.out.println("#################### CheckBruteforce new: OK");
        }else{
            bruteForceData = BruteForceMap.getInstance().getValue(key);
            if(bruteForceData.isBruteForceLock()){
                Date brutDate = bruteForceData.getBruteForceBlockTime();
                Date sysDate = new Date();
                if(sysDate.before(brutDate)){
                    System.out.println("#################### Bruteforce: BruteForce Detected, Account Blocked for one hour");
                    bruteForceData.setBruteForceCount(bruteForceData.getBruteForceCount() + 1);
                    BruteForceMap.getInstance().addValue(key, bruteForceData);
                    return true;
                }else{
                    removeLock(key);
                    return false;
                }
            }
        }
        return false;
    }

    public void sendNotification(LoginRequest request, String key) {
        bruteForceData = loadFromMap(key);
        String email = Anty_Iniect(request.getEmail());
        int userId = getUserIdFromDB(request);
        
        if(userId == 0){
            return;
        }
        
        System.out.println("#################### Email: " + email + " UserId: " + userId + " login: " + request.getLogin());
        
        String subject =  "Monit Bezpieczeństwa. Nieudana próba logowania";
        String description =  "Witaj właśnie wykryliśmy <"+bruteForceData.getBruteForceCount()+"> niepoprawnych prób/y logowania. Ze względów bezpieczeństwa Twoje konto  < " +request.getLogin()+ " >zostało zablokowane na jedną godzinę";
        
        String query = "insert into powiadomienia "
                + "(po_status, po_temat, po_tresc, po_user_id, po_autor)"
                + "VALUES("
                + Dictionary.NOTIFICATION_STATUS_NEW +","
                + "'"+ subject +"',"
                + "'"+ description +"',"
                + userId +","
                + " '1'"
                + ")";
        System.out.println("login query : " + query);
        
        ORA_Start_Transakcji(Get_Polaczenie_Z_Baza_Danych());
        WynikSerweraSerwer wynikUpdate = Baza_Update(Get_Polaczenie_Z_Baza_Danych(), query);
        if(wynikUpdate.Get_Kod_Bledu() != Stale_Slownikowe.Operacja_Zakonczona_Sukcesem){
            System.out.println("Błąd wysłania powiadomienia");
        }
        ORA_Koniec_Transakcji(Get_Polaczenie_Z_Baza_Danych());

    }
    
    private int getUserIdFromDB(LoginRequest request) {
        String query = "select us_id as usid from users where us_login = '" + request.getLogin()+"'";
        System.out.println("query" + query);

        WynikSerweraSerwer wynik = Baza_Query(Get_Polaczenie_Z_Baza_Danych(), query);
        if(wynik.Get_Kod_Bledu() != Stale_Slownikowe.Operacja_Zakonczona_Sukcesem){
            System.out.println("Błąd wysłania powiadomienia");
        }
        return wynik.Daj_Int_Po_Nazwie(0, "usid"); //todo do poprawy
    }

    public void removeLock(String key) {
        BruteForceMap.getInstance().removeValue(key);
        System.out.println("#################### Bruteforce : RemoveLock");
    }
    
    private BruteForceData loadFromMap(String key) {
        if((bruteForceData = BruteForceMap.getInstance().getValue(key)) == null){
            bruteForceData = new BruteForceData();
        }else{
            bruteForceData = BruteForceMap.getInstance().getValue(key);
        }
         return bruteForceData;
    }

}
