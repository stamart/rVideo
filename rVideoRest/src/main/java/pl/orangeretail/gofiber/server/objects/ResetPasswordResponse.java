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
public class ResetPasswordResponse extends StandardResponse{
    public boolean passwordChanged;
    public boolean deviceRegistered;

    public boolean isPasswordChanged() {
        return passwordChanged;
    }

    public void setPasswordChanged(boolean passwordChanged) {
        this.passwordChanged = passwordChanged;
    }

    public boolean isDeviceRegistered() {
        return deviceRegistered;
    }

    public void setDeviceRegistered(boolean deviceRegistered) {
        this.deviceRegistered = deviceRegistered;
    }

    @Override
    public String toString() {
        return "ResetPasswordResponse{" + "passwordChanged=" + passwordChanged + ", deviceRegistered=" + deviceRegistered + '}';
    }
    
}
