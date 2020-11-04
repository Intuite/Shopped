package com.intuite.shopped.repository;

import com.intuite.shopped.domain.Follower;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Follower entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FollowerRepository extends JpaRepository<Follower, Long>, JpaSpecificationExecutor<Follower> {

    @Query("select follower from Follower follower where follower.userFollowed.login = ?#{principal.username}")
    List<Follower> findByUserFollowedIsCurrentUser();

    @Query("select follower from Follower follower where follower.user.login = ?#{principal.username}")
    List<Follower> findByUserIsCurrentUser();
}
