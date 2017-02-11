/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.orangeretail.gofiber.server.logi;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import pl.orangeretail.gofiber.server.objects.StandardResponse;

/**
 *
 * @author staszek.martowicz
 */
public class ConnectionsList extends StandardResponse {

    private final Map <Integer, ApplicationLogs> connections;
    
    private static ConnectionsList instance;
    
    public static ConnectionsList getInstance() {
        if(instance==null) { instance = new ConnectionsList(); }
        return instance;
    }
    
    private ConnectionsList(){
        connections = new ConcurrentHashMap<Integer, ApplicationLogs>();
    }
    
    public void addValue(Integer key, ApplicationLogs value){
        connections.put(key, value);
        System.out.println("############### ConnectionList object added:" + connections.get(key));
    }
    
    public ApplicationLogs getValue(Integer key){
        System.out.println("############### ApplicationLogs getValue (userId): " + key);
        return connections.get(key);
    }
   
}
