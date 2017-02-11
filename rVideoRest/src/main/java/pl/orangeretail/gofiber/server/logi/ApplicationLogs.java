/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.orangeretail.gofiber.server.logi;


import java.util.Date;

/**
 *
 * @author Staszek.martowicz
 */
public class ApplicationLogs {
    private int typID;
    private int userId;
    private int deviceID;
    private int idSession;
    private int elementID;

    private Date dataZdarzenia;

    private String appVersion;
    private String appType;
    private String imei;
    private String description;
    private String ipAddress;
    private String sessionId;
    private String responseError;

    public int getTypID() {
        return typID;
    }

    public void setTypID(int typID) {
        this.typID = typID;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(int deviceID) {
        this.deviceID = deviceID;
    }

    public int getIdSession() {
        return idSession;
    }

    public void setIdSession(int idSession) {
        this.idSession = idSession;
    }

    public Date getDataZdarzenia() {
        return dataZdarzenia;
    }

    public void setDataZdarzenia(Date dataZdarzenia) {
        this.dataZdarzenia = dataZdarzenia;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public int getElementID() {
        return elementID;
    }

    public void setElementID(int elementID) {
        this.elementID = elementID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getResponseError() {
        return responseError;
    }

    public void setResponseError(String responseError) {
        this.responseError = responseError;
    }

    @Override
    public String toString() {
        return "ApplicationLogs{" + "typID=" + typID + ", userId=" + userId + ", deviceID=" + deviceID + ", idSession=" + idSession + ", dataZdarzenia=" + dataZdarzenia + ", appVersion=" + appVersion + ", appType=" + appType + ", imei=" + imei + ", elementID=" + elementID + ", description=" + description + ", ipAddress=" + ipAddress + ", sessionId=" + sessionId + ", responseError=" + responseError + '}';
    }

}
