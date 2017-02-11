package pl.orangeretail.gofiber.server.objects;

public class LoginResponse extends StandardResponse {
    
    private String sessionId;
    private int accountType;
    private boolean idKampaFilled;
    
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public boolean isIdKampaFilled() {
        return idKampaFilled;
    }

    public void setIdKampaFilled(boolean idKampaFilled) {
        this.idKampaFilled = idKampaFilled;
    }

    @Override
    public String toString() {
        return "LoginResponse{" + "sessionId=" + sessionId + ", accountType=" + accountType + ", idKampaFilled=" + idKampaFilled + '}';
    }

   
}
