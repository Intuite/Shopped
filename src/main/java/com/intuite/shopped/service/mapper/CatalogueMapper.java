package com.intuite.shopped.service.mapper;


import com.intuite.shopped.domain.*;
import com.intuite.shopped.service.dto.CatalogueDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Catalogue} and its DTO {@link CatalogueDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CatalogueMapper extends EntityMapper<CatalogueDTO, Catalogue> {



    default Catalogue fromId(Long id) {
        if (id == null) {
            return null;
        }
        Catalogue catalogue = new Catalogue();
        catalogue.setId(id);
        return catalogue;
    }
}
