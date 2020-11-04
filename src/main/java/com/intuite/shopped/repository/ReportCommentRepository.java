package com.intuite.shopped.repository;

import com.intuite.shopped.domain.ReportComment;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the ReportComment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReportCommentRepository extends JpaRepository<ReportComment, Long>, JpaSpecificationExecutor<ReportComment> {

    @Query("select reportComment from ReportComment reportComment where reportComment.user.login = ?#{principal.username}")
    List<ReportComment> findByUserIsCurrentUser();
}
