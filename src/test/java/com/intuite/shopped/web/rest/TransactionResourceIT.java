package com.intuite.shopped.web.rest;

import com.intuite.shopped.ShoppedApp;
import com.intuite.shopped.domain.Transaction;
import com.intuite.shopped.domain.User;
import com.intuite.shopped.repository.TransactionRepository;
import com.intuite.shopped.service.TransactionService;
import com.intuite.shopped.service.dto.TransactionDTO;
import com.intuite.shopped.service.mapper.TransactionMapper;
import com.intuite.shopped.service.dto.TransactionCriteria;
import com.intuite.shopped.service.TransactionQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TransactionResource} REST controller.
 */
@SpringBootTest(classes = ShoppedApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TransactionResourceIT {

    private static final Float DEFAULT_AMOUNT = 1F;
    private static final Float UPDATED_AMOUNT = 2F;
    private static final Float SMALLER_AMOUNT = 1F - 1F;

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_COOKIES_AMOUNT = 1;
    private static final Integer UPDATED_COOKIES_AMOUNT = 2;
    private static final Integer SMALLER_COOKIES_AMOUNT = 1 - 1;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionQueryService transactionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransactionMockMvc;

    private Transaction transaction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transaction createEntity(EntityManager em) {
        Transaction transaction = new Transaction()
            .amount(DEFAULT_AMOUNT)
            .created(DEFAULT_CREATED)
            .description(DEFAULT_DESCRIPTION)
            .cookiesAmount(DEFAULT_COOKIES_AMOUNT);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        transaction.setUser(user);
        return transaction;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transaction createUpdatedEntity(EntityManager em) {
        Transaction transaction = new Transaction()
            .amount(UPDATED_AMOUNT)
            .created(UPDATED_CREATED)
            .description(UPDATED_DESCRIPTION)
            .cookiesAmount(UPDATED_COOKIES_AMOUNT);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        transaction.setUser(user);
        return transaction;
    }

    @BeforeEach
    public void initTest() {
        transaction = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransaction() throws Exception {
        int databaseSizeBeforeCreate = transactionRepository.findAll().size();
        // Create the Transaction
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);
        restTransactionMockMvc.perform(post("/api/transactions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDTO)))
            .andExpect(status().isCreated());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeCreate + 1);
        Transaction testTransaction = transactionList.get(transactionList.size() - 1);
        assertThat(testTransaction.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testTransaction.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testTransaction.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTransaction.getCookiesAmount()).isEqualTo(DEFAULT_COOKIES_AMOUNT);
    }

    @Test
    @Transactional
    public void createTransactionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionRepository.findAll().size();

        // Create the Transaction with an existing ID
        transaction.setId(1L);
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionMockMvc.perform(post("/api/transactions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionRepository.findAll().size();
        // set the field null
        transaction.setAmount(null);

        // Create the Transaction, which fails.
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);


        restTransactionMockMvc.perform(post("/api/transactions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDTO)))
            .andExpect(status().isBadRequest());

        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionRepository.findAll().size();
        // set the field null
        transaction.setCreated(null);

        // Create the Transaction, which fails.
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);


        restTransactionMockMvc.perform(post("/api/transactions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDTO)))
            .andExpect(status().isBadRequest());

        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionRepository.findAll().size();
        // set the field null
        transaction.setDescription(null);

        // Create the Transaction, which fails.
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);


        restTransactionMockMvc.perform(post("/api/transactions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDTO)))
            .andExpect(status().isBadRequest());

        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCookiesAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionRepository.findAll().size();
        // set the field null
        transaction.setCookiesAmount(null);

        // Create the Transaction, which fails.
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);


        restTransactionMockMvc.perform(post("/api/transactions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDTO)))
            .andExpect(status().isBadRequest());

        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransactions() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList
        restTransactionMockMvc.perform(get("/api/transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].cookiesAmount").value(hasItem(DEFAULT_COOKIES_AMOUNT)));
    }
    
    @Test
    @Transactional
    public void getTransaction() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get the transaction
        restTransactionMockMvc.perform(get("/api/transactions/{id}", transaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transaction.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.cookiesAmount").value(DEFAULT_COOKIES_AMOUNT));
    }


    @Test
    @Transactional
    public void getTransactionsByIdFiltering() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        Long id = transaction.getId();

        defaultTransactionShouldBeFound("id.equals=" + id);
        defaultTransactionShouldNotBeFound("id.notEquals=" + id);

        defaultTransactionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTransactionShouldNotBeFound("id.greaterThan=" + id);

        defaultTransactionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTransactionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTransactionsByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where amount equals to DEFAULT_AMOUNT
        defaultTransactionShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the transactionList where amount equals to UPDATED_AMOUNT
        defaultTransactionShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllTransactionsByAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where amount not equals to DEFAULT_AMOUNT
        defaultTransactionShouldNotBeFound("amount.notEquals=" + DEFAULT_AMOUNT);

        // Get all the transactionList where amount not equals to UPDATED_AMOUNT
        defaultTransactionShouldBeFound("amount.notEquals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllTransactionsByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultTransactionShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the transactionList where amount equals to UPDATED_AMOUNT
        defaultTransactionShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllTransactionsByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where amount is not null
        defaultTransactionShouldBeFound("amount.specified=true");

        // Get all the transactionList where amount is null
        defaultTransactionShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransactionsByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultTransactionShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the transactionList where amount is greater than or equal to UPDATED_AMOUNT
        defaultTransactionShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllTransactionsByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where amount is less than or equal to DEFAULT_AMOUNT
        defaultTransactionShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the transactionList where amount is less than or equal to SMALLER_AMOUNT
        defaultTransactionShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllTransactionsByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where amount is less than DEFAULT_AMOUNT
        defaultTransactionShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the transactionList where amount is less than UPDATED_AMOUNT
        defaultTransactionShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllTransactionsByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where amount is greater than DEFAULT_AMOUNT
        defaultTransactionShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the transactionList where amount is greater than SMALLER_AMOUNT
        defaultTransactionShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllTransactionsByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where created equals to DEFAULT_CREATED
        defaultTransactionShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the transactionList where created equals to UPDATED_CREATED
        defaultTransactionShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllTransactionsByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where created not equals to DEFAULT_CREATED
        defaultTransactionShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the transactionList where created not equals to UPDATED_CREATED
        defaultTransactionShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllTransactionsByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultTransactionShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the transactionList where created equals to UPDATED_CREATED
        defaultTransactionShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllTransactionsByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where created is not null
        defaultTransactionShouldBeFound("created.specified=true");

        // Get all the transactionList where created is null
        defaultTransactionShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransactionsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where description equals to DEFAULT_DESCRIPTION
        defaultTransactionShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the transactionList where description equals to UPDATED_DESCRIPTION
        defaultTransactionShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTransactionsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where description not equals to DEFAULT_DESCRIPTION
        defaultTransactionShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the transactionList where description not equals to UPDATED_DESCRIPTION
        defaultTransactionShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTransactionsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultTransactionShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the transactionList where description equals to UPDATED_DESCRIPTION
        defaultTransactionShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTransactionsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where description is not null
        defaultTransactionShouldBeFound("description.specified=true");

        // Get all the transactionList where description is null
        defaultTransactionShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllTransactionsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where description contains DEFAULT_DESCRIPTION
        defaultTransactionShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the transactionList where description contains UPDATED_DESCRIPTION
        defaultTransactionShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTransactionsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where description does not contain DEFAULT_DESCRIPTION
        defaultTransactionShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the transactionList where description does not contain UPDATED_DESCRIPTION
        defaultTransactionShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllTransactionsByCookiesAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where cookiesAmount equals to DEFAULT_COOKIES_AMOUNT
        defaultTransactionShouldBeFound("cookiesAmount.equals=" + DEFAULT_COOKIES_AMOUNT);

        // Get all the transactionList where cookiesAmount equals to UPDATED_COOKIES_AMOUNT
        defaultTransactionShouldNotBeFound("cookiesAmount.equals=" + UPDATED_COOKIES_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllTransactionsByCookiesAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where cookiesAmount not equals to DEFAULT_COOKIES_AMOUNT
        defaultTransactionShouldNotBeFound("cookiesAmount.notEquals=" + DEFAULT_COOKIES_AMOUNT);

        // Get all the transactionList where cookiesAmount not equals to UPDATED_COOKIES_AMOUNT
        defaultTransactionShouldBeFound("cookiesAmount.notEquals=" + UPDATED_COOKIES_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllTransactionsByCookiesAmountIsInShouldWork() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where cookiesAmount in DEFAULT_COOKIES_AMOUNT or UPDATED_COOKIES_AMOUNT
        defaultTransactionShouldBeFound("cookiesAmount.in=" + DEFAULT_COOKIES_AMOUNT + "," + UPDATED_COOKIES_AMOUNT);

        // Get all the transactionList where cookiesAmount equals to UPDATED_COOKIES_AMOUNT
        defaultTransactionShouldNotBeFound("cookiesAmount.in=" + UPDATED_COOKIES_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllTransactionsByCookiesAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where cookiesAmount is not null
        defaultTransactionShouldBeFound("cookiesAmount.specified=true");

        // Get all the transactionList where cookiesAmount is null
        defaultTransactionShouldNotBeFound("cookiesAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransactionsByCookiesAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where cookiesAmount is greater than or equal to DEFAULT_COOKIES_AMOUNT
        defaultTransactionShouldBeFound("cookiesAmount.greaterThanOrEqual=" + DEFAULT_COOKIES_AMOUNT);

        // Get all the transactionList where cookiesAmount is greater than or equal to UPDATED_COOKIES_AMOUNT
        defaultTransactionShouldNotBeFound("cookiesAmount.greaterThanOrEqual=" + UPDATED_COOKIES_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllTransactionsByCookiesAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where cookiesAmount is less than or equal to DEFAULT_COOKIES_AMOUNT
        defaultTransactionShouldBeFound("cookiesAmount.lessThanOrEqual=" + DEFAULT_COOKIES_AMOUNT);

        // Get all the transactionList where cookiesAmount is less than or equal to SMALLER_COOKIES_AMOUNT
        defaultTransactionShouldNotBeFound("cookiesAmount.lessThanOrEqual=" + SMALLER_COOKIES_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllTransactionsByCookiesAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where cookiesAmount is less than DEFAULT_COOKIES_AMOUNT
        defaultTransactionShouldNotBeFound("cookiesAmount.lessThan=" + DEFAULT_COOKIES_AMOUNT);

        // Get all the transactionList where cookiesAmount is less than UPDATED_COOKIES_AMOUNT
        defaultTransactionShouldBeFound("cookiesAmount.lessThan=" + UPDATED_COOKIES_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllTransactionsByCookiesAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where cookiesAmount is greater than DEFAULT_COOKIES_AMOUNT
        defaultTransactionShouldNotBeFound("cookiesAmount.greaterThan=" + DEFAULT_COOKIES_AMOUNT);

        // Get all the transactionList where cookiesAmount is greater than SMALLER_COOKIES_AMOUNT
        defaultTransactionShouldBeFound("cookiesAmount.greaterThan=" + SMALLER_COOKIES_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllTransactionsByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User user = transaction.getUser();
        transactionRepository.saveAndFlush(transaction);
        Long userId = user.getId();

        // Get all the transactionList where user equals to userId
        defaultTransactionShouldBeFound("userId.equals=" + userId);

        // Get all the transactionList where user equals to userId + 1
        defaultTransactionShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTransactionShouldBeFound(String filter) throws Exception {
        restTransactionMockMvc.perform(get("/api/transactions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].cookiesAmount").value(hasItem(DEFAULT_COOKIES_AMOUNT)));

        // Check, that the count call also returns 1
        restTransactionMockMvc.perform(get("/api/transactions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTransactionShouldNotBeFound(String filter) throws Exception {
        restTransactionMockMvc.perform(get("/api/transactions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTransactionMockMvc.perform(get("/api/transactions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTransaction() throws Exception {
        // Get the transaction
        restTransactionMockMvc.perform(get("/api/transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransaction() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        int databaseSizeBeforeUpdate = transactionRepository.findAll().size();

        // Update the transaction
        Transaction updatedTransaction = transactionRepository.findById(transaction.getId()).get();
        // Disconnect from session so that the updates on updatedTransaction are not directly saved in db
        em.detach(updatedTransaction);
        updatedTransaction
            .amount(UPDATED_AMOUNT)
            .created(UPDATED_CREATED)
            .description(UPDATED_DESCRIPTION)
            .cookiesAmount(UPDATED_COOKIES_AMOUNT);
        TransactionDTO transactionDTO = transactionMapper.toDto(updatedTransaction);

        restTransactionMockMvc.perform(put("/api/transactions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDTO)))
            .andExpect(status().isOk());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeUpdate);
        Transaction testTransaction = transactionList.get(transactionList.size() - 1);
        assertThat(testTransaction.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testTransaction.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testTransaction.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTransaction.getCookiesAmount()).isEqualTo(UPDATED_COOKIES_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingTransaction() throws Exception {
        int databaseSizeBeforeUpdate = transactionRepository.findAll().size();

        // Create the Transaction
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionMockMvc.perform(put("/api/transactions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransaction() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        int databaseSizeBeforeDelete = transactionRepository.findAll().size();

        // Delete the transaction
        restTransactionMockMvc.perform(delete("/api/transactions/{id}", transaction.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
