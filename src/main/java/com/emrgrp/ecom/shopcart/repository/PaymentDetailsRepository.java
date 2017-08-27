package com.emrgrp.ecom.shopcart.repository;

import com.emrgrp.ecom.shopcart.domain.PaymentDetails;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PaymentDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, Long> {

}
