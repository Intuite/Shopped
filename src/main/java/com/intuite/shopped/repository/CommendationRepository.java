package com.intuite.shopped.repository;

import com.intuite.shopped.domain.Commendation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Commendation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommendationRepository extends JpaRepository<Commendation, Long>, JpaSpecificationExecutor<Commendation> {

    @Query("select commendation from Commendation commendation where commendation.user.login = ?#{principal.username}")
    List<Commendation> findByUserIsCurrentUser();
}
