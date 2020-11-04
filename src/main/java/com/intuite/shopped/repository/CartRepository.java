package com.intuite.shopped.repository;

import com.intuite.shopped.domain.Cart;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Cart entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CartRepository extends JpaRepository<Cart, Long>, JpaSpecificationExecutor<Cart> {

    @Query("select cart from Cart cart where cart.user.login = ?#{principal.username}")
    List<Cart> findByUserIsCurrentUser();
}
