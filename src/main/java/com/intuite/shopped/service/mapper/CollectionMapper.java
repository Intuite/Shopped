package com.intuite.shopped.service.mapper;


import com.intuite.shopped.domain.*;
import com.intuite.shopped.service.dto.CollectionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Collection} and its DTO {@link CollectionDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CollectionMapper extends EntityMapper<CollectionDTO, Collection> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    CollectionDTO toDto(Collection collection);

    @Mapping(source = "userId", target = "user")
    Collection toEntity(CollectionDTO collectionDTO);

    default Collection fromId(Long id) {
        if (id == null) {
            return null;
        }
        Collection collection = new Collection();
        collection.setId(id);
        return collection;
    }
}
