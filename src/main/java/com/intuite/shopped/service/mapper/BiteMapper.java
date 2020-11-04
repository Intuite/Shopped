package com.intuite.shopped.service.mapper;


import com.intuite.shopped.domain.*;
import com.intuite.shopped.service.dto.BiteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Bite} and its DTO {@link BiteDTO}.
 */
@Mapper(componentModel = "spring", uses = {PostMapper.class, UserMapper.class})
public interface BiteMapper extends EntityMapper<BiteDTO, Bite> {

    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    BiteDTO toDto(Bite bite);

    @Mapping(source = "postId", target = "post")
    @Mapping(source = "userId", target = "user")
    Bite toEntity(BiteDTO biteDTO);

    default Bite fromId(Long id) {
        if (id == null) {
            return null;
        }
        Bite bite = new Bite();
        bite.setId(id);
        return bite;
    }
}
