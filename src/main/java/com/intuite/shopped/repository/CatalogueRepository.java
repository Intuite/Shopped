package com.intuite.shopped.repository;

import com.intuite.shopped.domain.Catalogue;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Catalogue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CatalogueRepository extends JpaRepository<Catalogue, Long>, JpaSpecificationExecutor<Catalogue> {
}
