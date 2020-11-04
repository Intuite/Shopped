package com.intuite.shopped.repository;

import com.intuite.shopped.domain.TagType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TagType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TagTypeRepository extends JpaRepository<TagType, Long>, JpaSpecificationExecutor<TagType> {
}
