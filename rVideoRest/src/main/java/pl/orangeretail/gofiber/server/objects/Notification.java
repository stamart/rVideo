/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.orangeretail.gofiber.server.objects;

import java.util.Date;

/**
 *
 * @author staszek
 */
public class Notification {
    
    private int notificationId;
    private int type;
    private int status;
    private int author;
    
    private String subject;
    private String description;

    private Date crationDate;
    private Date changeDate;

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }
    
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCrationDate() {
        return crationDate;
    }

    public void setCrationDate(Date crationDate) {
        this.crationDate = crationDate;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    @Override
    public String toString() {
        return "Notification{" + "notificationId=" + notificationId + ", type=" + type + ", status=" + status + ", author=" + author + ", subject=" + subject + ", description=" + description + ", crationDate=" + crationDate + ", changeDate=" + changeDate + '}';
    }

}
