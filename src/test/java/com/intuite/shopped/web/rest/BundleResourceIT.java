package com.intuite.shopped.web.rest;

import com.intuite.shopped.ShoppedApp;
import com.intuite.shopped.domain.Bundle;
import com.intuite.shopped.repository.BundleRepository;
import com.intuite.shopped.service.BundleService;
import com.intuite.shopped.service.dto.BundleDTO;
import com.intuite.shopped.service.mapper.BundleMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BundleResource} REST controller.
 */
@SpringBootTest(classes = ShoppedApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BundleResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_COST = 0D;
    private static final Double UPDATED_COST = 1D;

    private static final Integer DEFAULT_COOKIE_AMOUNT = 0;
    private static final Integer UPDATED_COOKIE_AMOUNT = 1;

    @Autowired
    private BundleRepository bundleRepository;

    @Autowired
    private BundleMapper bundleMapper;

    @Autowired
    private BundleService bundleService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBundleMockMvc;

    private Bundle bundle;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bundle createEntity(EntityManager em) {
        Bundle bundle = new Bundle()
            .name(DEFAULT_NAME)
            .cost(DEFAULT_COST)
            .cookieAmount(DEFAULT_COOKIE_AMOUNT);
        return bundle;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bundle createUpdatedEntity(EntityManager em) {
        Bundle bundle = new Bundle()
            .name(UPDATED_NAME)
            .cost(UPDATED_COST)
            .cookieAmount(UPDATED_COOKIE_AMOUNT);
        return bundle;
    }

    @BeforeEach
    public void initTest() {
        bundle = createEntity(em);
    }

    @Test
    @Transactional
    public void createBundle() throws Exception {
        int databaseSizeBeforeCreate = bundleRepository.findAll().size();
        // Create the Bundle
        BundleDTO bundleDTO = bundleMapper.toDto(bundle);
        restBundleMockMvc.perform(post("/api/bundles").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bundleDTO)))
            .andExpect(status().isCreated());

        // Validate the Bundle in the database
        List<Bundle> bundleList = bundleRepository.findAll();
        assertThat(bundleList).hasSize(databaseSizeBeforeCreate + 1);
        Bundle testBundle = bundleList.get(bundleList.size() - 1);
        assertThat(testBundle.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBundle.getCost()).isEqualTo(DEFAULT_COST);
        assertThat(testBundle.getCookieAmount()).isEqualTo(DEFAULT_COOKIE_AMOUNT);
    }

    @Test
    @Transactional
    public void createBundleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bundleRepository.findAll().size();

        // Create the Bundle with an existing ID
        bundle.setId(1L);
        BundleDTO bundleDTO = bundleMapper.toDto(bundle);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBundleMockMvc.perform(post("/api/bundles").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bundleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bundle in the database
        List<Bundle> bundleList = bundleRepository.findAll();
        assertThat(bundleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = bundleRepository.findAll().size();
        // set the field null
        bundle.setName(null);

        // Create the Bundle, which fails.
        BundleDTO bundleDTO = bundleMapper.toDto(bundle);


        restBundleMockMvc.perform(post("/api/bundles").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bundleDTO)))
            .andExpect(status().isBadRequest());

        List<Bundle> bundleList = bundleRepository.findAll();
        assertThat(bundleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCostIsRequired() throws Exception {
        int databaseSizeBeforeTest = bundleRepository.findAll().size();
        // set the field null
        bundle.setCost(null);

        // Create the Bundle, which fails.
        BundleDTO bundleDTO = bundleMapper.toDto(bundle);


        restBundleMockMvc.perform(post("/api/bundles").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bundleDTO)))
            .andExpect(status().isBadRequest());

        List<Bundle> bundleList = bundleRepository.findAll();
        assertThat(bundleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCookieAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = bundleRepository.findAll().size();
        // set the field null
        bundle.setCookieAmount(null);

        // Create the Bundle, which fails.
        BundleDTO bundleDTO = bundleMapper.toDto(bundle);


        restBundleMockMvc.perform(post("/api/bundles").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bundleDTO)))
            .andExpect(status().isBadRequest());

        List<Bundle> bundleList = bundleRepository.findAll();
        assertThat(bundleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBundles() throws Exception {
        // Initialize the database
        bundleRepository.saveAndFlush(bundle);

        // Get all the bundleList
        restBundleMockMvc.perform(get("/api/bundles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bundle.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].cookieAmount").value(hasItem(DEFAULT_COOKIE_AMOUNT)));
    }
    
    @Test
    @Transactional
    public void getBundle() throws Exception {
        // Initialize the database
        bundleRepository.saveAndFlush(bundle);

        // Get the bundle
        restBundleMockMvc.perform(get("/api/bundles/{id}", bundle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bundle.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST.doubleValue()))
            .andExpect(jsonPath("$.cookieAmount").value(DEFAULT_COOKIE_AMOUNT));
    }
    @Test
    @Transactional
    public void getNonExistingBundle() throws Exception {
        // Get the bundle
        restBundleMockMvc.perform(get("/api/bundles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBundle() throws Exception {
        // Initialize the database
        bundleRepository.saveAndFlush(bundle);

        int databaseSizeBeforeUpdate = bundleRepository.findAll().size();

        // Update the bundle
        Bundle updatedBundle = bundleRepository.findById(bundle.getId()).get();
        // Disconnect from session so that the updates on updatedBundle are not directly saved in db
        em.detach(updatedBundle);
        updatedBundle
            .name(UPDATED_NAME)
            .cost(UPDATED_COST)
            .cookieAmount(UPDATED_COOKIE_AMOUNT);
        BundleDTO bundleDTO = bundleMapper.toDto(updatedBundle);

        restBundleMockMvc.perform(put("/api/bundles").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bundleDTO)))
            .andExpect(status().isOk());

        // Validate the Bundle in the database
        List<Bundle> bundleList = bundleRepository.findAll();
        assertThat(bundleList).hasSize(databaseSizeBeforeUpdate);
        Bundle testBundle = bundleList.get(bundleList.size() - 1);
        assertThat(testBundle.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBundle.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testBundle.getCookieAmount()).isEqualTo(UPDATED_COOKIE_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingBundle() throws Exception {
        int databaseSizeBeforeUpdate = bundleRepository.findAll().size();

        // Create the Bundle
        BundleDTO bundleDTO = bundleMapper.toDto(bundle);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBundleMockMvc.perform(put("/api/bundles").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bundleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bundle in the database
        List<Bundle> bundleList = bundleRepository.findAll();
        assertThat(bundleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBundle() throws Exception {
        // Initialize the database
        bundleRepository.saveAndFlush(bundle);

        int databaseSizeBeforeDelete = bundleRepository.findAll().size();

        // Delete the bundle
        restBundleMockMvc.perform(delete("/api/bundles/{id}", bundle.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bundle> bundleList = bundleRepository.findAll();
        assertThat(bundleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
