package com.intuite.shopped.repository;

import com.intuite.shopped.domain.Award;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Award entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AwardRepository extends JpaRepository<Award, Long>, JpaSpecificationExecutor<Award> {
}
