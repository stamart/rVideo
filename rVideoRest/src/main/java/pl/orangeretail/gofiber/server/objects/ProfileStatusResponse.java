package pl.orangeretail.gofiber.server.objects;

import java.util.Date;

public class ProfileStatusResponse extends StandardResponse {
    public String firstName;
    public String lastName;
    public int accountTypeId ;
    public String accountTypeName;
    public Date updateToProDate;
    public String phoneNumber;
    public String email;
    public int fillPercent;
    public boolean marketingAgreementsStatus;
    public boolean emailAddressStatus;
    public boolean addressDataStatus;
    public boolean bankAccountStatus;
    public boolean taxOfficeDataStatus;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(int accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public String getAccountTypeName() {
        return accountTypeName;
    }

    public void setAccountTypeName(String accountTypeName) {
        this.accountTypeName = accountTypeName;
    }

    public Date getUpdateToProDate() {
        return updateToProDate;
    }

    public void setUpdateToProDate(Date updateToProDate) {
        this.updateToProDate = updateToProDate;
    }
                
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getFillPercent() {
        return fillPercent;
    }

    public void setFillPercent(int fillPercent) {
        this.fillPercent = fillPercent;
    }

    public boolean isMarketingAgreementsStatus() {
        return marketingAgreementsStatus;
    }

    public void setMarketingAgreementsStatus(boolean marketingAgreementsStatus) {
        this.marketingAgreementsStatus = marketingAgreementsStatus;
    }

    public boolean isEmailAddressStatus() {
        return emailAddressStatus;
    }

    public void setEmailAddressStatus(boolean emailAddressStatus) {
        this.emailAddressStatus = emailAddressStatus;
    }

    public boolean isAddressDataStatus() {
        return addressDataStatus;
    }

    public void setAddressDataStatus(boolean addressDataStatus) {
        this.addressDataStatus = addressDataStatus;
    }

    public boolean isBankAccountStatus() {
        return bankAccountStatus;
    }

    public void setBankAccountStatus(boolean bankAccountStatus) {
        this.bankAccountStatus = bankAccountStatus;
    }

    public boolean isTaxOfficeDataStatus() {
        return taxOfficeDataStatus;
    }

    public void setTaxOfficeDataStatus(boolean taxOfficeDataStatus) {
        this.taxOfficeDataStatus = taxOfficeDataStatus;
    }
}
