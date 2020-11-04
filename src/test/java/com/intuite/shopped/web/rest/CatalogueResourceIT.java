package com.intuite.shopped.web.rest;

import com.intuite.shopped.ShoppedApp;
import com.intuite.shopped.domain.Catalogue;
import com.intuite.shopped.repository.CatalogueRepository;
import com.intuite.shopped.service.CatalogueService;
import com.intuite.shopped.service.dto.CatalogueDTO;
import com.intuite.shopped.service.mapper.CatalogueMapper;
import com.intuite.shopped.service.dto.CatalogueCriteria;
import com.intuite.shopped.service.CatalogueQueryService;

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
 * Integration tests for the {@link CatalogueResource} REST controller.
 */
@SpringBootTest(classes = ShoppedApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CatalogueResourceIT {

    private static final String DEFAULT_ID_CATALOGUE = "AAAAAAAAAA";
    private static final String UPDATED_ID_CATALOGUE = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private CatalogueRepository catalogueRepository;

    @Autowired
    private CatalogueMapper catalogueMapper;

    @Autowired
    private CatalogueService catalogueService;

    @Autowired
    private CatalogueQueryService catalogueQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCatalogueMockMvc;

    private Catalogue catalogue;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Catalogue createEntity(EntityManager em) {
        Catalogue catalogue = new Catalogue()
            .idCatalogue(DEFAULT_ID_CATALOGUE)
            .value(DEFAULT_VALUE);
        return catalogue;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Catalogue createUpdatedEntity(EntityManager em) {
        Catalogue catalogue = new Catalogue()
            .idCatalogue(UPDATED_ID_CATALOGUE)
            .value(UPDATED_VALUE);
        return catalogue;
    }

    @BeforeEach
    public void initTest() {
        catalogue = createEntity(em);
    }

    @Test
    @Transactional
    public void createCatalogue() throws Exception {
        int databaseSizeBeforeCreate = catalogueRepository.findAll().size();
        // Create the Catalogue
        CatalogueDTO catalogueDTO = catalogueMapper.toDto(catalogue);
        restCatalogueMockMvc.perform(post("/api/catalogues").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catalogueDTO)))
            .andExpect(status().isCreated());

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll();
        assertThat(catalogueList).hasSize(databaseSizeBeforeCreate + 1);
        Catalogue testCatalogue = catalogueList.get(catalogueList.size() - 1);
        assertThat(testCatalogue.getIdCatalogue()).isEqualTo(DEFAULT_ID_CATALOGUE);
        assertThat(testCatalogue.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createCatalogueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = catalogueRepository.findAll().size();

        // Create the Catalogue with an existing ID
        catalogue.setId(1L);
        CatalogueDTO catalogueDTO = catalogueMapper.toDto(catalogue);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCatalogueMockMvc.perform(post("/api/catalogues").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catalogueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll();
        assertThat(catalogueList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdCatalogueIsRequired() throws Exception {
        int databaseSizeBeforeTest = catalogueRepository.findAll().size();
        // set the field null
        catalogue.setIdCatalogue(null);

        // Create the Catalogue, which fails.
        CatalogueDTO catalogueDTO = catalogueMapper.toDto(catalogue);


        restCatalogueMockMvc.perform(post("/api/catalogues").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catalogueDTO)))
            .andExpect(status().isBadRequest());

        List<Catalogue> catalogueList = catalogueRepository.findAll();
        assertThat(catalogueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = catalogueRepository.findAll().size();
        // set the field null
        catalogue.setValue(null);

        // Create the Catalogue, which fails.
        CatalogueDTO catalogueDTO = catalogueMapper.toDto(catalogue);


        restCatalogueMockMvc.perform(post("/api/catalogues").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catalogueDTO)))
            .andExpect(status().isBadRequest());

        List<Catalogue> catalogueList = catalogueRepository.findAll();
        assertThat(catalogueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCatalogues() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList
        restCatalogueMockMvc.perform(get("/api/catalogues?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catalogue.getId().intValue())))
            .andExpect(jsonPath("$.[*].idCatalogue").value(hasItem(DEFAULT_ID_CATALOGUE)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }
    
    @Test
    @Transactional
    public void getCatalogue() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get the catalogue
        restCatalogueMockMvc.perform(get("/api/catalogues/{id}", catalogue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(catalogue.getId().intValue()))
            .andExpect(jsonPath("$.idCatalogue").value(DEFAULT_ID_CATALOGUE))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }


    @Test
    @Transactional
    public void getCataloguesByIdFiltering() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        Long id = catalogue.getId();

        defaultCatalogueShouldBeFound("id.equals=" + id);
        defaultCatalogueShouldNotBeFound("id.notEquals=" + id);

        defaultCatalogueShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCatalogueShouldNotBeFound("id.greaterThan=" + id);

        defaultCatalogueShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCatalogueShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCataloguesByIdCatalogueIsEqualToSomething() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where idCatalogue equals to DEFAULT_ID_CATALOGUE
        defaultCatalogueShouldBeFound("idCatalogue.equals=" + DEFAULT_ID_CATALOGUE);

        // Get all the catalogueList where idCatalogue equals to UPDATED_ID_CATALOGUE
        defaultCatalogueShouldNotBeFound("idCatalogue.equals=" + UPDATED_ID_CATALOGUE);
    }

    @Test
    @Transactional
    public void getAllCataloguesByIdCatalogueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where idCatalogue not equals to DEFAULT_ID_CATALOGUE
        defaultCatalogueShouldNotBeFound("idCatalogue.notEquals=" + DEFAULT_ID_CATALOGUE);

        // Get all the catalogueList where idCatalogue not equals to UPDATED_ID_CATALOGUE
        defaultCatalogueShouldBeFound("idCatalogue.notEquals=" + UPDATED_ID_CATALOGUE);
    }

    @Test
    @Transactional
    public void getAllCataloguesByIdCatalogueIsInShouldWork() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where idCatalogue in DEFAULT_ID_CATALOGUE or UPDATED_ID_CATALOGUE
        defaultCatalogueShouldBeFound("idCatalogue.in=" + DEFAULT_ID_CATALOGUE + "," + UPDATED_ID_CATALOGUE);

        // Get all the catalogueList where idCatalogue equals to UPDATED_ID_CATALOGUE
        defaultCatalogueShouldNotBeFound("idCatalogue.in=" + UPDATED_ID_CATALOGUE);
    }

    @Test
    @Transactional
    public void getAllCataloguesByIdCatalogueIsNullOrNotNull() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where idCatalogue is not null
        defaultCatalogueShouldBeFound("idCatalogue.specified=true");

        // Get all the catalogueList where idCatalogue is null
        defaultCatalogueShouldNotBeFound("idCatalogue.specified=false");
    }
                @Test
    @Transactional
    public void getAllCataloguesByIdCatalogueContainsSomething() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where idCatalogue contains DEFAULT_ID_CATALOGUE
        defaultCatalogueShouldBeFound("idCatalogue.contains=" + DEFAULT_ID_CATALOGUE);

        // Get all the catalogueList where idCatalogue contains UPDATED_ID_CATALOGUE
        defaultCatalogueShouldNotBeFound("idCatalogue.contains=" + UPDATED_ID_CATALOGUE);
    }

    @Test
    @Transactional
    public void getAllCataloguesByIdCatalogueNotContainsSomething() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where idCatalogue does not contain DEFAULT_ID_CATALOGUE
        defaultCatalogueShouldNotBeFound("idCatalogue.doesNotContain=" + DEFAULT_ID_CATALOGUE);

        // Get all the catalogueList where idCatalogue does not contain UPDATED_ID_CATALOGUE
        defaultCatalogueShouldBeFound("idCatalogue.doesNotContain=" + UPDATED_ID_CATALOGUE);
    }


    @Test
    @Transactional
    public void getAllCataloguesByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where value equals to DEFAULT_VALUE
        defaultCatalogueShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the catalogueList where value equals to UPDATED_VALUE
        defaultCatalogueShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllCataloguesByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where value not equals to DEFAULT_VALUE
        defaultCatalogueShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the catalogueList where value not equals to UPDATED_VALUE
        defaultCatalogueShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllCataloguesByValueIsInShouldWork() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultCatalogueShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the catalogueList where value equals to UPDATED_VALUE
        defaultCatalogueShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllCataloguesByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where value is not null
        defaultCatalogueShouldBeFound("value.specified=true");

        // Get all the catalogueList where value is null
        defaultCatalogueShouldNotBeFound("value.specified=false");
    }
                @Test
    @Transactional
    public void getAllCataloguesByValueContainsSomething() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where value contains DEFAULT_VALUE
        defaultCatalogueShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the catalogueList where value contains UPDATED_VALUE
        defaultCatalogueShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllCataloguesByValueNotContainsSomething() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where value does not contain DEFAULT_VALUE
        defaultCatalogueShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the catalogueList where value does not contain UPDATED_VALUE
        defaultCatalogueShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCatalogueShouldBeFound(String filter) throws Exception {
        restCatalogueMockMvc.perform(get("/api/catalogues?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catalogue.getId().intValue())))
            .andExpect(jsonPath("$.[*].idCatalogue").value(hasItem(DEFAULT_ID_CATALOGUE)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));

        // Check, that the count call also returns 1
        restCatalogueMockMvc.perform(get("/api/catalogues/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCatalogueShouldNotBeFound(String filter) throws Exception {
        restCatalogueMockMvc.perform(get("/api/catalogues?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCatalogueMockMvc.perform(get("/api/catalogues/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCatalogue() throws Exception {
        // Get the catalogue
        restCatalogueMockMvc.perform(get("/api/catalogues/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCatalogue() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        int databaseSizeBeforeUpdate = catalogueRepository.findAll().size();

        // Update the catalogue
        Catalogue updatedCatalogue = catalogueRepository.findById(catalogue.getId()).get();
        // Disconnect from session so that the updates on updatedCatalogue are not directly saved in db
        em.detach(updatedCatalogue);
        updatedCatalogue
            .idCatalogue(UPDATED_ID_CATALOGUE)
            .value(UPDATED_VALUE);
        CatalogueDTO catalogueDTO = catalogueMapper.toDto(updatedCatalogue);

        restCatalogueMockMvc.perform(put("/api/catalogues").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catalogueDTO)))
            .andExpect(status().isOk());

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll();
        assertThat(catalogueList).hasSize(databaseSizeBeforeUpdate);
        Catalogue testCatalogue = catalogueList.get(catalogueList.size() - 1);
        assertThat(testCatalogue.getIdCatalogue()).isEqualTo(UPDATED_ID_CATALOGUE);
        assertThat(testCatalogue.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingCatalogue() throws Exception {
        int databaseSizeBeforeUpdate = catalogueRepository.findAll().size();

        // Create the Catalogue
        CatalogueDTO catalogueDTO = catalogueMapper.toDto(catalogue);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCatalogueMockMvc.perform(put("/api/catalogues").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catalogueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll();
        assertThat(catalogueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCatalogue() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        int databaseSizeBeforeDelete = catalogueRepository.findAll().size();

        // Delete the catalogue
        restCatalogueMockMvc.perform(delete("/api/catalogues/{id}", catalogue.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Catalogue> catalogueList = catalogueRepository.findAll();
        assertThat(catalogueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
