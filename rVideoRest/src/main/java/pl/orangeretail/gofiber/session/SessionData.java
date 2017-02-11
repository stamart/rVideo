/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.orangeretail.gofiber.session;

import java.util.Date;
import pl.orangeretail.gofiber.server.logi.ApplicationLogs;

/**
 *
 * @author staszek.martowicz
 */
public class SessionData {

    private boolean isDeviceRegistered;
    private boolean isTokenValidated;
    private boolean isUserLogedIn;
    private boolean isAccountTypePro;
    private boolean isAccountTypeBasic;
    private boolean isAccountTypeHunter;
    private boolean idKampaFilled;
    
    private ApplicationLogs applicationLogs;
    
    private Date dateLogin;
    private Date dateLastActivity;
    private Date expirationDate;
    
    private int deviceID;
    private int userId;
    private int idKampa;
    private int accountType;
    
    private String token;
    private String SessionID;
    private String IPAddress;
    private String phonNumber;

    
    private String login;
    private String hash;
    private String imei;
    private String model;
    private String osVersion;
    private String appVersion;

    public boolean isIsDeviceRegistered() {
        return isDeviceRegistered;
    }

    public void setIsDeviceRegistered(boolean isDeviceRegistered) {
        this.isDeviceRegistered = isDeviceRegistered;
    }

    public boolean isIsTokenValidated() {
        return isTokenValidated;
    }

    public void setIsTokenValidated(boolean isTokenValidated) {
        this.isTokenValidated = isTokenValidated;
    }

    public boolean isIsUserLogedIn() {
        return isUserLogedIn;
    }

    public void setIsUserLogedIn(boolean isUserLogedIn) {
        this.isUserLogedIn = isUserLogedIn;
    }

    public boolean isIsAccountTypePro() {
        return isAccountTypePro;
    }

    public void setIsAccountTypePro(boolean isAccountTypePro) {
        this.isAccountTypePro = isAccountTypePro;
    }

    public boolean isIsAccountTypeBasic() {
        return isAccountTypeBasic;
    }

    public void setIsAccountTypeBasic(boolean isAccountTypeBasic) {
        this.isAccountTypeBasic = isAccountTypeBasic;
    }

    public boolean isIsAccountTypeHunter() {
        return isAccountTypeHunter;
    }

    public void setIsAccountTypeHunter(boolean isAccountTypeHunter) {
        this.isAccountTypeHunter = isAccountTypeHunter;
    }

    public boolean isIdKampaFilled() {
        return idKampaFilled;
    }

    public void setIdKampaFilled(boolean idKampaFilled) {
        this.idKampaFilled = idKampaFilled;
    }

    public int getIdKampa() {
        return idKampa;
    }

    public void setIdKampa(int idKampa) {
        this.idKampa = idKampa;
    }

    public SessionData() {
        dateLastActivity = new Date();
    }

    public ApplicationLogs getApplicationLogs() {
        return applicationLogs;
    }

    public void setApplicationLogs(ApplicationLogs applicationLogs) {
        this.applicationLogs = applicationLogs;
    }

    public Date getDateLogin() {
        return dateLogin;
    }

    public void setDateLogin(Date dateLogin) {
        this.dateLogin = dateLogin;
    }

    public Date getDateLastActivity() {
        return dateLastActivity;
    }

    public void setDateLastActivity(Date dateLastActivity) {
        this.dateLastActivity = dateLastActivity;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

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

    public int getUserID() {
        return userId;
    }

    public void setUserID(int userId) {
        this.userId = userId;
    }
    
    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }
    
    public String getSessionId() {
        return SessionID;
    }

    public void setSessionID(String SessionID) {
        this.SessionID = SessionID;
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
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

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getPhonNumber() {
        return phonNumber;
    }

    public void setPhonNumber(String phonNumber) {
        this.phonNumber = phonNumber;
    }

    @Override
    public String toString() {
        return "SessionData{" + "isDeviceRegistered=" + isDeviceRegistered + ", isTokenValidated=" + isTokenValidated + ", isUserLogedIn=" + isUserLogedIn + ", isAccountTypePro=" + isAccountTypePro + ", isAccountTypeBasic=" + isAccountTypeBasic + ", isAccountTypeHunter=" + isAccountTypeHunter + ", idKampaFilled=" + idKampaFilled + ", applicationLogs=" + applicationLogs + ", dateLogin=" + dateLogin + ", dateLastActivity=" + dateLastActivity + ", expirationDate=" + expirationDate + ", deviceID=" + deviceID + ", userId=" + userId + ", idKampa=" + idKampa + ", accountType=" + accountType + ", token=" + token + ", SessionID=" + SessionID + ", IPAddress=" + IPAddress + ", phonNumber=" + phonNumber + ", login=" + login + ", hash=" + hash + ", imei=" + imei + ", model=" + model + ", osVersion=" + osVersion + ", appVersion=" + appVersion + '}';
    }

}
