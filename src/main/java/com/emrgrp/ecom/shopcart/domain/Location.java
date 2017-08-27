package com.emrgrp.ecom.shopcart.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * not an ignored comment
 */
@ApiModel(description = "not an ignored comment")
@Entity
@Table(name = "location")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "street_address")
    private String streetAddress;

    @NotNull
    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @NotNull
    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "state_province")
    private String stateProvince;

    @Column(name = "country")
    private String country;

    @OneToMany(mappedBy = "location")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DealerInfo> dealerLocations = new HashSet<>();

    @OneToMany(mappedBy = "location")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CustomerInfo> custLocations = new HashSet<>();

    @OneToMany(mappedBy = "location")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DeliverManagement> deliveryLocations = new HashSet<>();

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public Location streetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
        return this;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Location postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public Location city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public Location stateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
        return this;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public String getCountry() {
        return country;
    }

    public Location country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Set<DealerInfo> getDealerLocations() {
        return dealerLocations;
    }

    public Location dealerLocations(Set<DealerInfo> dealerInfos) {
        this.dealerLocations = dealerInfos;
        return this;
    }

    public Location addDealerLocation(DealerInfo dealerInfo) {
        this.dealerLocations.add(dealerInfo);
        dealerInfo.setLocation(this);
        return this;
    }

    public Location removeDealerLocation(DealerInfo dealerInfo) {
        this.dealerLocations.remove(dealerInfo);
        dealerInfo.setLocation(null);
        return this;
    }

    public void setDealerLocations(Set<DealerInfo> dealerInfos) {
        this.dealerLocations = dealerInfos;
    }

    public Set<CustomerInfo> getCustLocations() {
        return custLocations;
    }

    public Location custLocations(Set<CustomerInfo> customerInfos) {
        this.custLocations = customerInfos;
        return this;
    }

    public Location addCustLocation(CustomerInfo customerInfo) {
        this.custLocations.add(customerInfo);
        customerInfo.setLocation(this);
        return this;
    }

    public Location removeCustLocation(CustomerInfo customerInfo) {
        this.custLocations.remove(customerInfo);
        customerInfo.setLocation(null);
        return this;
    }

    public void setCustLocations(Set<CustomerInfo> customerInfos) {
        this.custLocations = customerInfos;
    }

    public Set<DeliverManagement> getDeliveryLocations() {
        return deliveryLocations;
    }

    public Location deliveryLocations(Set<DeliverManagement> deliverManagements) {
        this.deliveryLocations = deliverManagements;
        return this;
    }

    public Location addDeliveryLocation(DeliverManagement deliverManagement) {
        this.deliveryLocations.add(deliverManagement);
        deliverManagement.setLocation(this);
        return this;
    }

    public Location removeDeliveryLocation(DeliverManagement deliverManagement) {
        this.deliveryLocations.remove(deliverManagement);
        deliverManagement.setLocation(null);
        return this;
    }

    public void setDeliveryLocations(Set<DeliverManagement> deliverManagements) {
        this.deliveryLocations = deliverManagements;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Location location = (Location) o;
        if (location.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), location.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Location{" +
            "id=" + getId() +
            ", streetAddress='" + getStreetAddress() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", city='" + getCity() + "'" +
            ", stateProvince='" + getStateProvince() + "'" +
            ", country='" + getCountry() + "'" +
            "}";
    }
}
