package com.intuite.shopped.repository;

import com.intuite.shopped.domain.Collection;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Collection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long>, JpaSpecificationExecutor<Collection> {

    @Query("select collection from Collection collection where collection.user.login = ?#{principal.username}")
    List<Collection> findByUserIsCurrentUser();
}
