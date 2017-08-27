package com.emrgrp.ecom.shopcart.repository;

import com.emrgrp.ecom.shopcart.domain.DeliverManagement;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DeliverManagement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeliverManagementRepository extends JpaRepository<DeliverManagement, Long> {

}
