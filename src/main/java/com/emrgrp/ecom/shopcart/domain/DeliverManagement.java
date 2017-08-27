package com.emrgrp.ecom.shopcart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.emrgrp.ecom.shopcart.domain.enumeration.DeliveryStatus;

/**
 * A DeliverManagement.
 */
@Entity
@Table(name = "deliver_management")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DeliverManagement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_number")
    private Long orderNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_status")
    private DeliveryStatus deliveryStatus;

    @ManyToOne
    private Location location;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public DeliverManagement orderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public DeliverManagement deliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
        return this;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public Location getLocation() {
        return location;
    }

    public DeliverManagement location(Location location) {
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
        DeliverManagement deliverManagement = (DeliverManagement) o;
        if (deliverManagement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), deliverManagement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DeliverManagement{" +
            "id=" + getId() +
            ", orderNumber='" + getOrderNumber() + "'" +
            ", deliveryStatus='" + getDeliveryStatus() + "'" +
            "}";
    }
}
