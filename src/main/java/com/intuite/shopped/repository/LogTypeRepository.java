package com.intuite.shopped.repository;

import com.intuite.shopped.domain.LogType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the LogType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LogTypeRepository extends JpaRepository<LogType, Long>, JpaSpecificationExecutor<LogType> {
}
