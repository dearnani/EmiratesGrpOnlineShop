package com.emrgrp.ecom.shopcart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.emrgrp.ecom.shopcart.domain.enumeration.OrderStatus;

import com.emrgrp.ecom.shopcart.domain.enumeration.PaymentStatus;

/**
 * A OrderMangement.
 */
@Entity
@Table(name = "order_mangement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrderMangement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "product_code")
    private Long productCode;

    @Column(name = "dealer_code")
    private String dealerCode;

    @Column(name = "assigned_price", precision=10, scale=2)
    private BigDecimal assignedPrice;

    @Column(name = "dear_location")
    private String dearLocation;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @ManyToOne
    private ProductInfo productInfo;

    @ManyToOne
    private PaymentDetails paymentDetails;

    @ManyToOne
    private CustomerInfo customerInfo;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public OrderMangement orderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductCode() {
        return productCode;
    }

    public OrderMangement productCode(Long productCode) {
        this.productCode = productCode;
        return this;
    }

    public void setProductCode(Long productCode) {
        this.productCode = productCode;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public OrderMangement dealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
        return this;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    public BigDecimal getAssignedPrice() {
        return assignedPrice;
    }

    public OrderMangement assignedPrice(BigDecimal assignedPrice) {
        this.assignedPrice = assignedPrice;
        return this;
    }

    public void setAssignedPrice(BigDecimal assignedPrice) {
        this.assignedPrice = assignedPrice;
    }

    public String getDearLocation() {
        return dearLocation;
    }

    public OrderMangement dearLocation(String dearLocation) {
        this.dearLocation = dearLocation;
        return this;
    }

    public void setDearLocation(String dearLocation) {
        this.dearLocation = dearLocation;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public OrderMangement orderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public OrderMangement paymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public OrderMangement productInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
        return this;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    public OrderMangement paymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
        return this;
    }

    public void setPaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public OrderMangement customerInfo(CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
        return this;
    }

    public void setCustomerInfo(CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
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
        OrderMangement orderMangement = (OrderMangement) o;
        if (orderMangement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderMangement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderMangement{" +
            "id=" + getId() +
            ", orderId='" + getOrderId() + "'" +
            ", productCode='" + getProductCode() + "'" +
            ", dealerCode='" + getDealerCode() + "'" +
            ", assignedPrice='" + getAssignedPrice() + "'" +
            ", dearLocation='" + getDearLocation() + "'" +
            ", orderStatus='" + getOrderStatus() + "'" +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            "}";
    }
}
