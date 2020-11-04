package com.intuite.shopped.service.mapper;


import com.intuite.shopped.domain.*;
import com.intuite.shopped.service.dto.CommendationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Commendation} and its DTO {@link CommendationDTO}.
 */
@Mapper(componentModel = "spring", uses = {PostMapper.class, AwardMapper.class, UserMapper.class})
public interface CommendationMapper extends EntityMapper<CommendationDTO, Commendation> {

    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "award.id", target = "awardId")
    @Mapping(source = "award.name", target = "awardName")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    CommendationDTO toDto(Commendation commendation);

    @Mapping(source = "postId", target = "post")
    @Mapping(source = "awardId", target = "award")
    @Mapping(source = "userId", target = "user")
    Commendation toEntity(CommendationDTO commendationDTO);

    default Commendation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Commendation commendation = new Commendation();
        commendation.setId(id);
        return commendation;
    }
}
