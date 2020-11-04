package com.intuite.shopped.repository;

import com.intuite.shopped.domain.ReportPost;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the ReportPost entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReportPostRepository extends JpaRepository<ReportPost, Long>, JpaSpecificationExecutor<ReportPost> {

    @Query("select reportPost from ReportPost reportPost where reportPost.user.login = ?#{principal.username}")
    List<ReportPost> findByUserIsCurrentUser();
}
