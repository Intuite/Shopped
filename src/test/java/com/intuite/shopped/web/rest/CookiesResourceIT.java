package com.intuite.shopped.web.rest;

import com.intuite.shopped.ShoppedApp;
import com.intuite.shopped.domain.Cookies;
import com.intuite.shopped.domain.User;
import com.intuite.shopped.repository.CookiesRepository;
import com.intuite.shopped.service.CookiesService;
import com.intuite.shopped.service.dto.CookiesDTO;
import com.intuite.shopped.service.mapper.CookiesMapper;
import com.intuite.shopped.service.dto.CookiesCriteria;
import com.intuite.shopped.service.CookiesQueryService;

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
 * Integration tests for the {@link CookiesResource} REST controller.
 */
@SpringBootTest(classes = ShoppedApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CookiesResourceIT {

    private static final Integer DEFAULT_AMOUNT = 0;
    private static final Integer UPDATED_AMOUNT = 1;
    private static final Integer SMALLER_AMOUNT = 0 - 1;

    private static final String DEFAULT_WALLET_KEY = "AAAAAAAAAA";
    private static final String UPDATED_WALLET_KEY = "BBBBBBBBBB";

    @Autowired
    private CookiesRepository cookiesRepository;

    @Autowired
    private CookiesMapper cookiesMapper;

    @Autowired
    private CookiesService cookiesService;

    @Autowired
    private CookiesQueryService cookiesQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCookiesMockMvc;

    private Cookies cookies;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cookies createEntity(EntityManager em) {
        Cookies cookies = new Cookies()
            .amount(DEFAULT_AMOUNT)
            .walletKey(DEFAULT_WALLET_KEY);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        cookies.setUser(user);
        return cookies;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cookies createUpdatedEntity(EntityManager em) {
        Cookies cookies = new Cookies()
            .amount(UPDATED_AMOUNT)
            .walletKey(UPDATED_WALLET_KEY);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        cookies.setUser(user);
        return cookies;
    }

    @BeforeEach
    public void initTest() {
        cookies = createEntity(em);
    }

    @Test
    @Transactional
    public void createCookies() throws Exception {
        int databaseSizeBeforeCreate = cookiesRepository.findAll().size();
        // Create the Cookies
        CookiesDTO cookiesDTO = cookiesMapper.toDto(cookies);
        restCookiesMockMvc.perform(post("/api/cookies").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cookiesDTO)))
            .andExpect(status().isCreated());

        // Validate the Cookies in the database
        List<Cookies> cookiesList = cookiesRepository.findAll();
        assertThat(cookiesList).hasSize(databaseSizeBeforeCreate + 1);
        Cookies testCookies = cookiesList.get(cookiesList.size() - 1);
        assertThat(testCookies.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testCookies.getWalletKey()).isEqualTo(DEFAULT_WALLET_KEY);
    }

    @Test
    @Transactional
    public void createCookiesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cookiesRepository.findAll().size();

        // Create the Cookies with an existing ID
        cookies.setId(1L);
        CookiesDTO cookiesDTO = cookiesMapper.toDto(cookies);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCookiesMockMvc.perform(post("/api/cookies").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cookiesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cookies in the database
        List<Cookies> cookiesList = cookiesRepository.findAll();
        assertThat(cookiesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCookies() throws Exception {
        // Initialize the database
        cookiesRepository.saveAndFlush(cookies);

        // Get all the cookiesList
        restCookiesMockMvc.perform(get("/api/cookies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cookies.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.[*].walletKey").value(hasItem(DEFAULT_WALLET_KEY)));
    }
    
    @Test
    @Transactional
    public void getCookies() throws Exception {
        // Initialize the database
        cookiesRepository.saveAndFlush(cookies);

        // Get the cookies
        restCookiesMockMvc.perform(get("/api/cookies/{id}", cookies.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cookies.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT))
            .andExpect(jsonPath("$.walletKey").value(DEFAULT_WALLET_KEY));
    }


    @Test
    @Transactional
    public void getCookiesByIdFiltering() throws Exception {
        // Initialize the database
        cookiesRepository.saveAndFlush(cookies);

        Long id = cookies.getId();

        defaultCookiesShouldBeFound("id.equals=" + id);
        defaultCookiesShouldNotBeFound("id.notEquals=" + id);

        defaultCookiesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCookiesShouldNotBeFound("id.greaterThan=" + id);

        defaultCookiesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCookiesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCookiesByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        cookiesRepository.saveAndFlush(cookies);

        // Get all the cookiesList where amount equals to DEFAULT_AMOUNT
        defaultCookiesShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the cookiesList where amount equals to UPDATED_AMOUNT
        defaultCookiesShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCookiesByAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cookiesRepository.saveAndFlush(cookies);

        // Get all the cookiesList where amount not equals to DEFAULT_AMOUNT
        defaultCookiesShouldNotBeFound("amount.notEquals=" + DEFAULT_AMOUNT);

        // Get all the cookiesList where amount not equals to UPDATED_AMOUNT
        defaultCookiesShouldBeFound("amount.notEquals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCookiesByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        cookiesRepository.saveAndFlush(cookies);

        // Get all the cookiesList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultCookiesShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the cookiesList where amount equals to UPDATED_AMOUNT
        defaultCookiesShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCookiesByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        cookiesRepository.saveAndFlush(cookies);

        // Get all the cookiesList where amount is not null
        defaultCookiesShouldBeFound("amount.specified=true");

        // Get all the cookiesList where amount is null
        defaultCookiesShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllCookiesByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cookiesRepository.saveAndFlush(cookies);

        // Get all the cookiesList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultCookiesShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the cookiesList where amount is greater than or equal to UPDATED_AMOUNT
        defaultCookiesShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCookiesByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cookiesRepository.saveAndFlush(cookies);

        // Get all the cookiesList where amount is less than or equal to DEFAULT_AMOUNT
        defaultCookiesShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the cookiesList where amount is less than or equal to SMALLER_AMOUNT
        defaultCookiesShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCookiesByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        cookiesRepository.saveAndFlush(cookies);

        // Get all the cookiesList where amount is less than DEFAULT_AMOUNT
        defaultCookiesShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the cookiesList where amount is less than UPDATED_AMOUNT
        defaultCookiesShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCookiesByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cookiesRepository.saveAndFlush(cookies);

        // Get all the cookiesList where amount is greater than DEFAULT_AMOUNT
        defaultCookiesShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the cookiesList where amount is greater than SMALLER_AMOUNT
        defaultCookiesShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllCookiesByWalletKeyIsEqualToSomething() throws Exception {
        // Initialize the database
        cookiesRepository.saveAndFlush(cookies);

        // Get all the cookiesList where walletKey equals to DEFAULT_WALLET_KEY
        defaultCookiesShouldBeFound("walletKey.equals=" + DEFAULT_WALLET_KEY);

        // Get all the cookiesList where walletKey equals to UPDATED_WALLET_KEY
        defaultCookiesShouldNotBeFound("walletKey.equals=" + UPDATED_WALLET_KEY);
    }

    @Test
    @Transactional
    public void getAllCookiesByWalletKeyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cookiesRepository.saveAndFlush(cookies);

        // Get all the cookiesList where walletKey not equals to DEFAULT_WALLET_KEY
        defaultCookiesShouldNotBeFound("walletKey.notEquals=" + DEFAULT_WALLET_KEY);

        // Get all the cookiesList where walletKey not equals to UPDATED_WALLET_KEY
        defaultCookiesShouldBeFound("walletKey.notEquals=" + UPDATED_WALLET_KEY);
    }

    @Test
    @Transactional
    public void getAllCookiesByWalletKeyIsInShouldWork() throws Exception {
        // Initialize the database
        cookiesRepository.saveAndFlush(cookies);

        // Get all the cookiesList where walletKey in DEFAULT_WALLET_KEY or UPDATED_WALLET_KEY
        defaultCookiesShouldBeFound("walletKey.in=" + DEFAULT_WALLET_KEY + "," + UPDATED_WALLET_KEY);

        // Get all the cookiesList where walletKey equals to UPDATED_WALLET_KEY
        defaultCookiesShouldNotBeFound("walletKey.in=" + UPDATED_WALLET_KEY);
    }

    @Test
    @Transactional
    public void getAllCookiesByWalletKeyIsNullOrNotNull() throws Exception {
        // Initialize the database
        cookiesRepository.saveAndFlush(cookies);

        // Get all the cookiesList where walletKey is not null
        defaultCookiesShouldBeFound("walletKey.specified=true");

        // Get all the cookiesList where walletKey is null
        defaultCookiesShouldNotBeFound("walletKey.specified=false");
    }
                @Test
    @Transactional
    public void getAllCookiesByWalletKeyContainsSomething() throws Exception {
        // Initialize the database
        cookiesRepository.saveAndFlush(cookies);

        // Get all the cookiesList where walletKey contains DEFAULT_WALLET_KEY
        defaultCookiesShouldBeFound("walletKey.contains=" + DEFAULT_WALLET_KEY);

        // Get all the cookiesList where walletKey contains UPDATED_WALLET_KEY
        defaultCookiesShouldNotBeFound("walletKey.contains=" + UPDATED_WALLET_KEY);
    }

    @Test
    @Transactional
    public void getAllCookiesByWalletKeyNotContainsSomething() throws Exception {
        // Initialize the database
        cookiesRepository.saveAndFlush(cookies);

        // Get all the cookiesList where walletKey does not contain DEFAULT_WALLET_KEY
        defaultCookiesShouldNotBeFound("walletKey.doesNotContain=" + DEFAULT_WALLET_KEY);

        // Get all the cookiesList where walletKey does not contain UPDATED_WALLET_KEY
        defaultCookiesShouldBeFound("walletKey.doesNotContain=" + UPDATED_WALLET_KEY);
    }


    @Test
    @Transactional
    public void getAllCookiesByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User user = cookies.getUser();
        cookiesRepository.saveAndFlush(cookies);
        Long userId = user.getId();

        // Get all the cookiesList where user equals to userId
        defaultCookiesShouldBeFound("userId.equals=" + userId);

        // Get all the cookiesList where user equals to userId + 1
        defaultCookiesShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCookiesShouldBeFound(String filter) throws Exception {
        restCookiesMockMvc.perform(get("/api/cookies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cookies.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.[*].walletKey").value(hasItem(DEFAULT_WALLET_KEY)));

        // Check, that the count call also returns 1
        restCookiesMockMvc.perform(get("/api/cookies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCookiesShouldNotBeFound(String filter) throws Exception {
        restCookiesMockMvc.perform(get("/api/cookies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCookiesMockMvc.perform(get("/api/cookies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCookies() throws Exception {
        // Get the cookies
        restCookiesMockMvc.perform(get("/api/cookies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCookies() throws Exception {
        // Initialize the database
        cookiesRepository.saveAndFlush(cookies);

        int databaseSizeBeforeUpdate = cookiesRepository.findAll().size();

        // Update the cookies
        Cookies updatedCookies = cookiesRepository.findById(cookies.getId()).get();
        // Disconnect from session so that the updates on updatedCookies are not directly saved in db
        em.detach(updatedCookies);
        updatedCookies
            .amount(UPDATED_AMOUNT)
            .walletKey(UPDATED_WALLET_KEY);
        CookiesDTO cookiesDTO = cookiesMapper.toDto(updatedCookies);

        restCookiesMockMvc.perform(put("/api/cookies").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cookiesDTO)))
            .andExpect(status().isOk());

        // Validate the Cookies in the database
        List<Cookies> cookiesList = cookiesRepository.findAll();
        assertThat(cookiesList).hasSize(databaseSizeBeforeUpdate);
        Cookies testCookies = cookiesList.get(cookiesList.size() - 1);
        assertThat(testCookies.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testCookies.getWalletKey()).isEqualTo(UPDATED_WALLET_KEY);
    }

    @Test
    @Transactional
    public void updateNonExistingCookies() throws Exception {
        int databaseSizeBeforeUpdate = cookiesRepository.findAll().size();

        // Create the Cookies
        CookiesDTO cookiesDTO = cookiesMapper.toDto(cookies);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCookiesMockMvc.perform(put("/api/cookies").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cookiesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cookies in the database
        List<Cookies> cookiesList = cookiesRepository.findAll();
        assertThat(cookiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCookies() throws Exception {
        // Initialize the database
        cookiesRepository.saveAndFlush(cookies);

        int databaseSizeBeforeDelete = cookiesRepository.findAll().size();

        // Delete the cookies
        restCookiesMockMvc.perform(delete("/api/cookies/{id}", cookies.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cookies> cookiesList = cookiesRepository.findAll();
        assertThat(cookiesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
