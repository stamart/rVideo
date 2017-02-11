/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.orangeretail.gofiber.server.objects;

/**
 *
 * @author staszek
 */
public class ServiceDeskResponse extends StandardResponse{
    private boolean serviceDeskFlag;
    private boolean serviceDeskUnregistered;

    public boolean isServiceDeskFlag() {
        return serviceDeskFlag;
    }

    public void setServiceDeskFlag(boolean serviceDeskFlag) {
        this.serviceDeskFlag = serviceDeskFlag;
    }

    public boolean isServiceDeskUnregistered() {
        return serviceDeskUnregistered;
    }

    public void setServiceDeskUnregistered(boolean serviceDeskUnregistered) {
        this.serviceDeskUnregistered = serviceDeskUnregistered;
    }
    
}
