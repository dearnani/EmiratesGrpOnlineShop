package com.emrgrp.ecom.shopcart.web.rest;

import com.emrgrp.ecom.shopcart.EmiratesGrpOnlineShopApp;

import com.emrgrp.ecom.shopcart.domain.OrderMangement;
import com.emrgrp.ecom.shopcart.repository.OrderMangementRepository;
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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.emrgrp.ecom.shopcart.domain.enumeration.OrderStatus;
import com.emrgrp.ecom.shopcart.domain.enumeration.PaymentStatus;
/**
 * Test class for the OrderMangementResource REST controller.
 *
 * @see OrderMangementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EmiratesGrpOnlineShopApp.class)
public class OrderMangementResourceIntTest {

    private static final Long DEFAULT_ORDER_ID = 1L;
    private static final Long UPDATED_ORDER_ID = 2L;

    private static final Long DEFAULT_PRODUCT_CODE = 1L;
    private static final Long UPDATED_PRODUCT_CODE = 2L;

    private static final String DEFAULT_DEALER_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DEALER_CODE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_ASSIGNED_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_ASSIGNED_PRICE = new BigDecimal(2);

    private static final String DEFAULT_DEAR_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_DEAR_LOCATION = "BBBBBBBBBB";

    private static final OrderStatus DEFAULT_ORDER_STATUS = OrderStatus.SAVE;
    private static final OrderStatus UPDATED_ORDER_STATUS = OrderStatus.SUCESS;

    private static final PaymentStatus DEFAULT_PAYMENT_STATUS = PaymentStatus.SUCCESS;
    private static final PaymentStatus UPDATED_PAYMENT_STATUS = PaymentStatus.FAIL;

    @Autowired
    private OrderMangementRepository orderMangementRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrderMangementMockMvc;

    private OrderMangement orderMangement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrderMangementResource orderMangementResource = new OrderMangementResource(orderMangementRepository);
        this.restOrderMangementMockMvc = MockMvcBuilders.standaloneSetup(orderMangementResource)
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
    public static OrderMangement createEntity(EntityManager em) {
        OrderMangement orderMangement = new OrderMangement()
            .orderId(DEFAULT_ORDER_ID)
            .productCode(DEFAULT_PRODUCT_CODE)
            .dealerCode(DEFAULT_DEALER_CODE)
            .assignedPrice(DEFAULT_ASSIGNED_PRICE)
            .dearLocation(DEFAULT_DEAR_LOCATION)
            .orderStatus(DEFAULT_ORDER_STATUS)
            .paymentStatus(DEFAULT_PAYMENT_STATUS);
        return orderMangement;
    }

    @Before
    public void initTest() {
        orderMangement = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderMangement() throws Exception {
        int databaseSizeBeforeCreate = orderMangementRepository.findAll().size();

        // Create the OrderMangement
        restOrderMangementMockMvc.perform(post("/api/order-mangements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderMangement)))
            .andExpect(status().isCreated());

        // Validate the OrderMangement in the database
        List<OrderMangement> orderMangementList = orderMangementRepository.findAll();
        assertThat(orderMangementList).hasSize(databaseSizeBeforeCreate + 1);
        OrderMangement testOrderMangement = orderMangementList.get(orderMangementList.size() - 1);
        assertThat(testOrderMangement.getOrderId()).isEqualTo(DEFAULT_ORDER_ID);
        assertThat(testOrderMangement.getProductCode()).isEqualTo(DEFAULT_PRODUCT_CODE);
        assertThat(testOrderMangement.getDealerCode()).isEqualTo(DEFAULT_DEALER_CODE);
        assertThat(testOrderMangement.getAssignedPrice()).isEqualTo(DEFAULT_ASSIGNED_PRICE);
        assertThat(testOrderMangement.getDearLocation()).isEqualTo(DEFAULT_DEAR_LOCATION);
        assertThat(testOrderMangement.getOrderStatus()).isEqualTo(DEFAULT_ORDER_STATUS);
        assertThat(testOrderMangement.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    public void createOrderMangementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderMangementRepository.findAll().size();

        // Create the OrderMangement with an existing ID
        orderMangement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderMangementMockMvc.perform(post("/api/order-mangements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderMangement)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<OrderMangement> orderMangementList = orderMangementRepository.findAll();
        assertThat(orderMangementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOrderMangements() throws Exception {
        // Initialize the database
        orderMangementRepository.saveAndFlush(orderMangement);

        // Get all the orderMangementList
        restOrderMangementMockMvc.perform(get("/api/order-mangements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderMangement.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderId").value(hasItem(DEFAULT_ORDER_ID.intValue())))
            .andExpect(jsonPath("$.[*].productCode").value(hasItem(DEFAULT_PRODUCT_CODE.intValue())))
            .andExpect(jsonPath("$.[*].dealerCode").value(hasItem(DEFAULT_DEALER_CODE.toString())))
            .andExpect(jsonPath("$.[*].assignedPrice").value(hasItem(DEFAULT_ASSIGNED_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].dearLocation").value(hasItem(DEFAULT_DEAR_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].orderStatus").value(hasItem(DEFAULT_ORDER_STATUS.toString())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getOrderMangement() throws Exception {
        // Initialize the database
        orderMangementRepository.saveAndFlush(orderMangement);

        // Get the orderMangement
        restOrderMangementMockMvc.perform(get("/api/order-mangements/{id}", orderMangement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderMangement.getId().intValue()))
            .andExpect(jsonPath("$.orderId").value(DEFAULT_ORDER_ID.intValue()))
            .andExpect(jsonPath("$.productCode").value(DEFAULT_PRODUCT_CODE.intValue()))
            .andExpect(jsonPath("$.dealerCode").value(DEFAULT_DEALER_CODE.toString()))
            .andExpect(jsonPath("$.assignedPrice").value(DEFAULT_ASSIGNED_PRICE.intValue()))
            .andExpect(jsonPath("$.dearLocation").value(DEFAULT_DEAR_LOCATION.toString()))
            .andExpect(jsonPath("$.orderStatus").value(DEFAULT_ORDER_STATUS.toString()))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderMangement() throws Exception {
        // Get the orderMangement
        restOrderMangementMockMvc.perform(get("/api/order-mangements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderMangement() throws Exception {
        // Initialize the database
        orderMangementRepository.saveAndFlush(orderMangement);
        int databaseSizeBeforeUpdate = orderMangementRepository.findAll().size();

        // Update the orderMangement
        OrderMangement updatedOrderMangement = orderMangementRepository.findOne(orderMangement.getId());
        updatedOrderMangement
            .orderId(UPDATED_ORDER_ID)
            .productCode(UPDATED_PRODUCT_CODE)
            .dealerCode(UPDATED_DEALER_CODE)
            .assignedPrice(UPDATED_ASSIGNED_PRICE)
            .dearLocation(UPDATED_DEAR_LOCATION)
            .orderStatus(UPDATED_ORDER_STATUS)
            .paymentStatus(UPDATED_PAYMENT_STATUS);

        restOrderMangementMockMvc.perform(put("/api/order-mangements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrderMangement)))
            .andExpect(status().isOk());

        // Validate the OrderMangement in the database
        List<OrderMangement> orderMangementList = orderMangementRepository.findAll();
        assertThat(orderMangementList).hasSize(databaseSizeBeforeUpdate);
        OrderMangement testOrderMangement = orderMangementList.get(orderMangementList.size() - 1);
        assertThat(testOrderMangement.getOrderId()).isEqualTo(UPDATED_ORDER_ID);
        assertThat(testOrderMangement.getProductCode()).isEqualTo(UPDATED_PRODUCT_CODE);
        assertThat(testOrderMangement.getDealerCode()).isEqualTo(UPDATED_DEALER_CODE);
        assertThat(testOrderMangement.getAssignedPrice()).isEqualTo(UPDATED_ASSIGNED_PRICE);
        assertThat(testOrderMangement.getDearLocation()).isEqualTo(UPDATED_DEAR_LOCATION);
        assertThat(testOrderMangement.getOrderStatus()).isEqualTo(UPDATED_ORDER_STATUS);
        assertThat(testOrderMangement.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderMangement() throws Exception {
        int databaseSizeBeforeUpdate = orderMangementRepository.findAll().size();

        // Create the OrderMangement

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOrderMangementMockMvc.perform(put("/api/order-mangements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderMangement)))
            .andExpect(status().isCreated());

        // Validate the OrderMangement in the database
        List<OrderMangement> orderMangementList = orderMangementRepository.findAll();
        assertThat(orderMangementList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOrderMangement() throws Exception {
        // Initialize the database
        orderMangementRepository.saveAndFlush(orderMangement);
        int databaseSizeBeforeDelete = orderMangementRepository.findAll().size();

        // Get the orderMangement
        restOrderMangementMockMvc.perform(delete("/api/order-mangements/{id}", orderMangement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OrderMangement> orderMangementList = orderMangementRepository.findAll();
        assertThat(orderMangementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderMangement.class);
        OrderMangement orderMangement1 = new OrderMangement();
        orderMangement1.setId(1L);
        OrderMangement orderMangement2 = new OrderMangement();
        orderMangement2.setId(orderMangement1.getId());
        assertThat(orderMangement1).isEqualTo(orderMangement2);
        orderMangement2.setId(2L);
        assertThat(orderMangement1).isNotEqualTo(orderMangement2);
        orderMangement1.setId(null);
        assertThat(orderMangement1).isNotEqualTo(orderMangement2);
    }
}
