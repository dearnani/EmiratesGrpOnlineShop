package com.emrgrp.ecom.shopcart.web.rest;

import com.emrgrp.ecom.shopcart.EmiratesGrpOnlineShopApp;

import com.emrgrp.ecom.shopcart.domain.DeliverManagement;
import com.emrgrp.ecom.shopcart.repository.DeliverManagementRepository;
import com.emrgrp.ecom.shopcart.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.emrgrp.ecom.shopcart.domain.enumeration.DeliveryStatus;
/**
 * Test class for the DeliverManagementResource REST controller.
 *
 * @see DeliverManagementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EmiratesGrpOnlineShopApp.class)
public class DeliverManagementResourceIntTest {

    private static final Long DEFAULT_ORDER_NUMBER = 1L;
    private static final Long UPDATED_ORDER_NUMBER = 2L;

    private static final DeliveryStatus DEFAULT_DELIVERY_STATUS = DeliveryStatus.DISPATCHED;
    private static final DeliveryStatus UPDATED_DELIVERY_STATUS = DeliveryStatus.TRANSIT;

    @Autowired
    private DeliverManagementRepository deliverManagementRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDeliverManagementMockMvc;

    private DeliverManagement deliverManagement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DeliverManagementResource deliverManagementResource = new DeliverManagementResource(deliverManagementRepository);
        this.restDeliverManagementMockMvc = MockMvcBuilders.standaloneSetup(deliverManagementResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeliverManagement createEntity(EntityManager em) {
        DeliverManagement deliverManagement = new DeliverManagement()
            .orderNumber(DEFAULT_ORDER_NUMBER)
            .deliveryStatus(DEFAULT_DELIVERY_STATUS);
        return deliverManagement;
    }

    @Before
    public void initTest() {
        deliverManagement = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeliverManagement() throws Exception {
        int databaseSizeBeforeCreate = deliverManagementRepository.findAll().size();

        // Create the DeliverManagement
        restDeliverManagementMockMvc.perform(post("/api/deliver-managements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliverManagement)))
            .andExpect(status().isCreated());

        // Validate the DeliverManagement in the database
        List<DeliverManagement> deliverManagementList = deliverManagementRepository.findAll();
        assertThat(deliverManagementList).hasSize(databaseSizeBeforeCreate + 1);
        DeliverManagement testDeliverManagement = deliverManagementList.get(deliverManagementList.size() - 1);
        assertThat(testDeliverManagement.getOrderNumber()).isEqualTo(DEFAULT_ORDER_NUMBER);
        assertThat(testDeliverManagement.getDeliveryStatus()).isEqualTo(DEFAULT_DELIVERY_STATUS);
    }

    @Test
    @Transactional
    public void createDeliverManagementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deliverManagementRepository.findAll().size();

        // Create the DeliverManagement with an existing ID
        deliverManagement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeliverManagementMockMvc.perform(post("/api/deliver-managements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliverManagement)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<DeliverManagement> deliverManagementList = deliverManagementRepository.findAll();
        assertThat(deliverManagementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDeliverManagements() throws Exception {
        // Initialize the database
        deliverManagementRepository.saveAndFlush(deliverManagement);

        // Get all the deliverManagementList
        restDeliverManagementMockMvc.perform(get("/api/deliver-managements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deliverManagement.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderNumber").value(hasItem(DEFAULT_ORDER_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].deliveryStatus").value(hasItem(DEFAULT_DELIVERY_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getDeliverManagement() throws Exception {
        // Initialize the database
        deliverManagementRepository.saveAndFlush(deliverManagement);

        // Get the deliverManagement
        restDeliverManagementMockMvc.perform(get("/api/deliver-managements/{id}", deliverManagement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(deliverManagement.getId().intValue()))
            .andExpect(jsonPath("$.orderNumber").value(DEFAULT_ORDER_NUMBER.intValue()))
            .andExpect(jsonPath("$.deliveryStatus").value(DEFAULT_DELIVERY_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDeliverManagement() throws Exception {
        // Get the deliverManagement
        restDeliverManagementMockMvc.perform(get("/api/deliver-managements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeliverManagement() throws Exception {
        // Initialize the database
        deliverManagementRepository.saveAndFlush(deliverManagement);
        int databaseSizeBeforeUpdate = deliverManagementRepository.findAll().size();

        // Update the deliverManagement
        DeliverManagement updatedDeliverManagement = deliverManagementRepository.findOne(deliverManagement.getId());
        updatedDeliverManagement
            .orderNumber(UPDATED_ORDER_NUMBER)
            .deliveryStatus(UPDATED_DELIVERY_STATUS);

        restDeliverManagementMockMvc.perform(put("/api/deliver-managements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDeliverManagement)))
            .andExpect(status().isOk());

        // Validate the DeliverManagement in the database
        List<DeliverManagement> deliverManagementList = deliverManagementRepository.findAll();
        assertThat(deliverManagementList).hasSize(databaseSizeBeforeUpdate);
        DeliverManagement testDeliverManagement = deliverManagementList.get(deliverManagementList.size() - 1);
        assertThat(testDeliverManagement.getOrderNumber()).isEqualTo(UPDATED_ORDER_NUMBER);
        assertThat(testDeliverManagement.getDeliveryStatus()).isEqualTo(UPDATED_DELIVERY_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingDeliverManagement() throws Exception {
        int databaseSizeBeforeUpdate = deliverManagementRepository.findAll().size();

        // Create the DeliverManagement

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDeliverManagementMockMvc.perform(put("/api/deliver-managements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliverManagement)))
            .andExpect(status().isCreated());

        // Validate the DeliverManagement in the database
        List<DeliverManagement> deliverManagementList = deliverManagementRepository.findAll();
        assertThat(deliverManagementList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDeliverManagement() throws Exception {
        // Initialize the database
        deliverManagementRepository.saveAndFlush(deliverManagement);
        int databaseSizeBeforeDelete = deliverManagementRepository.findAll().size();

        // Get the deliverManagement
        restDeliverManagementMockMvc.perform(delete("/api/deliver-managements/{id}", deliverManagement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DeliverManagement> deliverManagementList = deliverManagementRepository.findAll();
        assertThat(deliverManagementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeliverManagement.class);
        DeliverManagement deliverManagement1 = new DeliverManagement();
        deliverManagement1.setId(1L);
        DeliverManagement deliverManagement2 = new DeliverManagement();
        deliverManagement2.setId(deliverManagement1.getId());
        assertThat(deliverManagement1).isEqualTo(deliverManagement2);
        deliverManagement2.setId(2L);
        assertThat(deliverManagement1).isNotEqualTo(deliverManagement2);
        deliverManagement1.setId(null);
        assertThat(deliverManagement1).isNotEqualTo(deliverManagement2);
    }
}
