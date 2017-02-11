package pl.orangeretail.gofiber.server.objects;

/**
 *
 * @author stanislaw.martowicz
 */
public class RegistrationRequest extends DeviceVeryficationRequest {

    private String emailAdress;
    private String phoneNumber;
    private String zipCode;

    private int accountType;

    private int socialMediaType;
    private String socialMediaId;

    public String getEmailAdress() {
        return emailAdress;
    }

    public void setEmailAdress(String emailAdress) {
        this.emailAdress = emailAdress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public int getSocialMediaType() {
        return socialMediaType;
    }

    public void setSocialMediaType(int socialMediaType) {
        this.socialMediaType = socialMediaType;
    }

    public String getSocialMediaId() {
        return socialMediaId;
    }

    public void setSocialMediaId(String socialMediaId) {
        this.socialMediaId = socialMediaId;
    }

    @Override
    public String toString() {
        return "RegistrationRequest{" + "emailAdress=" + emailAdress + ", phoneNumber=" + phoneNumber + ", zipCode=" + zipCode + ", accountType=" + accountType + ", socialMediaType=" + socialMediaType + ", socialMediaId=" + socialMediaId + '}';
    }

}
