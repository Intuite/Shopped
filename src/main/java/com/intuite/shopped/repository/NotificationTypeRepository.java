package com.intuite.shopped.repository;

import com.intuite.shopped.domain.NotificationType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the NotificationType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotificationTypeRepository extends JpaRepository<NotificationType, Long>, JpaSpecificationExecutor<NotificationType> {
}
