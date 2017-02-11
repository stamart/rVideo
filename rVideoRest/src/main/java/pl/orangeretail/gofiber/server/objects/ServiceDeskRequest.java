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
public class ServiceDeskRequest extends StandardRequest{
    
    private String servicedeskDescription;
    private String email;
    private String appVersion;

    public String getServicedeskDescription() {
        return servicedeskDescription;
    }

    public void setServicedeskDescription(String servicedeskDescription) {
        this.servicedeskDescription = servicedeskDescription;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    @Override
    public String toString() {
        return "ServiceDeskRequest{" + "servicedeskDescription=" + servicedeskDescription + ", email=" + email + ", appVersion=" + appVersion + '}';
    }
    
}
