package pl.orangeretail.gofiber.server.objects;

/**
 *
 * @author stanislaw.martowicz
 */
public class DeviceRegistrationResponse extends StandardResponse{
    
    public boolean deviceRegistered;
    private String phoneNumber;
    
    public boolean isDeviceRegistered() {
        return deviceRegistered;
    }

    public void setDeviceRegistered(boolean deviceRegistered) {
        this.deviceRegistered = deviceRegistered;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "DeviceRegistrationResponse{" + "deviceRegistered=" + deviceRegistered + ", phoneNumber=" + phoneNumber + '}';
    }

}
