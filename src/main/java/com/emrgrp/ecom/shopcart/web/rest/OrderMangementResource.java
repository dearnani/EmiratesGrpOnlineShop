package com.emrgrp.ecom.shopcart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emrgrp.ecom.shopcart.domain.OrderMangement;

import com.emrgrp.ecom.shopcart.repository.OrderMangementRepository;
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
 * REST controller for managing OrderMangement.
 */
@RestController
@RequestMapping("/api")
public class OrderMangementResource {

    private final Logger log = LoggerFactory.getLogger(OrderMangementResource.class);

    private static final String ENTITY_NAME = "orderMangement";

    private final OrderMangementRepository orderMangementRepository;
    public OrderMangementResource(OrderMangementRepository orderMangementRepository) {
        this.orderMangementRepository = orderMangementRepository;
    }

    /**
     * POST  /order-mangements : Create a new orderMangement.
     *
     * @param orderMangement the orderMangement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new orderMangement, or with status 400 (Bad Request) if the orderMangement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/order-mangements")
    @Timed
    public ResponseEntity<OrderMangement> createOrderMangement(@RequestBody OrderMangement orderMangement) throws URISyntaxException {
        log.debug("REST request to save OrderMangement : {}", orderMangement);
        if (orderMangement.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new orderMangement cannot already have an ID")).body(null);
        }
        OrderMangement result = orderMangementRepository.save(orderMangement);
        return ResponseEntity.created(new URI("/api/order-mangements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /order-mangements : Updates an existing orderMangement.
     *
     * @param orderMangement the orderMangement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated orderMangement,
     * or with status 400 (Bad Request) if the orderMangement is not valid,
     * or with status 500 (Internal Server Error) if the orderMangement couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/order-mangements")
    @Timed
    public ResponseEntity<OrderMangement> updateOrderMangement(@RequestBody OrderMangement orderMangement) throws URISyntaxException {
        log.debug("REST request to update OrderMangement : {}", orderMangement);
        if (orderMangement.getId() == null) {
            return createOrderMangement(orderMangement);
        }
        OrderMangement result = orderMangementRepository.save(orderMangement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, orderMangement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /order-mangements : get all the orderMangements.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of orderMangements in body
     */
    @GetMapping("/order-mangements")
    @Timed
    public List<OrderMangement> getAllOrderMangements() {
        log.debug("REST request to get all OrderMangements");
        return orderMangementRepository.findAll();
        }

    /**
     * GET  /order-mangements/:id : get the "id" orderMangement.
     *
     * @param id the id of the orderMangement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the orderMangement, or with status 404 (Not Found)
     */
    @GetMapping("/order-mangements/{id}")
    @Timed
    public ResponseEntity<OrderMangement> getOrderMangement(@PathVariable Long id) {
        log.debug("REST request to get OrderMangement : {}", id);
        OrderMangement orderMangement = orderMangementRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(orderMangement));
    }

    /**
     * DELETE  /order-mangements/:id : delete the "id" orderMangement.
     *
     * @param id the id of the orderMangement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/order-mangements/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrderMangement(@PathVariable Long id) {
        log.debug("REST request to delete OrderMangement : {}", id);
        orderMangementRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
