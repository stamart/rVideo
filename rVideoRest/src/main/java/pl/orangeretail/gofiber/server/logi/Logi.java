/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.orangeretail.gofiber.server.logi;

import BazaDanych.Environment;
import BazaDanych.Stale_Slownikowe;
import BazaDanych.WynikSerweraSerwer;

/**
 *
 * @author staszek.martowicz
 */
public class Logi extends Environment{
    
   
    private ApplicationLogs applicationLogs;
    
    public Logi() {
        setObiketWywolanyPrzezManageraPolaczen(true);
    }

    public void logEvent(ApplicationLogs logsObject) {
        this.applicationLogs = logsObject;
        ORA_Start_Transakcji(Get_Polaczenie_Z_Baza_Danych());
        String insert = "INSERT INTO Logi ("
                + "lo_typ_id, "
                + "lo_data_zdarzenia, "
                + "lo_user_id, "
                + "lo_wersja_aplikacji,"
                + "lo_typ_aplikacji,"
                + "lo_imei,"
                + "lo_device_id, "
                + "lo_element_id, "
                + "lo_ds_id, "
                + "lo_adres_ip, "
                + "lo_opis_bledu, "
                + "lo_opis) "
                + "VALUES (" 
                + applicationLogs.getTypID() + "," 
                + "sysdate" + ", " 
                + applicationLogs.getUserId() + ",'"
                + applicationLogs.getAppVersion() + "','"
                + applicationLogs.getAppType() + "','"
                + applicationLogs.getImei() + "',"
                + applicationLogs.getDeviceID() + ","
                + applicationLogs.getElementID() + ","
                + applicationLogs.getIdSession() + ",'"
                + applicationLogs.getIpAddress() + "','"
                + applicationLogs.getResponseError()+ "','"
                + Anty_Iniect(applicationLogs.getDescription()) + "')";

        System.out.println("Query: " + insert);
        
        WynikSerweraSerwer wynikTmp = Baza_Update(Get_Polaczenie_Z_Baza_Danych(), insert);
        
        if(wynikTmp.Get_Kod_Bledu() != Stale_Slownikowe.Operacja_Zakonczona_Sukcesem){
            System.out.println("Błąd dodawnia logu");
        }

        ORA_Koniec_Transakcji(Get_Polaczenie_Z_Baza_Danych());
        
    }

}
