package com.intuite.shopped.web.rest;

import com.intuite.shopped.ShoppedApp;
import com.intuite.shopped.domain.CartHasIngredient;
import com.intuite.shopped.domain.Cart;
import com.intuite.shopped.domain.Ingredient;
import com.intuite.shopped.repository.CartHasIngredientRepository;
import com.intuite.shopped.service.CartHasIngredientService;
import com.intuite.shopped.service.dto.CartHasIngredientDTO;
import com.intuite.shopped.service.mapper.CartHasIngredientMapper;
import com.intuite.shopped.service.dto.CartHasIngredientCriteria;
import com.intuite.shopped.service.CartHasIngredientQueryService;

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

import com.intuite.shopped.domain.enumeration.Status;
/**
 * Integration tests for the {@link CartHasIngredientResource} REST controller.
 */
@SpringBootTest(classes = ShoppedApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CartHasIngredientResourceIT {

    private static final Integer DEFAULT_AMOUNT = 0;
    private static final Integer UPDATED_AMOUNT = 1;
    private static final Integer SMALLER_AMOUNT = 0 - 1;

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.INACTIVE;

    @Autowired
    private CartHasIngredientRepository cartHasIngredientRepository;

    @Autowired
    private CartHasIngredientMapper cartHasIngredientMapper;

    @Autowired
    private CartHasIngredientService cartHasIngredientService;

    @Autowired
    private CartHasIngredientQueryService cartHasIngredientQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCartHasIngredientMockMvc;

    private CartHasIngredient cartHasIngredient;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CartHasIngredient createEntity(EntityManager em) {
        CartHasIngredient cartHasIngredient = new CartHasIngredient()
            .amount(DEFAULT_AMOUNT)
            .status(DEFAULT_STATUS);
        // Add required entity
        Cart cart;
        if (TestUtil.findAll(em, Cart.class).isEmpty()) {
            cart = CartResourceIT.createEntity(em);
            em.persist(cart);
            em.flush();
        } else {
            cart = TestUtil.findAll(em, Cart.class).get(0);
        }
        cartHasIngredient.setCart(cart);
        // Add required entity
        Ingredient ingredient;
        if (TestUtil.findAll(em, Ingredient.class).isEmpty()) {
            ingredient = IngredientResourceIT.createEntity(em);
            em.persist(ingredient);
            em.flush();
        } else {
            ingredient = TestUtil.findAll(em, Ingredient.class).get(0);
        }
        cartHasIngredient.setIngredient(ingredient);
        return cartHasIngredient;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CartHasIngredient createUpdatedEntity(EntityManager em) {
        CartHasIngredient cartHasIngredient = new CartHasIngredient()
            .amount(UPDATED_AMOUNT)
            .status(UPDATED_STATUS);
        // Add required entity
        Cart cart;
        if (TestUtil.findAll(em, Cart.class).isEmpty()) {
            cart = CartResourceIT.createUpdatedEntity(em);
            em.persist(cart);
            em.flush();
        } else {
            cart = TestUtil.findAll(em, Cart.class).get(0);
        }
        cartHasIngredient.setCart(cart);
        // Add required entity
        Ingredient ingredient;
        if (TestUtil.findAll(em, Ingredient.class).isEmpty()) {
            ingredient = IngredientResourceIT.createUpdatedEntity(em);
            em.persist(ingredient);
            em.flush();
        } else {
            ingredient = TestUtil.findAll(em, Ingredient.class).get(0);
        }
        cartHasIngredient.setIngredient(ingredient);
        return cartHasIngredient;
    }

    @BeforeEach
    public void initTest() {
        cartHasIngredient = createEntity(em);
    }

    @Test
    @Transactional
    public void createCartHasIngredient() throws Exception {
        int databaseSizeBeforeCreate = cartHasIngredientRepository.findAll().size();
        // Create the CartHasIngredient
        CartHasIngredientDTO cartHasIngredientDTO = cartHasIngredientMapper.toDto(cartHasIngredient);
        restCartHasIngredientMockMvc.perform(post("/api/cart-has-ingredients").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cartHasIngredientDTO)))
            .andExpect(status().isCreated());

        // Validate the CartHasIngredient in the database
        List<CartHasIngredient> cartHasIngredientList = cartHasIngredientRepository.findAll();
        assertThat(cartHasIngredientList).hasSize(databaseSizeBeforeCreate + 1);
        CartHasIngredient testCartHasIngredient = cartHasIngredientList.get(cartHasIngredientList.size() - 1);
        assertThat(testCartHasIngredient.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testCartHasIngredient.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createCartHasIngredientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cartHasIngredientRepository.findAll().size();

        // Create the CartHasIngredient with an existing ID
        cartHasIngredient.setId(1L);
        CartHasIngredientDTO cartHasIngredientDTO = cartHasIngredientMapper.toDto(cartHasIngredient);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCartHasIngredientMockMvc.perform(post("/api/cart-has-ingredients").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cartHasIngredientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CartHasIngredient in the database
        List<CartHasIngredient> cartHasIngredientList = cartHasIngredientRepository.findAll();
        assertThat(cartHasIngredientList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = cartHasIngredientRepository.findAll().size();
        // set the field null
        cartHasIngredient.setAmount(null);

        // Create the CartHasIngredient, which fails.
        CartHasIngredientDTO cartHasIngredientDTO = cartHasIngredientMapper.toDto(cartHasIngredient);


        restCartHasIngredientMockMvc.perform(post("/api/cart-has-ingredients").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cartHasIngredientDTO)))
            .andExpect(status().isBadRequest());

        List<CartHasIngredient> cartHasIngredientList = cartHasIngredientRepository.findAll();
        assertThat(cartHasIngredientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCartHasIngredients() throws Exception {
        // Initialize the database
        cartHasIngredientRepository.saveAndFlush(cartHasIngredient);

        // Get all the cartHasIngredientList
        restCartHasIngredientMockMvc.perform(get("/api/cart-has-ingredients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cartHasIngredient.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getCartHasIngredient() throws Exception {
        // Initialize the database
        cartHasIngredientRepository.saveAndFlush(cartHasIngredient);

        // Get the cartHasIngredient
        restCartHasIngredientMockMvc.perform(get("/api/cart-has-ingredients/{id}", cartHasIngredient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cartHasIngredient.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }


    @Test
    @Transactional
    public void getCartHasIngredientsByIdFiltering() throws Exception {
        // Initialize the database
        cartHasIngredientRepository.saveAndFlush(cartHasIngredient);

        Long id = cartHasIngredient.getId();

        defaultCartHasIngredientShouldBeFound("id.equals=" + id);
        defaultCartHasIngredientShouldNotBeFound("id.notEquals=" + id);

        defaultCartHasIngredientShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCartHasIngredientShouldNotBeFound("id.greaterThan=" + id);

        defaultCartHasIngredientShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCartHasIngredientShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCartHasIngredientsByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        cartHasIngredientRepository.saveAndFlush(cartHasIngredient);

        // Get all the cartHasIngredientList where amount equals to DEFAULT_AMOUNT
        defaultCartHasIngredientShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the cartHasIngredientList where amount equals to UPDATED_AMOUNT
        defaultCartHasIngredientShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCartHasIngredientsByAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cartHasIngredientRepository.saveAndFlush(cartHasIngredient);

        // Get all the cartHasIngredientList where amount not equals to DEFAULT_AMOUNT
        defaultCartHasIngredientShouldNotBeFound("amount.notEquals=" + DEFAULT_AMOUNT);

        // Get all the cartHasIngredientList where amount not equals to UPDATED_AMOUNT
        defaultCartHasIngredientShouldBeFound("amount.notEquals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCartHasIngredientsByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        cartHasIngredientRepository.saveAndFlush(cartHasIngredient);

        // Get all the cartHasIngredientList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultCartHasIngredientShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the cartHasIngredientList where amount equals to UPDATED_AMOUNT
        defaultCartHasIngredientShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCartHasIngredientsByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        cartHasIngredientRepository.saveAndFlush(cartHasIngredient);

        // Get all the cartHasIngredientList where amount is not null
        defaultCartHasIngredientShouldBeFound("amount.specified=true");

        // Get all the cartHasIngredientList where amount is null
        defaultCartHasIngredientShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllCartHasIngredientsByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cartHasIngredientRepository.saveAndFlush(cartHasIngredient);

        // Get all the cartHasIngredientList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultCartHasIngredientShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the cartHasIngredientList where amount is greater than or equal to UPDATED_AMOUNT
        defaultCartHasIngredientShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCartHasIngredientsByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cartHasIngredientRepository.saveAndFlush(cartHasIngredient);

        // Get all the cartHasIngredientList where amount is less than or equal to DEFAULT_AMOUNT
        defaultCartHasIngredientShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the cartHasIngredientList where amount is less than or equal to SMALLER_AMOUNT
        defaultCartHasIngredientShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCartHasIngredientsByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        cartHasIngredientRepository.saveAndFlush(cartHasIngredient);

        // Get all the cartHasIngredientList where amount is less than DEFAULT_AMOUNT
        defaultCartHasIngredientShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the cartHasIngredientList where amount is less than UPDATED_AMOUNT
        defaultCartHasIngredientShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCartHasIngredientsByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cartHasIngredientRepository.saveAndFlush(cartHasIngredient);

        // Get all the cartHasIngredientList where amount is greater than DEFAULT_AMOUNT
        defaultCartHasIngredientShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the cartHasIngredientList where amount is greater than SMALLER_AMOUNT
        defaultCartHasIngredientShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllCartHasIngredientsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        cartHasIngredientRepository.saveAndFlush(cartHasIngredient);

        // Get all the cartHasIngredientList where status equals to DEFAULT_STATUS
        defaultCartHasIngredientShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the cartHasIngredientList where status equals to UPDATED_STATUS
        defaultCartHasIngredientShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllCartHasIngredientsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cartHasIngredientRepository.saveAndFlush(cartHasIngredient);

        // Get all the cartHasIngredientList where status not equals to DEFAULT_STATUS
        defaultCartHasIngredientShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the cartHasIngredientList where status not equals to UPDATED_STATUS
        defaultCartHasIngredientShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllCartHasIngredientsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        cartHasIngredientRepository.saveAndFlush(cartHasIngredient);

        // Get all the cartHasIngredientList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultCartHasIngredientShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the cartHasIngredientList where status equals to UPDATED_STATUS
        defaultCartHasIngredientShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllCartHasIngredientsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        cartHasIngredientRepository.saveAndFlush(cartHasIngredient);

        // Get all the cartHasIngredientList where status is not null
        defaultCartHasIngredientShouldBeFound("status.specified=true");

        // Get all the cartHasIngredientList where status is null
        defaultCartHasIngredientShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllCartHasIngredientsByCartIsEqualToSomething() throws Exception {
        // Get already existing entity
        Cart cart = cartHasIngredient.getCart();
        cartHasIngredientRepository.saveAndFlush(cartHasIngredient);
        Long cartId = cart.getId();

        // Get all the cartHasIngredientList where cart equals to cartId
        defaultCartHasIngredientShouldBeFound("cartId.equals=" + cartId);

        // Get all the cartHasIngredientList where cart equals to cartId + 1
        defaultCartHasIngredientShouldNotBeFound("cartId.equals=" + (cartId + 1));
    }


    @Test
    @Transactional
    public void getAllCartHasIngredientsByIngredientIsEqualToSomething() throws Exception {
        // Get already existing entity
        Ingredient ingredient = cartHasIngredient.getIngredient();
        cartHasIngredientRepository.saveAndFlush(cartHasIngredient);
        Long ingredientId = ingredient.getId();

        // Get all the cartHasIngredientList where ingredient equals to ingredientId
        defaultCartHasIngredientShouldBeFound("ingredientId.equals=" + ingredientId);

        // Get all the cartHasIngredientList where ingredient equals to ingredientId + 1
        defaultCartHasIngredientShouldNotBeFound("ingredientId.equals=" + (ingredientId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCartHasIngredientShouldBeFound(String filter) throws Exception {
        restCartHasIngredientMockMvc.perform(get("/api/cart-has-ingredients?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cartHasIngredient.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restCartHasIngredientMockMvc.perform(get("/api/cart-has-ingredients/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCartHasIngredientShouldNotBeFound(String filter) throws Exception {
        restCartHasIngredientMockMvc.perform(get("/api/cart-has-ingredients?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCartHasIngredientMockMvc.perform(get("/api/cart-has-ingredients/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCartHasIngredient() throws Exception {
        // Get the cartHasIngredient
        restCartHasIngredientMockMvc.perform(get("/api/cart-has-ingredients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCartHasIngredient() throws Exception {
        // Initialize the database
        cartHasIngredientRepository.saveAndFlush(cartHasIngredient);

        int databaseSizeBeforeUpdate = cartHasIngredientRepository.findAll().size();

        // Update the cartHasIngredient
        CartHasIngredient updatedCartHasIngredient = cartHasIngredientRepository.findById(cartHasIngredient.getId()).get();
        // Disconnect from session so that the updates on updatedCartHasIngredient are not directly saved in db
        em.detach(updatedCartHasIngredient);
        updatedCartHasIngredient
            .amount(UPDATED_AMOUNT)
            .status(UPDATED_STATUS);
        CartHasIngredientDTO cartHasIngredientDTO = cartHasIngredientMapper.toDto(updatedCartHasIngredient);

        restCartHasIngredientMockMvc.perform(put("/api/cart-has-ingredients").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cartHasIngredientDTO)))
            .andExpect(status().isOk());

        // Validate the CartHasIngredient in the database
        List<CartHasIngredient> cartHasIngredientList = cartHasIngredientRepository.findAll();
        assertThat(cartHasIngredientList).hasSize(databaseSizeBeforeUpdate);
        CartHasIngredient testCartHasIngredient = cartHasIngredientList.get(cartHasIngredientList.size() - 1);
        assertThat(testCartHasIngredient.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testCartHasIngredient.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingCartHasIngredient() throws Exception {
        int databaseSizeBeforeUpdate = cartHasIngredientRepository.findAll().size();

        // Create the CartHasIngredient
        CartHasIngredientDTO cartHasIngredientDTO = cartHasIngredientMapper.toDto(cartHasIngredient);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCartHasIngredientMockMvc.perform(put("/api/cart-has-ingredients").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cartHasIngredientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CartHasIngredient in the database
        List<CartHasIngredient> cartHasIngredientList = cartHasIngredientRepository.findAll();
        assertThat(cartHasIngredientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCartHasIngredient() throws Exception {
        // Initialize the database
        cartHasIngredientRepository.saveAndFlush(cartHasIngredient);

        int databaseSizeBeforeDelete = cartHasIngredientRepository.findAll().size();

        // Delete the cartHasIngredient
        restCartHasIngredientMockMvc.perform(delete("/api/cart-has-ingredients/{id}", cartHasIngredient.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CartHasIngredient> cartHasIngredientList = cartHasIngredientRepository.findAll();
        assertThat(cartHasIngredientList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
