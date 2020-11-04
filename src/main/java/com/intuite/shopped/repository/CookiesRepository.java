package com.intuite.shopped.repository;

import com.intuite.shopped.domain.Cookies;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Cookies entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CookiesRepository extends JpaRepository<Cookies, Long>, JpaSpecificationExecutor<Cookies> {
}
