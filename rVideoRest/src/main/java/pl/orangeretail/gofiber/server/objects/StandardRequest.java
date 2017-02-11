/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.orangeretail.gofiber.server.objects;

/**
 *
 * Stanisław Martowicz
 */
public class StandardRequest implements java.io.Serializable {

    private static final long serialVersionUID = 7526471155622776147L;

    private int deviceID;
    private int userId;
    private String token;
    private String imei;
    private String model;
    private String osVersion;

    private String sessionId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(int deviceID) {
        this.deviceID = deviceID;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionID(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return "StandardRequest{" + "deviceID=" + deviceID + ", userId=" + userId + ", token=" + token + ", imei=" + imei + ", model=" + model + ", osVersion=" + osVersion + ", sessionId=" + sessionId + '}';
    }

}
