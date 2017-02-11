/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.orangeretail.gofiber.server.objects;

import java.util.ArrayList;

/**
 *
 * @author staszek
 */
public class NotificationsListResponse extends StandardResponse{
    
    private ArrayList<Notification> notifications = new ArrayList<>();

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }
    
}
