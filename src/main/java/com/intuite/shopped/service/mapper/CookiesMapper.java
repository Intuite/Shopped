package com.intuite.shopped.service.mapper;


import com.intuite.shopped.domain.*;
import com.intuite.shopped.service.dto.CookiesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cookies} and its DTO {@link CookiesDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CookiesMapper extends EntityMapper<CookiesDTO, Cookies> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    CookiesDTO toDto(Cookies cookies);

    @Mapping(source = "userId", target = "user")
    Cookies toEntity(CookiesDTO cookiesDTO);

    default Cookies fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cookies cookies = new Cookies();
        cookies.setId(id);
        return cookies;
    }
}
