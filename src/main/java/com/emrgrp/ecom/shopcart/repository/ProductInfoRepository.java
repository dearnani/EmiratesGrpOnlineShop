package com.emrgrp.ecom.shopcart.repository;

import com.emrgrp.ecom.shopcart.domain.ProductInfo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ProductInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductInfoRepository extends JpaRepository<ProductInfo, Long> {

}
