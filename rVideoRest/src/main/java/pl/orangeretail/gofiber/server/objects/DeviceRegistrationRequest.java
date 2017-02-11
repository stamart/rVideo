package pl.orangeretail.gofiber.server.objects;

/**
 *
 * @author stanislaw.martowicz
 */
public class DeviceRegistrationRequest extends LoginRequest{
    private String imei;
    private String model;
    private String osVersion;
    
    private int status;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "DeviceRegistrationRequest{" + "imei=" + imei + ", model=" + model + ", osVersion=" + osVersion + ", status=" + status + '}';
    }

}
