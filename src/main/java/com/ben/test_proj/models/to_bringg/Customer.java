package com.ben.test_proj.models.to_bringg;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name",
        "address",
        "address_second_line",
        "zipcode",
        "borough",
        "city",
        "state",
        "lat",
        "lng",
        "phone",
        "image",
        "email",
        "company_id",
        "external_id",
        "confirmation_code"
})
public class Customer {

    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("address")
    private String address;
    @JsonProperty("address_second_line")
    private Object addressSecondLine;
    @JsonProperty("zipcode")
    private Object zipcode;
    @JsonProperty("borough")
    private Object borough;
    @JsonProperty("city")
    private Object city;
    @JsonProperty("state")
    private Object state;
    @JsonProperty("lat")
    private double lat;
    @JsonProperty("lng")
    private double lng;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("image")
    private String image;
    @JsonProperty("email")
    private String email;
    @JsonProperty("company_id")
    private int companyId;
    @JsonProperty("external_id")
    private String externalId;
    @JsonProperty("confirmation_code")
    private String confirmationCode;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public int getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("address")
    public String getAddress() {
        return address;
    }

    @JsonProperty("address")
    public void setAddress(String address) {
        this.address = address;
    }

    @JsonProperty("address_second_line")
    public Object getAddressSecondLine() {
        return addressSecondLine;
    }

    @JsonProperty("address_second_line")
    public void setAddressSecondLine(Object addressSecondLine) {
        this.addressSecondLine = addressSecondLine;
    }

    @JsonProperty("zipcode")
    public Object getZipcode() {
        return zipcode;
    }

    @JsonProperty("zipcode")
    public void setZipcode(Object zipcode) {
        this.zipcode = zipcode;
    }

    @JsonProperty("borough")
    public Object getBorough() {
        return borough;
    }

    @JsonProperty("borough")
    public void setBorough(Object borough) {
        this.borough = borough;
    }

    @JsonProperty("city")
    public Object getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(Object city) {
        this.city = city;
    }

    @JsonProperty("state")
    public Object getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(Object state) {
        this.state = state;
    }

    @JsonProperty("lat")
    public double getLat() {
        return lat;
    }

    @JsonProperty("lat")
    public void setLat(double lat) {
        this.lat = lat;
    }

    @JsonProperty("lng")
    public double getLng() {
        return lng;
    }

    @JsonProperty("lng")
    public void setLng(double lng) {
        this.lng = lng;
    }

    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    @JsonProperty("phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonProperty("image")
    public String getImage() {
        return image;
    }

    @JsonProperty("image")
    public void setImage(String image) {
        this.image = image;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("company_id")
    public int getCompanyId() {
        return companyId;
    }

    @JsonProperty("company_id")
    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    @JsonProperty("external_id")
    public String getExternalId() {
        return externalId;
    }

    @JsonProperty("external_id")
    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    @JsonProperty("confirmation_code")
    public String getConfirmationCode() {
        return confirmationCode;
    }

    @JsonProperty("confirmation_code")
    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}