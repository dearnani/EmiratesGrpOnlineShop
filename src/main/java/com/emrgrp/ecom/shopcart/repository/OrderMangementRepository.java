package com.emrgrp.ecom.shopcart.repository;

import com.emrgrp.ecom.shopcart.domain.OrderMangement;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the OrderMangement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderMangementRepository extends JpaRepository<OrderMangement, Long> {

}
