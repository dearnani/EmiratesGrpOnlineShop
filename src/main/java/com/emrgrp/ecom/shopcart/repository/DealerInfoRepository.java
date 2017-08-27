package com.emrgrp.ecom.shopcart.repository;

import com.emrgrp.ecom.shopcart.domain.DealerInfo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DealerInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DealerInfoRepository extends JpaRepository<DealerInfo, Long> {

}
