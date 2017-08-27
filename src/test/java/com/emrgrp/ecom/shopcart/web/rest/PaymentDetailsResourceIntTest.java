package com.emrgrp.ecom.shopcart.web.rest;

import com.emrgrp.ecom.shopcart.EmiratesGrpOnlineShopApp;

import com.emrgrp.ecom.shopcart.domain.PaymentDetails;
import com.emrgrp.ecom.shopcart.repository.PaymentDetailsRepository;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.emrgrp.ecom.shopcart.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.emrgrp.ecom.shopcart.domain.enumeration.PaymentChannel;
import com.emrgrp.ecom.shopcart.domain.enumeration.PaymentStatus;
/**
 * Test class for the PaymentDetailsResource REST controller.
 *
 * @see PaymentDetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EmiratesGrpOnlineShopApp.class)
public class PaymentDetailsResourceIntTest {

    private static final PaymentChannel DEFAULT_PAYMENT_MODE = PaymentChannel.CASHON_DELIVERY;
    private static final PaymentChannel UPDATED_PAYMENT_MODE = PaymentChannel.ONLINE_CREDITCARD;

    private static final PaymentStatus DEFAULT_PAYMENT_STATUS = PaymentStatus.SUCCESS;
    private static final PaymentStatus UPDATED_PAYMENT_STATUS = PaymentStatus.FAIL;

    private static final String DEFAULT_ODER_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ODER_NUMBER = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_ODER_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ODER_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_NUMBER_OF_PRODUCTS = 1;
    private static final Integer UPDATED_NUMBER_OF_PRODUCTS = 2;

    private static final Long DEFAULT_TOTAL_AMOUNT = 1L;
    private static final Long UPDATED_TOTAL_AMOUNT = 2L;

    @Autowired
    private PaymentDetailsRepository paymentDetailsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPaymentDetailsMockMvc;

    private PaymentDetails paymentDetails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PaymentDetailsResource paymentDetailsResource = new PaymentDetailsResource(paymentDetailsRepository);
        this.restPaymentDetailsMockMvc = MockMvcBuilders.standaloneSetup(paymentDetailsResource)
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
    public static PaymentDetails createEntity(EntityManager em) {
        PaymentDetails paymentDetails = new PaymentDetails()
            .paymentMode(DEFAULT_PAYMENT_MODE)
            .paymentStatus(DEFAULT_PAYMENT_STATUS)
            .oderNumber(DEFAULT_ODER_NUMBER)
            .oderDate(DEFAULT_ODER_DATE)
            .numberOfProducts(DEFAULT_NUMBER_OF_PRODUCTS)
            .totalAmount(DEFAULT_TOTAL_AMOUNT);
        return paymentDetails;
    }

    @Before
    public void initTest() {
        paymentDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaymentDetails() throws Exception {
        int databaseSizeBeforeCreate = paymentDetailsRepository.findAll().size();

        // Create the PaymentDetails
        restPaymentDetailsMockMvc.perform(post("/api/payment-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentDetails)))
            .andExpect(status().isCreated());

        // Validate the PaymentDetails in the database
        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentDetails testPaymentDetails = paymentDetailsList.get(paymentDetailsList.size() - 1);
        assertThat(testPaymentDetails.getPaymentMode()).isEqualTo(DEFAULT_PAYMENT_MODE);
        assertThat(testPaymentDetails.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
        assertThat(testPaymentDetails.getOderNumber()).isEqualTo(DEFAULT_ODER_NUMBER);
        assertThat(testPaymentDetails.getOderDate()).isEqualTo(DEFAULT_ODER_DATE);
        assertThat(testPaymentDetails.getNumberOfProducts()).isEqualTo(DEFAULT_NUMBER_OF_PRODUCTS);
        assertThat(testPaymentDetails.getTotalAmount()).isEqualTo(DEFAULT_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    public void createPaymentDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentDetailsRepository.findAll().size();

        // Create the PaymentDetails with an existing ID
        paymentDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentDetailsMockMvc.perform(post("/api/payment-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentDetails)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPaymentModeIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentDetailsRepository.findAll().size();
        // set the field null
        paymentDetails.setPaymentMode(null);

        // Create the PaymentDetails, which fails.

        restPaymentDetailsMockMvc.perform(post("/api/payment-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentDetails)))
            .andExpect(status().isBadRequest());

        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPaymentStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentDetailsRepository.findAll().size();
        // set the field null
        paymentDetails.setPaymentStatus(null);

        // Create the PaymentDetails, which fails.

        restPaymentDetailsMockMvc.perform(post("/api/payment-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentDetails)))
            .andExpect(status().isBadRequest());

        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOderNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentDetailsRepository.findAll().size();
        // set the field null
        paymentDetails.setOderNumber(null);

        // Create the PaymentDetails, which fails.

        restPaymentDetailsMockMvc.perform(post("/api/payment-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentDetails)))
            .andExpect(status().isBadRequest());

        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumberOfProductsIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentDetailsRepository.findAll().size();
        // set the field null
        paymentDetails.setNumberOfProducts(null);

        // Create the PaymentDetails, which fails.

        restPaymentDetailsMockMvc.perform(post("/api/payment-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentDetails)))
            .andExpect(status().isBadRequest());

        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentDetailsRepository.findAll().size();
        // set the field null
        paymentDetails.setTotalAmount(null);

        // Create the PaymentDetails, which fails.

        restPaymentDetailsMockMvc.perform(post("/api/payment-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentDetails)))
            .andExpect(status().isBadRequest());

        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPaymentDetails() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);

        // Get all the paymentDetailsList
        restPaymentDetailsMockMvc.perform(get("/api/payment-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentMode").value(hasItem(DEFAULT_PAYMENT_MODE.toString())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].oderNumber").value(hasItem(DEFAULT_ODER_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].oderDate").value(hasItem(sameInstant(DEFAULT_ODER_DATE))))
            .andExpect(jsonPath("$.[*].numberOfProducts").value(hasItem(DEFAULT_NUMBER_OF_PRODUCTS)))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.intValue())));
    }

    @Test
    @Transactional
    public void getPaymentDetails() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);

        // Get the paymentDetails
        restPaymentDetailsMockMvc.perform(get("/api/payment-details/{id}", paymentDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paymentDetails.getId().intValue()))
            .andExpect(jsonPath("$.paymentMode").value(DEFAULT_PAYMENT_MODE.toString()))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS.toString()))
            .andExpect(jsonPath("$.oderNumber").value(DEFAULT_ODER_NUMBER.toString()))
            .andExpect(jsonPath("$.oderDate").value(sameInstant(DEFAULT_ODER_DATE)))
            .andExpect(jsonPath("$.numberOfProducts").value(DEFAULT_NUMBER_OF_PRODUCTS))
            .andExpect(jsonPath("$.totalAmount").value(DEFAULT_TOTAL_AMOUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPaymentDetails() throws Exception {
        // Get the paymentDetails
        restPaymentDetailsMockMvc.perform(get("/api/payment-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaymentDetails() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);
        int databaseSizeBeforeUpdate = paymentDetailsRepository.findAll().size();

        // Update the paymentDetails
        PaymentDetails updatedPaymentDetails = paymentDetailsRepository.findOne(paymentDetails.getId());
        updatedPaymentDetails
            .paymentMode(UPDATED_PAYMENT_MODE)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .oderNumber(UPDATED_ODER_NUMBER)
            .oderDate(UPDATED_ODER_DATE)
            .numberOfProducts(UPDATED_NUMBER_OF_PRODUCTS)
            .totalAmount(UPDATED_TOTAL_AMOUNT);

        restPaymentDetailsMockMvc.perform(put("/api/payment-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPaymentDetails)))
            .andExpect(status().isOk());

        // Validate the PaymentDetails in the database
        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeUpdate);
        PaymentDetails testPaymentDetails = paymentDetailsList.get(paymentDetailsList.size() - 1);
        assertThat(testPaymentDetails.getPaymentMode()).isEqualTo(UPDATED_PAYMENT_MODE);
        assertThat(testPaymentDetails.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
        assertThat(testPaymentDetails.getOderNumber()).isEqualTo(UPDATED_ODER_NUMBER);
        assertThat(testPaymentDetails.getOderDate()).isEqualTo(UPDATED_ODER_DATE);
        assertThat(testPaymentDetails.getNumberOfProducts()).isEqualTo(UPDATED_NUMBER_OF_PRODUCTS);
        assertThat(testPaymentDetails.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingPaymentDetails() throws Exception {
        int databaseSizeBeforeUpdate = paymentDetailsRepository.findAll().size();

        // Create the PaymentDetails

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPaymentDetailsMockMvc.perform(put("/api/payment-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentDetails)))
            .andExpect(status().isCreated());

        // Validate the PaymentDetails in the database
        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePaymentDetails() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);
        int databaseSizeBeforeDelete = paymentDetailsRepository.findAll().size();

        // Get the paymentDetails
        restPaymentDetailsMockMvc.perform(delete("/api/payment-details/{id}", paymentDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentDetails.class);
        PaymentDetails paymentDetails1 = new PaymentDetails();
        paymentDetails1.setId(1L);
        PaymentDetails paymentDetails2 = new PaymentDetails();
        paymentDetails2.setId(paymentDetails1.getId());
        assertThat(paymentDetails1).isEqualTo(paymentDetails2);
        paymentDetails2.setId(2L);
        assertThat(paymentDetails1).isNotEqualTo(paymentDetails2);
        paymentDetails1.setId(null);
        assertThat(paymentDetails1).isNotEqualTo(paymentDetails2);
    }
}
