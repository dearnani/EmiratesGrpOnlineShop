package com.emrgrp.ecom.shopcart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emrgrp.ecom.shopcart.domain.CustomerInfo;

import com.emrgrp.ecom.shopcart.repository.CustomerInfoRepository;
import com.emrgrp.ecom.shopcart.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CustomerInfo.
 */
@RestController
@RequestMapping("/api")
public class CustomerInfoResource {

    private final Logger log = LoggerFactory.getLogger(CustomerInfoResource.class);

    private static final String ENTITY_NAME = "customerInfo";

    private final CustomerInfoRepository customerInfoRepository;
    public CustomerInfoResource(CustomerInfoRepository customerInfoRepository) {
        this.customerInfoRepository = customerInfoRepository;
    }

    /**
     * POST  /customer-infos : Create a new customerInfo.
     *
     * @param customerInfo the customerInfo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customerInfo, or with status 400 (Bad Request) if the customerInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/customer-infos")
    @Timed
    public ResponseEntity<CustomerInfo> createCustomerInfo(@Valid @RequestBody CustomerInfo customerInfo) throws URISyntaxException {
        log.debug("REST request to save CustomerInfo : {}", customerInfo);
        if (customerInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new customerInfo cannot already have an ID")).body(null);
        }
        CustomerInfo result = customerInfoRepository.save(customerInfo);
        return ResponseEntity.created(new URI("/api/customer-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /customer-infos : Updates an existing customerInfo.
     *
     * @param customerInfo the customerInfo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customerInfo,
     * or with status 400 (Bad Request) if the customerInfo is not valid,
     * or with status 500 (Internal Server Error) if the customerInfo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/customer-infos")
    @Timed
    public ResponseEntity<CustomerInfo> updateCustomerInfo(@Valid @RequestBody CustomerInfo customerInfo) throws URISyntaxException {
        log.debug("REST request to update CustomerInfo : {}", customerInfo);
        if (customerInfo.getId() == null) {
            return createCustomerInfo(customerInfo);
        }
        CustomerInfo result = customerInfoRepository.save(customerInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, customerInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customer-infos : get all the customerInfos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of customerInfos in body
     */
    @GetMapping("/customer-infos")
    @Timed
    public List<CustomerInfo> getAllCustomerInfos() {
        log.debug("REST request to get all CustomerInfos");
        return customerInfoRepository.findAll();
        }

    /**
     * GET  /customer-infos/:id : get the "id" customerInfo.
     *
     * @param id the id of the customerInfo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customerInfo, or with status 404 (Not Found)
     */
    @GetMapping("/customer-infos/{id}")
    @Timed
    public ResponseEntity<CustomerInfo> getCustomerInfo(@PathVariable Long id) {
        log.debug("REST request to get CustomerInfo : {}", id);
        CustomerInfo customerInfo = customerInfoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(customerInfo));
    }

    /**
     * DELETE  /customer-infos/:id : delete the "id" customerInfo.
     *
     * @param id the id of the customerInfo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/customer-infos/{id}")
    @Timed
    public ResponseEntity<Void> deleteCustomerInfo(@PathVariable Long id) {
        log.debug("REST request to delete CustomerInfo : {}", id);
        customerInfoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
