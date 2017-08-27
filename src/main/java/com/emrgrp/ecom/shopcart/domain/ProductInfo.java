package com.emrgrp.ecom.shopcart.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.emrgrp.ecom.shopcart.domain.enumeration.ProductType;

import com.emrgrp.ecom.shopcart.domain.enumeration.ProductStatus;

/**
 * A ProductInfo.
 */
@Entity
@Table(name = "product_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "prouct_code", nullable = false)
    private String prouctCode;

    @Column(name = "proudct_name")
    private String proudctName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "product_type", nullable = false)
    private ProductType productType;

    @Column(name = "product_description")
    private String productDescription;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "product_status", nullable = false)
    private ProductStatus productStatus;

    @OneToMany(mappedBy = "productInfo")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DealerInfo> dealerProductInfos = new HashSet<>();

    @OneToMany(mappedBy = "productInfo")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OrderMangement> productInfos = new HashSet<>();

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProuctCode() {
        return prouctCode;
    }

    public ProductInfo prouctCode(String prouctCode) {
        this.prouctCode = prouctCode;
        return this;
    }

    public void setProuctCode(String prouctCode) {
        this.prouctCode = prouctCode;
    }

    public String getProudctName() {
        return proudctName;
    }

    public ProductInfo proudctName(String proudctName) {
        this.proudctName = proudctName;
        return this;
    }

    public void setProudctName(String proudctName) {
        this.proudctName = proudctName;
    }

    public ProductType getProductType() {
        return productType;
    }

    public ProductInfo productType(ProductType productType) {
        this.productType = productType;
        return this;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public ProductInfo productDescription(String productDescription) {
        this.productDescription = productDescription;
        return this;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public ProductInfo productStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
        return this;
    }

    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }

    public Set<DealerInfo> getDealerProductInfos() {
        return dealerProductInfos;
    }

    public ProductInfo dealerProductInfos(Set<DealerInfo> dealerInfos) {
        this.dealerProductInfos = dealerInfos;
        return this;
    }

    public ProductInfo addDealerProductInfo(DealerInfo dealerInfo) {
        this.dealerProductInfos.add(dealerInfo);
        dealerInfo.setProductInfo(this);
        return this;
    }

    public ProductInfo removeDealerProductInfo(DealerInfo dealerInfo) {
        this.dealerProductInfos.remove(dealerInfo);
        dealerInfo.setProductInfo(null);
        return this;
    }

    public void setDealerProductInfos(Set<DealerInfo> dealerInfos) {
        this.dealerProductInfos = dealerInfos;
    }

    public Set<OrderMangement> getProductInfos() {
        return productInfos;
    }

    public ProductInfo productInfos(Set<OrderMangement> orderMangements) {
        this.productInfos = orderMangements;
        return this;
    }

    public ProductInfo addProductInfo(OrderMangement orderMangement) {
        this.productInfos.add(orderMangement);
        orderMangement.setProductInfo(this);
        return this;
    }

    public ProductInfo removeProductInfo(OrderMangement orderMangement) {
        this.productInfos.remove(orderMangement);
        orderMangement.setProductInfo(null);
        return this;
    }

    public void setProductInfos(Set<OrderMangement> orderMangements) {
        this.productInfos = orderMangements;
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
        ProductInfo productInfo = (ProductInfo) o;
        if (productInfo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productInfo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductInfo{" +
            "id=" + getId() +
            ", prouctCode='" + getProuctCode() + "'" +
            ", proudctName='" + getProudctName() + "'" +
            ", productType='" + getProductType() + "'" +
            ", productDescription='" + getProductDescription() + "'" +
            ", productStatus='" + getProductStatus() + "'" +
            "}";
    }
}
