/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.orangeretail.gofiber.server.objects;

/**
 *
 * @author staszek
 */
public class NotificationsDetailRequest extends StandardRequest{
    
    private int notificationId;

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    @Override
    public String toString() {
        return "NotificationsDetailRequest{" + "notificationId=" + notificationId + '}';
    }
    
}
