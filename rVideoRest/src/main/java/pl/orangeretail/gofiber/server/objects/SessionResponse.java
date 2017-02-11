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
public class SessionResponse {
    private String sessionId;
    private String sessionError;
    private int sessionStatus;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionError() {
        return sessionError;
    }

    public void setSessionError(String sessionError) {
        this.sessionError = sessionError;
    }

    public int getSessionStatus() {
        return sessionStatus;
    }

    public void setSessionStatus(int sessionStatus) {
        this.sessionStatus = sessionStatus;
    }

    @Override
    public String toString() {
        return "SessionResponse{" + "sessionId=" + sessionId + ", sessionError=" + sessionError + ", sessionStatus=" + sessionStatus + '}';
    }

}
