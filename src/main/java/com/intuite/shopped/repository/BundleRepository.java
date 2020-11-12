package com.intuite.shopped.repository;

import com.intuite.shopped.domain.Bundle;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Bundle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BundleRepository extends JpaRepository<Bundle, Long> {
}
