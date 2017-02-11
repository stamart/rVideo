package pl.orangeretail.gofiber.server.objects;

/**
 *
 * @author pwitkowski
 */
public class StandardResponse {

//    private String sessionId;
    private String error;
    private int notificationAmount;
//    private int deviceID;
//    private int userId;

//    public String getSessionId() {
//        return sessionId;
//    }
//
//    public void setSessionId(String sessionId) {
//        this.sessionId = sessionId;
//    }

    public int getNotificationAmount() {
        return notificationAmount;
    }

    public void setNotificationAmount(int notificationAmount) {
        this.notificationAmount = notificationAmount;
    }
    
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
