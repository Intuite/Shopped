package com.intuite.shopped.web.rest;

import com.intuite.shopped.ShoppedApp;
import com.intuite.shopped.domain.CartHasRecipe;
import com.intuite.shopped.domain.Recipe;
import com.intuite.shopped.domain.Cart;
import com.intuite.shopped.repository.CartHasRecipeRepository;
import com.intuite.shopped.service.CartHasRecipeService;
import com.intuite.shopped.service.dto.CartHasRecipeDTO;
import com.intuite.shopped.service.mapper.CartHasRecipeMapper;
import com.intuite.shopped.service.dto.CartHasRecipeCriteria;
import com.intuite.shopped.service.CartHasRecipeQueryService;

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
 * Integration tests for the {@link CartHasRecipeResource} REST controller.
 */
@SpringBootTest(classes = ShoppedApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CartHasRecipeResourceIT {

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.INACTIVE;

    @Autowired
    private CartHasRecipeRepository cartHasRecipeRepository;

    @Autowired
    private CartHasRecipeMapper cartHasRecipeMapper;

    @Autowired
    private CartHasRecipeService cartHasRecipeService;

    @Autowired
    private CartHasRecipeQueryService cartHasRecipeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCartHasRecipeMockMvc;

    private CartHasRecipe cartHasRecipe;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CartHasRecipe createEntity(EntityManager em) {
        CartHasRecipe cartHasRecipe = new CartHasRecipe()
            .status(DEFAULT_STATUS);
        // Add required entity
        Recipe recipe;
        if (TestUtil.findAll(em, Recipe.class).isEmpty()) {
            recipe = RecipeResourceIT.createEntity(em);
            em.persist(recipe);
            em.flush();
        } else {
            recipe = TestUtil.findAll(em, Recipe.class).get(0);
        }
        cartHasRecipe.setRecipe(recipe);
        // Add required entity
        Cart cart;
        if (TestUtil.findAll(em, Cart.class).isEmpty()) {
            cart = CartResourceIT.createEntity(em);
            em.persist(cart);
            em.flush();
        } else {
            cart = TestUtil.findAll(em, Cart.class).get(0);
        }
        cartHasRecipe.setCart(cart);
        return cartHasRecipe;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CartHasRecipe createUpdatedEntity(EntityManager em) {
        CartHasRecipe cartHasRecipe = new CartHasRecipe()
            .status(UPDATED_STATUS);
        // Add required entity
        Recipe recipe;
        if (TestUtil.findAll(em, Recipe.class).isEmpty()) {
            recipe = RecipeResourceIT.createUpdatedEntity(em);
            em.persist(recipe);
            em.flush();
        } else {
            recipe = TestUtil.findAll(em, Recipe.class).get(0);
        }
        cartHasRecipe.setRecipe(recipe);
        // Add required entity
        Cart cart;
        if (TestUtil.findAll(em, Cart.class).isEmpty()) {
            cart = CartResourceIT.createUpdatedEntity(em);
            em.persist(cart);
            em.flush();
        } else {
            cart = TestUtil.findAll(em, Cart.class).get(0);
        }
        cartHasRecipe.setCart(cart);
        return cartHasRecipe;
    }

    @BeforeEach
    public void initTest() {
        cartHasRecipe = createEntity(em);
    }

    @Test
    @Transactional
    public void createCartHasRecipe() throws Exception {
        int databaseSizeBeforeCreate = cartHasRecipeRepository.findAll().size();
        // Create the CartHasRecipe
        CartHasRecipeDTO cartHasRecipeDTO = cartHasRecipeMapper.toDto(cartHasRecipe);
        restCartHasRecipeMockMvc.perform(post("/api/cart-has-recipes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cartHasRecipeDTO)))
            .andExpect(status().isCreated());

        // Validate the CartHasRecipe in the database
        List<CartHasRecipe> cartHasRecipeList = cartHasRecipeRepository.findAll();
        assertThat(cartHasRecipeList).hasSize(databaseSizeBeforeCreate + 1);
        CartHasRecipe testCartHasRecipe = cartHasRecipeList.get(cartHasRecipeList.size() - 1);
        assertThat(testCartHasRecipe.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createCartHasRecipeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cartHasRecipeRepository.findAll().size();

        // Create the CartHasRecipe with an existing ID
        cartHasRecipe.setId(1L);
        CartHasRecipeDTO cartHasRecipeDTO = cartHasRecipeMapper.toDto(cartHasRecipe);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCartHasRecipeMockMvc.perform(post("/api/cart-has-recipes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cartHasRecipeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CartHasRecipe in the database
        List<CartHasRecipe> cartHasRecipeList = cartHasRecipeRepository.findAll();
        assertThat(cartHasRecipeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCartHasRecipes() throws Exception {
        // Initialize the database
        cartHasRecipeRepository.saveAndFlush(cartHasRecipe);

        // Get all the cartHasRecipeList
        restCartHasRecipeMockMvc.perform(get("/api/cart-has-recipes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cartHasRecipe.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getCartHasRecipe() throws Exception {
        // Initialize the database
        cartHasRecipeRepository.saveAndFlush(cartHasRecipe);

        // Get the cartHasRecipe
        restCartHasRecipeMockMvc.perform(get("/api/cart-has-recipes/{id}", cartHasRecipe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cartHasRecipe.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }


    @Test
    @Transactional
    public void getCartHasRecipesByIdFiltering() throws Exception {
        // Initialize the database
        cartHasRecipeRepository.saveAndFlush(cartHasRecipe);

        Long id = cartHasRecipe.getId();

        defaultCartHasRecipeShouldBeFound("id.equals=" + id);
        defaultCartHasRecipeShouldNotBeFound("id.notEquals=" + id);

        defaultCartHasRecipeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCartHasRecipeShouldNotBeFound("id.greaterThan=" + id);

        defaultCartHasRecipeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCartHasRecipeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCartHasRecipesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        cartHasRecipeRepository.saveAndFlush(cartHasRecipe);

        // Get all the cartHasRecipeList where status equals to DEFAULT_STATUS
        defaultCartHasRecipeShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the cartHasRecipeList where status equals to UPDATED_STATUS
        defaultCartHasRecipeShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllCartHasRecipesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cartHasRecipeRepository.saveAndFlush(cartHasRecipe);

        // Get all the cartHasRecipeList where status not equals to DEFAULT_STATUS
        defaultCartHasRecipeShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the cartHasRecipeList where status not equals to UPDATED_STATUS
        defaultCartHasRecipeShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllCartHasRecipesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        cartHasRecipeRepository.saveAndFlush(cartHasRecipe);

        // Get all the cartHasRecipeList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultCartHasRecipeShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the cartHasRecipeList where status equals to UPDATED_STATUS
        defaultCartHasRecipeShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllCartHasRecipesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        cartHasRecipeRepository.saveAndFlush(cartHasRecipe);

        // Get all the cartHasRecipeList where status is not null
        defaultCartHasRecipeShouldBeFound("status.specified=true");

        // Get all the cartHasRecipeList where status is null
        defaultCartHasRecipeShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllCartHasRecipesByRecipeIsEqualToSomething() throws Exception {
        // Get already existing entity
        Recipe recipe = cartHasRecipe.getRecipe();
        cartHasRecipeRepository.saveAndFlush(cartHasRecipe);
        Long recipeId = recipe.getId();

        // Get all the cartHasRecipeList where recipe equals to recipeId
        defaultCartHasRecipeShouldBeFound("recipeId.equals=" + recipeId);

        // Get all the cartHasRecipeList where recipe equals to recipeId + 1
        defaultCartHasRecipeShouldNotBeFound("recipeId.equals=" + (recipeId + 1));
    }


    @Test
    @Transactional
    public void getAllCartHasRecipesByCartIsEqualToSomething() throws Exception {
        // Get already existing entity
        Cart cart = cartHasRecipe.getCart();
        cartHasRecipeRepository.saveAndFlush(cartHasRecipe);
        Long cartId = cart.getId();

        // Get all the cartHasRecipeList where cart equals to cartId
        defaultCartHasRecipeShouldBeFound("cartId.equals=" + cartId);

        // Get all the cartHasRecipeList where cart equals to cartId + 1
        defaultCartHasRecipeShouldNotBeFound("cartId.equals=" + (cartId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCartHasRecipeShouldBeFound(String filter) throws Exception {
        restCartHasRecipeMockMvc.perform(get("/api/cart-has-recipes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cartHasRecipe.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restCartHasRecipeMockMvc.perform(get("/api/cart-has-recipes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCartHasRecipeShouldNotBeFound(String filter) throws Exception {
        restCartHasRecipeMockMvc.perform(get("/api/cart-has-recipes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCartHasRecipeMockMvc.perform(get("/api/cart-has-recipes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCartHasRecipe() throws Exception {
        // Get the cartHasRecipe
        restCartHasRecipeMockMvc.perform(get("/api/cart-has-recipes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCartHasRecipe() throws Exception {
        // Initialize the database
        cartHasRecipeRepository.saveAndFlush(cartHasRecipe);

        int databaseSizeBeforeUpdate = cartHasRecipeRepository.findAll().size();

        // Update the cartHasRecipe
        CartHasRecipe updatedCartHasRecipe = cartHasRecipeRepository.findById(cartHasRecipe.getId()).get();
        // Disconnect from session so that the updates on updatedCartHasRecipe are not directly saved in db
        em.detach(updatedCartHasRecipe);
        updatedCartHasRecipe
            .status(UPDATED_STATUS);
        CartHasRecipeDTO cartHasRecipeDTO = cartHasRecipeMapper.toDto(updatedCartHasRecipe);

        restCartHasRecipeMockMvc.perform(put("/api/cart-has-recipes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cartHasRecipeDTO)))
            .andExpect(status().isOk());

        // Validate the CartHasRecipe in the database
        List<CartHasRecipe> cartHasRecipeList = cartHasRecipeRepository.findAll();
        assertThat(cartHasRecipeList).hasSize(databaseSizeBeforeUpdate);
        CartHasRecipe testCartHasRecipe = cartHasRecipeList.get(cartHasRecipeList.size() - 1);
        assertThat(testCartHasRecipe.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingCartHasRecipe() throws Exception {
        int databaseSizeBeforeUpdate = cartHasRecipeRepository.findAll().size();

        // Create the CartHasRecipe
        CartHasRecipeDTO cartHasRecipeDTO = cartHasRecipeMapper.toDto(cartHasRecipe);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCartHasRecipeMockMvc.perform(put("/api/cart-has-recipes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cartHasRecipeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CartHasRecipe in the database
        List<CartHasRecipe> cartHasRecipeList = cartHasRecipeRepository.findAll();
        assertThat(cartHasRecipeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCartHasRecipe() throws Exception {
        // Initialize the database
        cartHasRecipeRepository.saveAndFlush(cartHasRecipe);

        int databaseSizeBeforeDelete = cartHasRecipeRepository.findAll().size();

        // Delete the cartHasRecipe
        restCartHasRecipeMockMvc.perform(delete("/api/cart-has-recipes/{id}", cartHasRecipe.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CartHasRecipe> cartHasRecipeList = cartHasRecipeRepository.findAll();
        assertThat(cartHasRecipeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
