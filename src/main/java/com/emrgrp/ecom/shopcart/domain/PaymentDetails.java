package com.emrgrp.ecom.shopcart.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.emrgrp.ecom.shopcart.domain.enumeration.PaymentChannel;

import com.emrgrp.ecom.shopcart.domain.enumeration.PaymentStatus;

/**
 * A PaymentDetails.
 */
@Entity
@Table(name = "payment_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PaymentDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_mode", nullable = false)
    private PaymentChannel paymentMode;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus;

    @NotNull
    @Column(name = "oder_number", nullable = false)
    private String oderNumber;

    @Column(name = "oder_date")
    private ZonedDateTime oderDate;

    @NotNull
    @Column(name = "number_of_products", nullable = false)
    private Integer numberOfProducts;

    @NotNull
    @Column(name = "total_amount", nullable = false)
    private Long totalAmount;

    @OneToMany(mappedBy = "paymentDetails")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OrderMangement> paymentDetails = new HashSet<>();

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaymentChannel getPaymentMode() {
        return paymentMode;
    }

    public PaymentDetails paymentMode(PaymentChannel paymentMode) {
        this.paymentMode = paymentMode;
        return this;
    }

    public void setPaymentMode(PaymentChannel paymentMode) {
        this.paymentMode = paymentMode;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public PaymentDetails paymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getOderNumber() {
        return oderNumber;
    }

    public PaymentDetails oderNumber(String oderNumber) {
        this.oderNumber = oderNumber;
        return this;
    }

    public void setOderNumber(String oderNumber) {
        this.oderNumber = oderNumber;
    }

    public ZonedDateTime getOderDate() {
        return oderDate;
    }

    public PaymentDetails oderDate(ZonedDateTime oderDate) {
        this.oderDate = oderDate;
        return this;
    }

    public void setOderDate(ZonedDateTime oderDate) {
        this.oderDate = oderDate;
    }

    public Integer getNumberOfProducts() {
        return numberOfProducts;
    }

    public PaymentDetails numberOfProducts(Integer numberOfProducts) {
        this.numberOfProducts = numberOfProducts;
        return this;
    }

    public void setNumberOfProducts(Integer numberOfProducts) {
        this.numberOfProducts = numberOfProducts;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public PaymentDetails totalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Set<OrderMangement> getPaymentDetails() {
        return paymentDetails;
    }

    public PaymentDetails paymentDetails(Set<OrderMangement> orderMangements) {
        this.paymentDetails = orderMangements;
        return this;
    }

    public PaymentDetails addPaymentDetails(OrderMangement orderMangement) {
        this.paymentDetails.add(orderMangement);
        orderMangement.setPaymentDetails(this);
        return this;
    }

    public PaymentDetails removePaymentDetails(OrderMangement orderMangement) {
        this.paymentDetails.remove(orderMangement);
        orderMangement.setPaymentDetails(null);
        return this;
    }

    public void setPaymentDetails(Set<OrderMangement> orderMangements) {
        this.paymentDetails = orderMangements;
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
        PaymentDetails paymentDetails = (PaymentDetails) o;
        if (paymentDetails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paymentDetails.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PaymentDetails{" +
            "id=" + getId() +
            ", paymentMode='" + getPaymentMode() + "'" +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", oderNumber='" + getOderNumber() + "'" +
            ", oderDate='" + getOderDate() + "'" +
            ", numberOfProducts='" + getNumberOfProducts() + "'" +
            ", totalAmount='" + getTotalAmount() + "'" +
            "}";
    }
}
