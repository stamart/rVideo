package pl.orangeretail.gofiber.server.objects;

/**
 * Created by marek.rusnak on 19.09.2016.
 */
public class UpdateTaxOfficeDataResponse extends StandardResponse {
    private String nip;
    private String pesel;
    private String taxOfficeNumber;
    private String city;
    private String zipCode;
    private String street;
    private String streetNumber;
    private String flatNumber;
    private String country = "Polska";

    public void setNip(String nip) {
        this.nip = nip;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public void setTaxOfficeNumber(String taxOfficeNumber) {
        this.taxOfficeNumber = taxOfficeNumber;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNip() {
        return nip;
    }

    public String getPesel() {
        return pesel;
    }

    public String getTaxOfficeNumber() {
        return taxOfficeNumber;
    }

    public String getCity() {
        return city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getStreet() {
        return street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public String getCountry() {
        return country;
    }
}
