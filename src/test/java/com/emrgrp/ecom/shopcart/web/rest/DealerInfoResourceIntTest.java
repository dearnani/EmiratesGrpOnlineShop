package com.emrgrp.ecom.shopcart.web.rest;

import com.emrgrp.ecom.shopcart.EmiratesGrpOnlineShopApp;

import com.emrgrp.ecom.shopcart.domain.DealerInfo;
import com.emrgrp.ecom.shopcart.repository.DealerInfoRepository;
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

import com.emrgrp.ecom.shopcart.domain.enumeration.DealerStatus;
/**
 * Test class for the DealerInfoResource REST controller.
 *
 * @see DealerInfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EmiratesGrpOnlineShopApp.class)
public class DealerInfoResourceIntTest {

    private static final String DEFAULT_DEALER_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DEALER_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DEALER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DEALER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DEAR_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_DEAR_LOCATION = "BBBBBBBBBB";

    private static final DealerStatus DEFAULT_DEALER_STATUS = DealerStatus.ACIVE;
    private static final DealerStatus UPDATED_DEALER_STATUS = DealerStatus.INACTIVE;

    @Autowired
    private DealerInfoRepository dealerInfoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDealerInfoMockMvc;

    private DealerInfo dealerInfo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DealerInfoResource dealerInfoResource = new DealerInfoResource(dealerInfoRepository);
        this.restDealerInfoMockMvc = MockMvcBuilders.standaloneSetup(dealerInfoResource)
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
    public static DealerInfo createEntity(EntityManager em) {
        DealerInfo dealerInfo = new DealerInfo()
            .dealerCode(DEFAULT_DEALER_CODE)
            .dealerName(DEFAULT_DEALER_NAME)
            .dearLocation(DEFAULT_DEAR_LOCATION)
            .dealerStatus(DEFAULT_DEALER_STATUS);
        return dealerInfo;
    }

    @Before
    public void initTest() {
        dealerInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createDealerInfo() throws Exception {
        int databaseSizeBeforeCreate = dealerInfoRepository.findAll().size();

        // Create the DealerInfo
        restDealerInfoMockMvc.perform(post("/api/dealer-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dealerInfo)))
            .andExpect(status().isCreated());

        // Validate the DealerInfo in the database
        List<DealerInfo> dealerInfoList = dealerInfoRepository.findAll();
        assertThat(dealerInfoList).hasSize(databaseSizeBeforeCreate + 1);
        DealerInfo testDealerInfo = dealerInfoList.get(dealerInfoList.size() - 1);
        assertThat(testDealerInfo.getDealerCode()).isEqualTo(DEFAULT_DEALER_CODE);
        assertThat(testDealerInfo.getDealerName()).isEqualTo(DEFAULT_DEALER_NAME);
        assertThat(testDealerInfo.getDearLocation()).isEqualTo(DEFAULT_DEAR_LOCATION);
        assertThat(testDealerInfo.getDealerStatus()).isEqualTo(DEFAULT_DEALER_STATUS);
    }

    @Test
    @Transactional
    public void createDealerInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dealerInfoRepository.findAll().size();

        // Create the DealerInfo with an existing ID
        dealerInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDealerInfoMockMvc.perform(post("/api/dealer-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dealerInfo)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<DealerInfo> dealerInfoList = dealerInfoRepository.findAll();
        assertThat(dealerInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDealerCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = dealerInfoRepository.findAll().size();
        // set the field null
        dealerInfo.setDealerCode(null);

        // Create the DealerInfo, which fails.

        restDealerInfoMockMvc.perform(post("/api/dealer-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dealerInfo)))
            .andExpect(status().isBadRequest());

        List<DealerInfo> dealerInfoList = dealerInfoRepository.findAll();
        assertThat(dealerInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDealerStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = dealerInfoRepository.findAll().size();
        // set the field null
        dealerInfo.setDealerStatus(null);

        // Create the DealerInfo, which fails.

        restDealerInfoMockMvc.perform(post("/api/dealer-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dealerInfo)))
            .andExpect(status().isBadRequest());

        List<DealerInfo> dealerInfoList = dealerInfoRepository.findAll();
        assertThat(dealerInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDealerInfos() throws Exception {
        // Initialize the database
        dealerInfoRepository.saveAndFlush(dealerInfo);

        // Get all the dealerInfoList
        restDealerInfoMockMvc.perform(get("/api/dealer-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dealerInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].dealerCode").value(hasItem(DEFAULT_DEALER_CODE.toString())))
            .andExpect(jsonPath("$.[*].dealerName").value(hasItem(DEFAULT_DEALER_NAME.toString())))
            .andExpect(jsonPath("$.[*].dearLocation").value(hasItem(DEFAULT_DEAR_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].dealerStatus").value(hasItem(DEFAULT_DEALER_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getDealerInfo() throws Exception {
        // Initialize the database
        dealerInfoRepository.saveAndFlush(dealerInfo);

        // Get the dealerInfo
        restDealerInfoMockMvc.perform(get("/api/dealer-infos/{id}", dealerInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dealerInfo.getId().intValue()))
            .andExpect(jsonPath("$.dealerCode").value(DEFAULT_DEALER_CODE.toString()))
            .andExpect(jsonPath("$.dealerName").value(DEFAULT_DEALER_NAME.toString()))
            .andExpect(jsonPath("$.dearLocation").value(DEFAULT_DEAR_LOCATION.toString()))
            .andExpect(jsonPath("$.dealerStatus").value(DEFAULT_DEALER_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDealerInfo() throws Exception {
        // Get the dealerInfo
        restDealerInfoMockMvc.perform(get("/api/dealer-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDealerInfo() throws Exception {
        // Initialize the database
        dealerInfoRepository.saveAndFlush(dealerInfo);
        int databaseSizeBeforeUpdate = dealerInfoRepository.findAll().size();

        // Update the dealerInfo
        DealerInfo updatedDealerInfo = dealerInfoRepository.findOne(dealerInfo.getId());
        updatedDealerInfo
            .dealerCode(UPDATED_DEALER_CODE)
            .dealerName(UPDATED_DEALER_NAME)
            .dearLocation(UPDATED_DEAR_LOCATION)
            .dealerStatus(UPDATED_DEALER_STATUS);

        restDealerInfoMockMvc.perform(put("/api/dealer-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDealerInfo)))
            .andExpect(status().isOk());

        // Validate the DealerInfo in the database
        List<DealerInfo> dealerInfoList = dealerInfoRepository.findAll();
        assertThat(dealerInfoList).hasSize(databaseSizeBeforeUpdate);
        DealerInfo testDealerInfo = dealerInfoList.get(dealerInfoList.size() - 1);
        assertThat(testDealerInfo.getDealerCode()).isEqualTo(UPDATED_DEALER_CODE);
        assertThat(testDealerInfo.getDealerName()).isEqualTo(UPDATED_DEALER_NAME);
        assertThat(testDealerInfo.getDearLocation()).isEqualTo(UPDATED_DEAR_LOCATION);
        assertThat(testDealerInfo.getDealerStatus()).isEqualTo(UPDATED_DEALER_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingDealerInfo() throws Exception {
        int databaseSizeBeforeUpdate = dealerInfoRepository.findAll().size();

        // Create the DealerInfo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDealerInfoMockMvc.perform(put("/api/dealer-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dealerInfo)))
            .andExpect(status().isCreated());

        // Validate the DealerInfo in the database
        List<DealerInfo> dealerInfoList = dealerInfoRepository.findAll();
        assertThat(dealerInfoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDealerInfo() throws Exception {
        // Initialize the database
        dealerInfoRepository.saveAndFlush(dealerInfo);
        int databaseSizeBeforeDelete = dealerInfoRepository.findAll().size();

        // Get the dealerInfo
        restDealerInfoMockMvc.perform(delete("/api/dealer-infos/{id}", dealerInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DealerInfo> dealerInfoList = dealerInfoRepository.findAll();
        assertThat(dealerInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DealerInfo.class);
        DealerInfo dealerInfo1 = new DealerInfo();
        dealerInfo1.setId(1L);
        DealerInfo dealerInfo2 = new DealerInfo();
        dealerInfo2.setId(dealerInfo1.getId());
        assertThat(dealerInfo1).isEqualTo(dealerInfo2);
        dealerInfo2.setId(2L);
        assertThat(dealerInfo1).isNotEqualTo(dealerInfo2);
        dealerInfo1.setId(null);
        assertThat(dealerInfo1).isNotEqualTo(dealerInfo2);
    }
}
