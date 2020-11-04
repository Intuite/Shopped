package com.intuite.shopped.web.rest;

import com.intuite.shopped.ShoppedApp;
import com.intuite.shopped.domain.Award;
import com.intuite.shopped.repository.AwardRepository;
import com.intuite.shopped.service.AwardService;
import com.intuite.shopped.service.dto.AwardDTO;
import com.intuite.shopped.service.mapper.AwardMapper;
import com.intuite.shopped.service.dto.AwardCriteria;
import com.intuite.shopped.service.AwardQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.intuite.shopped.domain.enumeration.Status;
/**
 * Integration tests for the {@link AwardResource} REST controller.
 */
@SpringBootTest(classes = ShoppedApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AwardResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_COST = 0;
    private static final Integer UPDATED_COST = 1;
    private static final Integer SMALLER_COST = 0 - 1;

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.INACTIVE;

    @Autowired
    private AwardRepository awardRepository;

    @Autowired
    private AwardMapper awardMapper;

    @Autowired
    private AwardService awardService;

    @Autowired
    private AwardQueryService awardQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAwardMockMvc;

    private Award award;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Award createEntity(EntityManager em) {
        Award award = new Award()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .cost(DEFAULT_COST)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .status(DEFAULT_STATUS);
        return award;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Award createUpdatedEntity(EntityManager em) {
        Award award = new Award()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .cost(UPDATED_COST)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .status(UPDATED_STATUS);
        return award;
    }

    @BeforeEach
    public void initTest() {
        award = createEntity(em);
    }

    @Test
    @Transactional
    public void createAward() throws Exception {
        int databaseSizeBeforeCreate = awardRepository.findAll().size();
        // Create the Award
        AwardDTO awardDTO = awardMapper.toDto(award);
        restAwardMockMvc.perform(post("/api/awards").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(awardDTO)))
            .andExpect(status().isCreated());

        // Validate the Award in the database
        List<Award> awardList = awardRepository.findAll();
        assertThat(awardList).hasSize(databaseSizeBeforeCreate + 1);
        Award testAward = awardList.get(awardList.size() - 1);
        assertThat(testAward.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAward.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAward.getCost()).isEqualTo(DEFAULT_COST);
        assertThat(testAward.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testAward.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testAward.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createAwardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = awardRepository.findAll().size();

        // Create the Award with an existing ID
        award.setId(1L);
        AwardDTO awardDTO = awardMapper.toDto(award);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAwardMockMvc.perform(post("/api/awards").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(awardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Award in the database
        List<Award> awardList = awardRepository.findAll();
        assertThat(awardList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = awardRepository.findAll().size();
        // set the field null
        award.setName(null);

        // Create the Award, which fails.
        AwardDTO awardDTO = awardMapper.toDto(award);


        restAwardMockMvc.perform(post("/api/awards").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(awardDTO)))
            .andExpect(status().isBadRequest());

        List<Award> awardList = awardRepository.findAll();
        assertThat(awardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = awardRepository.findAll().size();
        // set the field null
        award.setDescription(null);

        // Create the Award, which fails.
        AwardDTO awardDTO = awardMapper.toDto(award);


        restAwardMockMvc.perform(post("/api/awards").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(awardDTO)))
            .andExpect(status().isBadRequest());

        List<Award> awardList = awardRepository.findAll();
        assertThat(awardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCostIsRequired() throws Exception {
        int databaseSizeBeforeTest = awardRepository.findAll().size();
        // set the field null
        award.setCost(null);

        // Create the Award, which fails.
        AwardDTO awardDTO = awardMapper.toDto(award);


        restAwardMockMvc.perform(post("/api/awards").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(awardDTO)))
            .andExpect(status().isBadRequest());

        List<Award> awardList = awardRepository.findAll();
        assertThat(awardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAwards() throws Exception {
        // Initialize the database
        awardRepository.saveAndFlush(award);

        // Get all the awardList
        restAwardMockMvc.perform(get("/api/awards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(award.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getAward() throws Exception {
        // Initialize the database
        awardRepository.saveAndFlush(award);

        // Get the award
        restAwardMockMvc.perform(get("/api/awards/{id}", award.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(award.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }


    @Test
    @Transactional
    public void getAwardsByIdFiltering() throws Exception {
        // Initialize the database
        awardRepository.saveAndFlush(award);

        Long id = award.getId();

        defaultAwardShouldBeFound("id.equals=" + id);
        defaultAwardShouldNotBeFound("id.notEquals=" + id);

        defaultAwardShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAwardShouldNotBeFound("id.greaterThan=" + id);

        defaultAwardShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAwardShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAwardsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        awardRepository.saveAndFlush(award);

        // Get all the awardList where name equals to DEFAULT_NAME
        defaultAwardShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the awardList where name equals to UPDATED_NAME
        defaultAwardShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAwardsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        awardRepository.saveAndFlush(award);

        // Get all the awardList where name not equals to DEFAULT_NAME
        defaultAwardShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the awardList where name not equals to UPDATED_NAME
        defaultAwardShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAwardsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        awardRepository.saveAndFlush(award);

        // Get all the awardList where name in DEFAULT_NAME or UPDATED_NAME
        defaultAwardShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the awardList where name equals to UPDATED_NAME
        defaultAwardShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAwardsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        awardRepository.saveAndFlush(award);

        // Get all the awardList where name is not null
        defaultAwardShouldBeFound("name.specified=true");

        // Get all the awardList where name is null
        defaultAwardShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllAwardsByNameContainsSomething() throws Exception {
        // Initialize the database
        awardRepository.saveAndFlush(award);

        // Get all the awardList where name contains DEFAULT_NAME
        defaultAwardShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the awardList where name contains UPDATED_NAME
        defaultAwardShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAwardsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        awardRepository.saveAndFlush(award);

        // Get all the awardList where name does not contain DEFAULT_NAME
        defaultAwardShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the awardList where name does not contain UPDATED_NAME
        defaultAwardShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllAwardsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        awardRepository.saveAndFlush(award);

        // Get all the awardList where description equals to DEFAULT_DESCRIPTION
        defaultAwardShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the awardList where description equals to UPDATED_DESCRIPTION
        defaultAwardShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAwardsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        awardRepository.saveAndFlush(award);

        // Get all the awardList where description not equals to DEFAULT_DESCRIPTION
        defaultAwardShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the awardList where description not equals to UPDATED_DESCRIPTION
        defaultAwardShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAwardsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        awardRepository.saveAndFlush(award);

        // Get all the awardList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultAwardShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the awardList where description equals to UPDATED_DESCRIPTION
        defaultAwardShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAwardsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        awardRepository.saveAndFlush(award);

        // Get all the awardList where description is not null
        defaultAwardShouldBeFound("description.specified=true");

        // Get all the awardList where description is null
        defaultAwardShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllAwardsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        awardRepository.saveAndFlush(award);

        // Get all the awardList where description contains DEFAULT_DESCRIPTION
        defaultAwardShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the awardList where description contains UPDATED_DESCRIPTION
        defaultAwardShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAwardsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        awardRepository.saveAndFlush(award);

        // Get all the awardList where description does not contain DEFAULT_DESCRIPTION
        defaultAwardShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the awardList where description does not contain UPDATED_DESCRIPTION
        defaultAwardShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllAwardsByCostIsEqualToSomething() throws Exception {
        // Initialize the database
        awardRepository.saveAndFlush(award);

        // Get all the awardList where cost equals to DEFAULT_COST
        defaultAwardShouldBeFound("cost.equals=" + DEFAULT_COST);

        // Get all the awardList where cost equals to UPDATED_COST
        defaultAwardShouldNotBeFound("cost.equals=" + UPDATED_COST);
    }

    @Test
    @Transactional
    public void getAllAwardsByCostIsNotEqualToSomething() throws Exception {
        // Initialize the database
        awardRepository.saveAndFlush(award);

        // Get all the awardList where cost not equals to DEFAULT_COST
        defaultAwardShouldNotBeFound("cost.notEquals=" + DEFAULT_COST);

        // Get all the awardList where cost not equals to UPDATED_COST
        defaultAwardShouldBeFound("cost.notEquals=" + UPDATED_COST);
    }

    @Test
    @Transactional
    public void getAllAwardsByCostIsInShouldWork() throws Exception {
        // Initialize the database
        awardRepository.saveAndFlush(award);

        // Get all the awardList where cost in DEFAULT_COST or UPDATED_COST
        defaultAwardShouldBeFound("cost.in=" + DEFAULT_COST + "," + UPDATED_COST);

        // Get all the awardList where cost equals to UPDATED_COST
        defaultAwardShouldNotBeFound("cost.in=" + UPDATED_COST);
    }

    @Test
    @Transactional
    public void getAllAwardsByCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        awardRepository.saveAndFlush(award);

        // Get all the awardList where cost is not null
        defaultAwardShouldBeFound("cost.specified=true");

        // Get all the awardList where cost is null
        defaultAwardShouldNotBeFound("cost.specified=false");
    }

    @Test
    @Transactional
    public void getAllAwardsByCostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        awardRepository.saveAndFlush(award);

        // Get all the awardList where cost is greater than or equal to DEFAULT_COST
        defaultAwardShouldBeFound("cost.greaterThanOrEqual=" + DEFAULT_COST);

        // Get all the awardList where cost is greater than or equal to UPDATED_COST
        defaultAwardShouldNotBeFound("cost.greaterThanOrEqual=" + UPDATED_COST);
    }

    @Test
    @Transactional
    public void getAllAwardsByCostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        awardRepository.saveAndFlush(award);

        // Get all the awardList where cost is less than or equal to DEFAULT_COST
        defaultAwardShouldBeFound("cost.lessThanOrEqual=" + DEFAULT_COST);

        // Get all the awardList where cost is less than or equal to SMALLER_COST
        defaultAwardShouldNotBeFound("cost.lessThanOrEqual=" + SMALLER_COST);
    }

    @Test
    @Transactional
    public void getAllAwardsByCostIsLessThanSomething() throws Exception {
        // Initialize the database
        awardRepository.saveAndFlush(award);

        // Get all the awardList where cost is less than DEFAULT_COST
        defaultAwardShouldNotBeFound("cost.lessThan=" + DEFAULT_COST);

        // Get all the awardList where cost is less than UPDATED_COST
        defaultAwardShouldBeFound("cost.lessThan=" + UPDATED_COST);
    }

    @Test
    @Transactional
    public void getAllAwardsByCostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        awardRepository.saveAndFlush(award);

        // Get all the awardList where cost is greater than DEFAULT_COST
        defaultAwardShouldNotBeFound("cost.greaterThan=" + DEFAULT_COST);

        // Get all the awardList where cost is greater than SMALLER_COST
        defaultAwardShouldBeFound("cost.greaterThan=" + SMALLER_COST);
    }


    @Test
    @Transactional
    public void getAllAwardsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        awardRepository.saveAndFlush(award);

        // Get all the awardList where status equals to DEFAULT_STATUS
        defaultAwardShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the awardList where status equals to UPDATED_STATUS
        defaultAwardShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllAwardsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        awardRepository.saveAndFlush(award);

        // Get all the awardList where status not equals to DEFAULT_STATUS
        defaultAwardShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the awardList where status not equals to UPDATED_STATUS
        defaultAwardShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllAwardsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        awardRepository.saveAndFlush(award);

        // Get all the awardList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultAwardShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the awardList where status equals to UPDATED_STATUS
        defaultAwardShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllAwardsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        awardRepository.saveAndFlush(award);

        // Get all the awardList where status is not null
        defaultAwardShouldBeFound("status.specified=true");

        // Get all the awardList where status is null
        defaultAwardShouldNotBeFound("status.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAwardShouldBeFound(String filter) throws Exception {
        restAwardMockMvc.perform(get("/api/awards?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(award.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restAwardMockMvc.perform(get("/api/awards/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAwardShouldNotBeFound(String filter) throws Exception {
        restAwardMockMvc.perform(get("/api/awards?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAwardMockMvc.perform(get("/api/awards/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAward() throws Exception {
        // Get the award
        restAwardMockMvc.perform(get("/api/awards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAward() throws Exception {
        // Initialize the database
        awardRepository.saveAndFlush(award);

        int databaseSizeBeforeUpdate = awardRepository.findAll().size();

        // Update the award
        Award updatedAward = awardRepository.findById(award.getId()).get();
        // Disconnect from session so that the updates on updatedAward are not directly saved in db
        em.detach(updatedAward);
        updatedAward
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .cost(UPDATED_COST)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .status(UPDATED_STATUS);
        AwardDTO awardDTO = awardMapper.toDto(updatedAward);

        restAwardMockMvc.perform(put("/api/awards").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(awardDTO)))
            .andExpect(status().isOk());

        // Validate the Award in the database
        List<Award> awardList = awardRepository.findAll();
        assertThat(awardList).hasSize(databaseSizeBeforeUpdate);
        Award testAward = awardList.get(awardList.size() - 1);
        assertThat(testAward.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAward.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAward.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testAward.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testAward.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testAward.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingAward() throws Exception {
        int databaseSizeBeforeUpdate = awardRepository.findAll().size();

        // Create the Award
        AwardDTO awardDTO = awardMapper.toDto(award);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAwardMockMvc.perform(put("/api/awards").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(awardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Award in the database
        List<Award> awardList = awardRepository.findAll();
        assertThat(awardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAward() throws Exception {
        // Initialize the database
        awardRepository.saveAndFlush(award);

        int databaseSizeBeforeDelete = awardRepository.findAll().size();

        // Delete the award
        restAwardMockMvc.perform(delete("/api/awards/{id}", award.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Award> awardList = awardRepository.findAll();
        assertThat(awardList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
