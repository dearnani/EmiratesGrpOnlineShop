package com.emrgrp.ecom.shopcart.repository;

import com.emrgrp.ecom.shopcart.domain.CustomerInfo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CustomerInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerInfoRepository extends JpaRepository<CustomerInfo, Long> {

}
