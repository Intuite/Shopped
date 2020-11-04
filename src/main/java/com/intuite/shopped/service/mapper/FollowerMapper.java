package com.intuite.shopped.service.mapper;


import com.intuite.shopped.domain.*;
import com.intuite.shopped.service.dto.FollowerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Follower} and its DTO {@link FollowerDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface FollowerMapper extends EntityMapper<FollowerDTO, Follower> {

    @Mapping(source = "userFollowed.id", target = "userFollowedId")
    @Mapping(source = "userFollowed.login", target = "userFollowedLogin")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    FollowerDTO toDto(Follower follower);

    @Mapping(source = "userFollowedId", target = "userFollowed")
    @Mapping(source = "userId", target = "user")
    Follower toEntity(FollowerDTO followerDTO);

    default Follower fromId(Long id) {
        if (id == null) {
            return null;
        }
        Follower follower = new Follower();
        follower.setId(id);
        return follower;
    }
}
