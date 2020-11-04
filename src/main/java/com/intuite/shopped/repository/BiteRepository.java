package com.intuite.shopped.repository;

import com.intuite.shopped.domain.Bite;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Bite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BiteRepository extends JpaRepository<Bite, Long>, JpaSpecificationExecutor<Bite> {

    @Query("select bite from Bite bite where bite.user.login = ?#{principal.username}")
    List<Bite> findByUserIsCurrentUser();
}
