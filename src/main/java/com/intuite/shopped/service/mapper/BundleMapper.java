package com.intuite.shopped.service.mapper;


import com.intuite.shopped.domain.*;
import com.intuite.shopped.service.dto.BundleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Bundle} and its DTO {@link BundleDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BundleMapper extends EntityMapper<BundleDTO, Bundle> {



    default Bundle fromId(Long id) {
        if (id == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        bundle.setId(id);
        return bundle;
    }
}
