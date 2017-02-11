package pl.orangeretail.gofiber.server.objects;

public class LoginRequest extends StandardRequest {

    private String login;
    private String hash;
    private String email;
    private int loginType;
    private String appVersion;

    public void setLogin(String login) {
        this.login = login;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getLogin() {
        return login;
    }

    public String getHash() {
        return hash;
    }

    public int getLoginType() {
        return loginType;
    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
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

}
