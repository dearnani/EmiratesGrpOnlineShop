package com.emrgrp.ecom.shopcart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emrgrp.ecom.shopcart.domain.DeliverManagement;

import com.emrgrp.ecom.shopcart.repository.DeliverManagementRepository;
import com.emrgrp.ecom.shopcart.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DeliverManagement.
 */
@RestController
@RequestMapping("/api")
public class DeliverManagementResource {

    private final Logger log = LoggerFactory.getLogger(DeliverManagementResource.class);

    private static final String ENTITY_NAME = "deliverManagement";

    private final DeliverManagementRepository deliverManagementRepository;
    public DeliverManagementResource(DeliverManagementRepository deliverManagementRepository) {
        this.deliverManagementRepository = deliverManagementRepository;
    }

    /**
     * POST  /deliver-managements : Create a new deliverManagement.
     *
     * @param deliverManagement the deliverManagement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new deliverManagement, or with status 400 (Bad Request) if the deliverManagement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/deliver-managements")
    @Timed
    public ResponseEntity<DeliverManagement> createDeliverManagement(@RequestBody DeliverManagement deliverManagement) throws URISyntaxException {
        log.debug("REST request to save DeliverManagement : {}", deliverManagement);
        if (deliverManagement.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new deliverManagement cannot already have an ID")).body(null);
        }
        DeliverManagement result = deliverManagementRepository.save(deliverManagement);
        return ResponseEntity.created(new URI("/api/deliver-managements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /deliver-managements : Updates an existing deliverManagement.
     *
     * @param deliverManagement the deliverManagement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated deliverManagement,
     * or with status 400 (Bad Request) if the deliverManagement is not valid,
     * or with status 500 (Internal Server Error) if the deliverManagement couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/deliver-managements")
    @Timed
    public ResponseEntity<DeliverManagement> updateDeliverManagement(@RequestBody DeliverManagement deliverManagement) throws URISyntaxException {
        log.debug("REST request to update DeliverManagement : {}", deliverManagement);
        if (deliverManagement.getId() == null) {
            return createDeliverManagement(deliverManagement);
        }
        DeliverManagement result = deliverManagementRepository.save(deliverManagement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, deliverManagement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /deliver-managements : get all the deliverManagements.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of deliverManagements in body
     */
    @GetMapping("/deliver-managements")
    @Timed
    public List<DeliverManagement> getAllDeliverManagements() {
        log.debug("REST request to get all DeliverManagements");
        return deliverManagementRepository.findAll();
        }

    /**
     * GET  /deliver-managements/:id : get the "id" deliverManagement.
     *
     * @param id the id of the deliverManagement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the deliverManagement, or with status 404 (Not Found)
     */
    @GetMapping("/deliver-managements/{id}")
    @Timed
    public ResponseEntity<DeliverManagement> getDeliverManagement(@PathVariable Long id) {
        log.debug("REST request to get DeliverManagement : {}", id);
        DeliverManagement deliverManagement = deliverManagementRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(deliverManagement));
    }

    /**
     * DELETE  /deliver-managements/:id : delete the "id" deliverManagement.
     *
     * @param id the id of the deliverManagement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/deliver-managements/{id}")
    @Timed
    public ResponseEntity<Void> deleteDeliverManagement(@PathVariable Long id) {
        log.debug("REST request to delete DeliverManagement : {}", id);
        deliverManagementRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
