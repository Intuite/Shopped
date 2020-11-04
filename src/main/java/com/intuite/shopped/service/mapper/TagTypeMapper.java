package com.intuite.shopped.service.mapper;


import com.intuite.shopped.domain.*;
import com.intuite.shopped.service.dto.TagTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TagType} and its DTO {@link TagTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TagTypeMapper extends EntityMapper<TagTypeDTO, TagType> {



    default TagType fromId(Long id) {
        if (id == null) {
            return null;
        }
        TagType tagType = new TagType();
        tagType.setId(id);
        return tagType;
    }
}
