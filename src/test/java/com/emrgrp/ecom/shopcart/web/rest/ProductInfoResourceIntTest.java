package com.emrgrp.ecom.shopcart.web.rest;

import com.emrgrp.ecom.shopcart.EmiratesGrpOnlineShopApp;

import com.emrgrp.ecom.shopcart.domain.ProductInfo;
import com.emrgrp.ecom.shopcart.repository.ProductInfoRepository;
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

import com.emrgrp.ecom.shopcart.domain.enumeration.ProductType;
import com.emrgrp.ecom.shopcart.domain.enumeration.ProductStatus;
/**
 * Test class for the ProductInfoResource REST controller.
 *
 * @see ProductInfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EmiratesGrpOnlineShopApp.class)
public class ProductInfoResourceIntTest {

    private static final String DEFAULT_PROUCT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PROUCT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PROUDCT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PROUDCT_NAME = "BBBBBBBBBB";

    private static final ProductType DEFAULT_PRODUCT_TYPE = ProductType.ELECTRONICS;
    private static final ProductType UPDATED_PRODUCT_TYPE = ProductType.HOMEAPPLIANCES;

    private static final String DEFAULT_PRODUCT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_DESCRIPTION = "BBBBBBBBBB";

    private static final ProductStatus DEFAULT_PRODUCT_STATUS = ProductStatus.AVAILABLE;
    private static final ProductStatus UPDATED_PRODUCT_STATUS = ProductStatus.NOT_AVAILABLE;

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProductInfoMockMvc;

    private ProductInfo productInfo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductInfoResource productInfoResource = new ProductInfoResource(productInfoRepository);
        this.restProductInfoMockMvc = MockMvcBuilders.standaloneSetup(productInfoResource)
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
    public static ProductInfo createEntity(EntityManager em) {
        ProductInfo productInfo = new ProductInfo()
            .prouctCode(DEFAULT_PROUCT_CODE)
            .proudctName(DEFAULT_PROUDCT_NAME)
            .productType(DEFAULT_PRODUCT_TYPE)
            .productDescription(DEFAULT_PRODUCT_DESCRIPTION)
            .productStatus(DEFAULT_PRODUCT_STATUS);
        return productInfo;
    }

    @Before
    public void initTest() {
        productInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductInfo() throws Exception {
        int databaseSizeBeforeCreate = productInfoRepository.findAll().size();

        // Create the ProductInfo
        restProductInfoMockMvc.perform(post("/api/product-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productInfo)))
            .andExpect(status().isCreated());

        // Validate the ProductInfo in the database
        List<ProductInfo> productInfoList = productInfoRepository.findAll();
        assertThat(productInfoList).hasSize(databaseSizeBeforeCreate + 1);
        ProductInfo testProductInfo = productInfoList.get(productInfoList.size() - 1);
        assertThat(testProductInfo.getProuctCode()).isEqualTo(DEFAULT_PROUCT_CODE);
        assertThat(testProductInfo.getProudctName()).isEqualTo(DEFAULT_PROUDCT_NAME);
        assertThat(testProductInfo.getProductType()).isEqualTo(DEFAULT_PRODUCT_TYPE);
        assertThat(testProductInfo.getProductDescription()).isEqualTo(DEFAULT_PRODUCT_DESCRIPTION);
        assertThat(testProductInfo.getProductStatus()).isEqualTo(DEFAULT_PRODUCT_STATUS);
    }

    @Test
    @Transactional
    public void createProductInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productInfoRepository.findAll().size();

        // Create the ProductInfo with an existing ID
        productInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductInfoMockMvc.perform(post("/api/product-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productInfo)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ProductInfo> productInfoList = productInfoRepository.findAll();
        assertThat(productInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkProuctCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = productInfoRepository.findAll().size();
        // set the field null
        productInfo.setProuctCode(null);

        // Create the ProductInfo, which fails.

        restProductInfoMockMvc.perform(post("/api/product-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productInfo)))
            .andExpect(status().isBadRequest());

        List<ProductInfo> productInfoList = productInfoRepository.findAll();
        assertThat(productInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProductTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = productInfoRepository.findAll().size();
        // set the field null
        productInfo.setProductType(null);

        // Create the ProductInfo, which fails.

        restProductInfoMockMvc.perform(post("/api/product-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productInfo)))
            .andExpect(status().isBadRequest());

        List<ProductInfo> productInfoList = productInfoRepository.findAll();
        assertThat(productInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProductStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = productInfoRepository.findAll().size();
        // set the field null
        productInfo.setProductStatus(null);

        // Create the ProductInfo, which fails.

        restProductInfoMockMvc.perform(post("/api/product-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productInfo)))
            .andExpect(status().isBadRequest());

        List<ProductInfo> productInfoList = productInfoRepository.findAll();
        assertThat(productInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductInfos() throws Exception {
        // Initialize the database
        productInfoRepository.saveAndFlush(productInfo);

        // Get all the productInfoList
        restProductInfoMockMvc.perform(get("/api/product-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].prouctCode").value(hasItem(DEFAULT_PROUCT_CODE.toString())))
            .andExpect(jsonPath("$.[*].proudctName").value(hasItem(DEFAULT_PROUDCT_NAME.toString())))
            .andExpect(jsonPath("$.[*].productType").value(hasItem(DEFAULT_PRODUCT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].productDescription").value(hasItem(DEFAULT_PRODUCT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].productStatus").value(hasItem(DEFAULT_PRODUCT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getProductInfo() throws Exception {
        // Initialize the database
        productInfoRepository.saveAndFlush(productInfo);

        // Get the productInfo
        restProductInfoMockMvc.perform(get("/api/product-infos/{id}", productInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productInfo.getId().intValue()))
            .andExpect(jsonPath("$.prouctCode").value(DEFAULT_PROUCT_CODE.toString()))
            .andExpect(jsonPath("$.proudctName").value(DEFAULT_PROUDCT_NAME.toString()))
            .andExpect(jsonPath("$.productType").value(DEFAULT_PRODUCT_TYPE.toString()))
            .andExpect(jsonPath("$.productDescription").value(DEFAULT_PRODUCT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.productStatus").value(DEFAULT_PRODUCT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProductInfo() throws Exception {
        // Get the productInfo
        restProductInfoMockMvc.perform(get("/api/product-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductInfo() throws Exception {
        // Initialize the database
        productInfoRepository.saveAndFlush(productInfo);
        int databaseSizeBeforeUpdate = productInfoRepository.findAll().size();

        // Update the productInfo
        ProductInfo updatedProductInfo = productInfoRepository.findOne(productInfo.getId());
        updatedProductInfo
            .prouctCode(UPDATED_PROUCT_CODE)
            .proudctName(UPDATED_PROUDCT_NAME)
            .productType(UPDATED_PRODUCT_TYPE)
            .productDescription(UPDATED_PRODUCT_DESCRIPTION)
            .productStatus(UPDATED_PRODUCT_STATUS);

        restProductInfoMockMvc.perform(put("/api/product-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductInfo)))
            .andExpect(status().isOk());

        // Validate the ProductInfo in the database
        List<ProductInfo> productInfoList = productInfoRepository.findAll();
        assertThat(productInfoList).hasSize(databaseSizeBeforeUpdate);
        ProductInfo testProductInfo = productInfoList.get(productInfoList.size() - 1);
        assertThat(testProductInfo.getProuctCode()).isEqualTo(UPDATED_PROUCT_CODE);
        assertThat(testProductInfo.getProudctName()).isEqualTo(UPDATED_PROUDCT_NAME);
        assertThat(testProductInfo.getProductType()).isEqualTo(UPDATED_PRODUCT_TYPE);
        assertThat(testProductInfo.getProductDescription()).isEqualTo(UPDATED_PRODUCT_DESCRIPTION);
        assertThat(testProductInfo.getProductStatus()).isEqualTo(UPDATED_PRODUCT_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingProductInfo() throws Exception {
        int databaseSizeBeforeUpdate = productInfoRepository.findAll().size();

        // Create the ProductInfo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProductInfoMockMvc.perform(put("/api/product-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productInfo)))
            .andExpect(status().isCreated());

        // Validate the ProductInfo in the database
        List<ProductInfo> productInfoList = productInfoRepository.findAll();
        assertThat(productInfoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProductInfo() throws Exception {
        // Initialize the database
        productInfoRepository.saveAndFlush(productInfo);
        int databaseSizeBeforeDelete = productInfoRepository.findAll().size();

        // Get the productInfo
        restProductInfoMockMvc.perform(delete("/api/product-infos/{id}", productInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductInfo> productInfoList = productInfoRepository.findAll();
        assertThat(productInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductInfo.class);
        ProductInfo productInfo1 = new ProductInfo();
        productInfo1.setId(1L);
        ProductInfo productInfo2 = new ProductInfo();
        productInfo2.setId(productInfo1.getId());
        assertThat(productInfo1).isEqualTo(productInfo2);
        productInfo2.setId(2L);
        assertThat(productInfo1).isNotEqualTo(productInfo2);
        productInfo1.setId(null);
        assertThat(productInfo1).isNotEqualTo(productInfo2);
    }
}
