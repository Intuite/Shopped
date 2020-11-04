package com.intuite.shopped.service.mapper;


import com.intuite.shopped.domain.*;
import com.intuite.shopped.service.dto.AwardDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Award} and its DTO {@link AwardDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AwardMapper extends EntityMapper<AwardDTO, Award> {



    default Award fromId(Long id) {
        if (id == null) {
            return null;
        }
        Award award = new Award();
        award.setId(id);
        return award;
    }
}
