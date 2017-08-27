package com.emrgrp.ecom.shopcart.web.rest;

import com.emrgrp.ecom.shopcart.EmiratesGrpOnlineShopApp;

import com.emrgrp.ecom.shopcart.domain.CustomerInfo;
import com.emrgrp.ecom.shopcart.repository.CustomerInfoRepository;
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

/**
 * Test class for the CustomerInfoResource REST controller.
 *
 * @see CustomerInfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EmiratesGrpOnlineShopApp.class)
public class CustomerInfoResourceIntTest {

    private static final String DEFAULT_ALIAS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ALIAS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_LOGIN = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_LOGIN = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private CustomerInfoRepository customerInfoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCustomerInfoMockMvc;

    private CustomerInfo customerInfo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CustomerInfoResource customerInfoResource = new CustomerInfoResource(customerInfoRepository);
        this.restCustomerInfoMockMvc = MockMvcBuilders.standaloneSetup(customerInfoResource)
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
    public static CustomerInfo createEntity(EntityManager em) {
        CustomerInfo customerInfo = new CustomerInfo()
            .aliasName(DEFAULT_ALIAS_NAME)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .lastLogin(DEFAULT_LAST_LOGIN);
        return customerInfo;
    }

    @Before
    public void initTest() {
        customerInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerInfo() throws Exception {
        int databaseSizeBeforeCreate = customerInfoRepository.findAll().size();

        // Create the CustomerInfo
        restCustomerInfoMockMvc.perform(post("/api/customer-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerInfo)))
            .andExpect(status().isCreated());

        // Validate the CustomerInfo in the database
        List<CustomerInfo> customerInfoList = customerInfoRepository.findAll();
        assertThat(customerInfoList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerInfo testCustomerInfo = customerInfoList.get(customerInfoList.size() - 1);
        assertThat(testCustomerInfo.getAliasName()).isEqualTo(DEFAULT_ALIAS_NAME);
        assertThat(testCustomerInfo.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testCustomerInfo.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testCustomerInfo.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCustomerInfo.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testCustomerInfo.getLastLogin()).isEqualTo(DEFAULT_LAST_LOGIN);
    }

    @Test
    @Transactional
    public void createCustomerInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerInfoRepository.findAll().size();

        // Create the CustomerInfo with an existing ID
        customerInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerInfoMockMvc.perform(post("/api/customer-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerInfo)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CustomerInfo> customerInfoList = customerInfoRepository.findAll();
        assertThat(customerInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAliasNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerInfoRepository.findAll().size();
        // set the field null
        customerInfo.setAliasName(null);

        // Create the CustomerInfo, which fails.

        restCustomerInfoMockMvc.perform(post("/api/customer-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerInfo)))
            .andExpect(status().isBadRequest());

        List<CustomerInfo> customerInfoList = customerInfoRepository.findAll();
        assertThat(customerInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerInfoRepository.findAll().size();
        // set the field null
        customerInfo.setPhoneNumber(null);

        // Create the CustomerInfo, which fails.

        restCustomerInfoMockMvc.perform(post("/api/customer-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerInfo)))
            .andExpect(status().isBadRequest());

        List<CustomerInfo> customerInfoList = customerInfoRepository.findAll();
        assertThat(customerInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomerInfos() throws Exception {
        // Initialize the database
        customerInfoRepository.saveAndFlush(customerInfo);

        // Get all the customerInfoList
        restCustomerInfoMockMvc.perform(get("/api/customer-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].aliasName").value(hasItem(DEFAULT_ALIAS_NAME.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].lastLogin").value(hasItem(sameInstant(DEFAULT_LAST_LOGIN))));
    }

    @Test
    @Transactional
    public void getCustomerInfo() throws Exception {
        // Initialize the database
        customerInfoRepository.saveAndFlush(customerInfo);

        // Get the customerInfo
        restCustomerInfoMockMvc.perform(get("/api/customer-infos/{id}", customerInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customerInfo.getId().intValue()))
            .andExpect(jsonPath("$.aliasName").value(DEFAULT_ALIAS_NAME.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.lastLogin").value(sameInstant(DEFAULT_LAST_LOGIN)));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerInfo() throws Exception {
        // Get the customerInfo
        restCustomerInfoMockMvc.perform(get("/api/customer-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerInfo() throws Exception {
        // Initialize the database
        customerInfoRepository.saveAndFlush(customerInfo);
        int databaseSizeBeforeUpdate = customerInfoRepository.findAll().size();

        // Update the customerInfo
        CustomerInfo updatedCustomerInfo = customerInfoRepository.findOne(customerInfo.getId());
        updatedCustomerInfo
            .aliasName(UPDATED_ALIAS_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .lastLogin(UPDATED_LAST_LOGIN);

        restCustomerInfoMockMvc.perform(put("/api/customer-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomerInfo)))
            .andExpect(status().isOk());

        // Validate the CustomerInfo in the database
        List<CustomerInfo> customerInfoList = customerInfoRepository.findAll();
        assertThat(customerInfoList).hasSize(databaseSizeBeforeUpdate);
        CustomerInfo testCustomerInfo = customerInfoList.get(customerInfoList.size() - 1);
        assertThat(testCustomerInfo.getAliasName()).isEqualTo(UPDATED_ALIAS_NAME);
        assertThat(testCustomerInfo.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testCustomerInfo.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCustomerInfo.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCustomerInfo.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testCustomerInfo.getLastLogin()).isEqualTo(UPDATED_LAST_LOGIN);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerInfo() throws Exception {
        int databaseSizeBeforeUpdate = customerInfoRepository.findAll().size();

        // Create the CustomerInfo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCustomerInfoMockMvc.perform(put("/api/customer-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerInfo)))
            .andExpect(status().isCreated());

        // Validate the CustomerInfo in the database
        List<CustomerInfo> customerInfoList = customerInfoRepository.findAll();
        assertThat(customerInfoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCustomerInfo() throws Exception {
        // Initialize the database
        customerInfoRepository.saveAndFlush(customerInfo);
        int databaseSizeBeforeDelete = customerInfoRepository.findAll().size();

        // Get the customerInfo
        restCustomerInfoMockMvc.perform(delete("/api/customer-infos/{id}", customerInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CustomerInfo> customerInfoList = customerInfoRepository.findAll();
        assertThat(customerInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerInfo.class);
        CustomerInfo customerInfo1 = new CustomerInfo();
        customerInfo1.setId(1L);
        CustomerInfo customerInfo2 = new CustomerInfo();
        customerInfo2.setId(customerInfo1.getId());
        assertThat(customerInfo1).isEqualTo(customerInfo2);
        customerInfo2.setId(2L);
        assertThat(customerInfo1).isNotEqualTo(customerInfo2);
        customerInfo1.setId(null);
        assertThat(customerInfo1).isNotEqualTo(customerInfo2);
    }
}
