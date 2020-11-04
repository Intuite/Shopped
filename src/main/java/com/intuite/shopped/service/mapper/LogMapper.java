package com.intuite.shopped.service.mapper;


import com.intuite.shopped.domain.*;
import com.intuite.shopped.service.dto.LogDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Log} and its DTO {@link LogDTO}.
 */
@Mapper(componentModel = "spring", uses = {LogTypeMapper.class, UserMapper.class})
public interface LogMapper extends EntityMapper<LogDTO, Log> {

    @Mapping(source = "type.id", target = "typeId")
    @Mapping(source = "type.name", target = "typeName")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    LogDTO toDto(Log log);

    @Mapping(source = "typeId", target = "type")
    @Mapping(source = "userId", target = "user")
    Log toEntity(LogDTO logDTO);

    default Log fromId(Long id) {
        if (id == null) {
            return null;
        }
        Log log = new Log();
        log.setId(id);
        return log;
    }
}
