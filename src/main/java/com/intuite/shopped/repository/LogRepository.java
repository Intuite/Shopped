package com.intuite.shopped.repository;

import com.intuite.shopped.domain.Log;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Log entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LogRepository extends JpaRepository<Log, Long>, JpaSpecificationExecutor<Log> {

    @Query("select log from Log log where log.user.login = ?#{principal.username}")
    List<Log> findByUserIsCurrentUser();
}
