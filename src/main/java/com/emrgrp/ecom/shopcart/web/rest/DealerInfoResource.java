package com.emrgrp.ecom.shopcart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emrgrp.ecom.shopcart.domain.DealerInfo;

import com.emrgrp.ecom.shopcart.repository.DealerInfoRepository;
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
 * REST controller for managing DealerInfo.
 */
@RestController
@RequestMapping("/api")
public class DealerInfoResource {

    private final Logger log = LoggerFactory.getLogger(DealerInfoResource.class);

    private static final String ENTITY_NAME = "dealerInfo";

    private final DealerInfoRepository dealerInfoRepository;
    public DealerInfoResource(DealerInfoRepository dealerInfoRepository) {
        this.dealerInfoRepository = dealerInfoRepository;
    }

    /**
     * POST  /dealer-infos : Create a new dealerInfo.
     *
     * @param dealerInfo the dealerInfo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dealerInfo, or with status 400 (Bad Request) if the dealerInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dealer-infos")
    @Timed
    public ResponseEntity<DealerInfo> createDealerInfo(@Valid @RequestBody DealerInfo dealerInfo) throws URISyntaxException {
        log.debug("REST request to save DealerInfo : {}", dealerInfo);
        if (dealerInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new dealerInfo cannot already have an ID")).body(null);
        }
        DealerInfo result = dealerInfoRepository.save(dealerInfo);
        return ResponseEntity.created(new URI("/api/dealer-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dealer-infos : Updates an existing dealerInfo.
     *
     * @param dealerInfo the dealerInfo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dealerInfo,
     * or with status 400 (Bad Request) if the dealerInfo is not valid,
     * or with status 500 (Internal Server Error) if the dealerInfo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dealer-infos")
    @Timed
    public ResponseEntity<DealerInfo> updateDealerInfo(@Valid @RequestBody DealerInfo dealerInfo) throws URISyntaxException {
        log.debug("REST request to update DealerInfo : {}", dealerInfo);
        if (dealerInfo.getId() == null) {
            return createDealerInfo(dealerInfo);
        }
        DealerInfo result = dealerInfoRepository.save(dealerInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dealerInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dealer-infos : get all the dealerInfos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of dealerInfos in body
     */
    @GetMapping("/dealer-infos")
    @Timed
    public List<DealerInfo> getAllDealerInfos() {
        log.debug("REST request to get all DealerInfos");
        return dealerInfoRepository.findAll();
        }

    /**
     * GET  /dealer-infos/:id : get the "id" dealerInfo.
     *
     * @param id the id of the dealerInfo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dealerInfo, or with status 404 (Not Found)
     */
    @GetMapping("/dealer-infos/{id}")
    @Timed
    public ResponseEntity<DealerInfo> getDealerInfo(@PathVariable Long id) {
        log.debug("REST request to get DealerInfo : {}", id);
        DealerInfo dealerInfo = dealerInfoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dealerInfo));
    }

    /**
     * DELETE  /dealer-infos/:id : delete the "id" dealerInfo.
     *
     * @param id the id of the dealerInfo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dealer-infos/{id}")
    @Timed
    public ResponseEntity<Void> deleteDealerInfo(@PathVariable Long id) {
        log.debug("REST request to delete DealerInfo : {}", id);
        dealerInfoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
