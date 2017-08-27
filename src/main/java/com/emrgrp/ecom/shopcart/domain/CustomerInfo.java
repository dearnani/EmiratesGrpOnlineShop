package com.emrgrp.ecom.shopcart.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * The Employee entity.
 */
@ApiModel(description = "The Employee entity.")
@Entity
@Table(name = "customer_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CustomerInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "alias_name", nullable = false)
    private String aliasName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "last_login")
    private ZonedDateTime lastLogin;

    @ManyToOne
    private Location location;

    @OneToMany(mappedBy = "customerInfo")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OrderMangement> customerInfos = new HashSet<>();

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAliasName() {
        return aliasName;
    }

    public CustomerInfo aliasName(String aliasName) {
        this.aliasName = aliasName;
        return this;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getFirstName() {
        return firstName;
    }

    public CustomerInfo firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public CustomerInfo lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public CustomerInfo email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public CustomerInfo phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ZonedDateTime getLastLogin() {
        return lastLogin;
    }

    public CustomerInfo lastLogin(ZonedDateTime lastLogin) {
        this.lastLogin = lastLogin;
        return this;
    }

    public void setLastLogin(ZonedDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Location getLocation() {
        return location;
    }

    public CustomerInfo location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Set<OrderMangement> getCustomerInfos() {
        return customerInfos;
    }

    public CustomerInfo customerInfos(Set<OrderMangement> orderMangements) {
        this.customerInfos = orderMangements;
        return this;
    }

    public CustomerInfo addCustomerInfo(OrderMangement orderMangement) {
        this.customerInfos.add(orderMangement);
        orderMangement.setCustomerInfo(this);
        return this;
    }

    public CustomerInfo removeCustomerInfo(OrderMangement orderMangement) {
        this.customerInfos.remove(orderMangement);
        orderMangement.setCustomerInfo(null);
        return this;
    }

    public void setCustomerInfos(Set<OrderMangement> orderMangements) {
        this.customerInfos = orderMangements;
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
        CustomerInfo customerInfo = (CustomerInfo) o;
        if (customerInfo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customerInfo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustomerInfo{" +
            "id=" + getId() +
            ", aliasName='" + getAliasName() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", lastLogin='" + getLastLogin() + "'" +
            "}";
    }
}
