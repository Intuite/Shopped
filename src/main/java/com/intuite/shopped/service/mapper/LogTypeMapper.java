package com.intuite.shopped.service.mapper;


import com.intuite.shopped.domain.*;
import com.intuite.shopped.service.dto.LogTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LogType} and its DTO {@link LogTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LogTypeMapper extends EntityMapper<LogTypeDTO, LogType> {



    default LogType fromId(Long id) {
        if (id == null) {
            return null;
        }
        LogType logType = new LogType();
        logType.setId(id);
        return logType;
    }
}
