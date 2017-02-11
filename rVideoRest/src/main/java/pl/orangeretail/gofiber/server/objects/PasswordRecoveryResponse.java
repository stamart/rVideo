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
public class PasswordRecoveryResponse extends StandardResponse{
     private String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return "LoginResponse{" + "sessionId=" + sessionId + '}';
    }
}
