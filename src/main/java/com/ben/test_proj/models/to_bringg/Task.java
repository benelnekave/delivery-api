package com.ben.test_proj.models.to_bringg;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "title",
        "address",
        "scheduled_at",
        "company_id",
        "team_id",
        "lat",
        "lan",
        "created_at",
        "customer"
})
public class Task {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("address")
    private String address;
    @JsonProperty("scheduled_at")
    private String scheduledAt;
    @JsonProperty("company_id")
    private Integer companyId;
    @JsonProperty("team_id")
    private Integer teamId;
    @JsonProperty("lat")
    private Double lat;
    @JsonProperty("lan")
    private Double lan;
    @JsonProperty("customer")
    private Customer customer;
    @JsonProperty("created_at")
    private String createdAt;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("address")
    public String getAddress() {
        return address;
    }

    @JsonProperty("address")
    public void setAddress(String address) {
        this.address = address;
    }

    @JsonProperty("scheduled_at")
    public String getScheduledAt() {
        return scheduledAt;
    }

    @JsonProperty("scheduled_at")
    public void setScheduledAt(String scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    @JsonProperty("company_id")
    public Integer getCompanyId() {
        return companyId;
    }

    @JsonProperty("company_id")
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @JsonProperty("team_id")
    public Integer getTeamId() {
        return teamId;
    }

    @JsonProperty("team_id")
    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    @JsonProperty("lat")
    public Double getLat() {
        return lat;
    }

    @JsonProperty("lat")
    public void setLat(Double lat) {
        this.lat = lat;
    }

    @JsonProperty("lan")
    public Double getLan() {
        return lan;
    }

    @JsonProperty("lan")
    public void setLan(Double lan) {
        this.lan = lan;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonProperty("created_at")
    public String getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("created_at")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("customer")
    public Customer getCustomer() {
        return customer;
    }

    @JsonProperty("customer")
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}