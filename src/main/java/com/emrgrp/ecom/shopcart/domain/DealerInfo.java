package com.emrgrp.ecom.shopcart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.emrgrp.ecom.shopcart.domain.enumeration.DealerStatus;

/**
 * A DealerInfo.
 */
@Entity
@Table(name = "dealer_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DealerInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "dealer_code", nullable = false)
    private String dealerCode;

    @Column(name = "dealer_name")
    private String dealerName;

    @Column(name = "dear_location")
    private String dearLocation;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "dealer_status", nullable = false)
    private DealerStatus dealerStatus;

    @ManyToOne
    private ProductInfo productInfo;

    @ManyToOne
    private Location location;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public DealerInfo dealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
        return this;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    public String getDealerName() {
        return dealerName;
    }

    public DealerInfo dealerName(String dealerName) {
        this.dealerName = dealerName;
        return this;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getDearLocation() {
        return dearLocation;
    }

    public DealerInfo dearLocation(String dearLocation) {
        this.dearLocation = dearLocation;
        return this;
    }

    public void setDearLocation(String dearLocation) {
        this.dearLocation = dearLocation;
    }

    public DealerStatus getDealerStatus() {
        return dealerStatus;
    }

    public DealerInfo dealerStatus(DealerStatus dealerStatus) {
        this.dealerStatus = dealerStatus;
        return this;
    }

    public void setDealerStatus(DealerStatus dealerStatus) {
        this.dealerStatus = dealerStatus;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public DealerInfo productInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
        return this;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    public Location getLocation() {
        return location;
    }

    public DealerInfo location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
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
        DealerInfo dealerInfo = (DealerInfo) o;
        if (dealerInfo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dealerInfo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DealerInfo{" +
            "id=" + getId() +
            ", dealerCode='" + getDealerCode() + "'" +
            ", dealerName='" + getDealerName() + "'" +
            ", dearLocation='" + getDearLocation() + "'" +
            ", dealerStatus='" + getDealerStatus() + "'" +
            "}";
    }
}
