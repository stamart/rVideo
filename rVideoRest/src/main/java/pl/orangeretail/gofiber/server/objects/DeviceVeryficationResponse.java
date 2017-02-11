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
public class DeviceVeryficationResponse extends StandardResponse{
     public boolean deviceValidated;

    public boolean isDeviceValidated() {
        return deviceValidated;
    }

    public void setDeviceValidated(boolean deviceRegistered) {
        this.deviceValidated = deviceRegistered;
    }
    
}
