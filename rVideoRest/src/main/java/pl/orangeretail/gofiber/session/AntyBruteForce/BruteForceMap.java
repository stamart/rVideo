/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.orangeretail.gofiber.session.AntyBruteForce;

import BazaDanych.Environment;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author staszek
 */
public class BruteForceMap extends Environment{
    
    public static final int BLOCKING_LENGHT = 60;
    public static final int BLOCKING_COUNT = 2;
    
    private final ConcurrentHashMap <String, BruteForceData> bruteForce;
        
    private static BruteForceMap instance;
    
    public static BruteForceMap getInstance() {
        if(instance==null) { instance = new BruteForceMap(); }
        return instance;
    }
    
    private BruteForceMap(){
//        setObiketWywolanyPrzezManageraPolaczen(true);
        bruteForce = new ConcurrentHashMap<>();
    }
    
    public void addValue(String key, BruteForceData value){
        bruteForce.put(key, value);
        System.out.println("############### AntyBruteForce object added:" + bruteForce.get(key));
    }
    
    public void removeValue(String key){
        bruteForce.remove(key);
        System.out.println("############### AntyBruteForce object added:" + bruteForce.get(key));
    }
    
    public BruteForceData getValue(String key){
        System.out.println("############### AntyBruteForce getValue (login) :" + key);
        return bruteForce.get(key);
    }
    
}
