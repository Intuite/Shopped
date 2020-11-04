package com.intuite.shopped.service.mapper;


import com.intuite.shopped.domain.*;
import com.intuite.shopped.service.dto.ReportTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReportType} and its DTO {@link ReportTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ReportTypeMapper extends EntityMapper<ReportTypeDTO, ReportType> {



    default ReportType fromId(Long id) {
        if (id == null) {
            return null;
        }
        ReportType reportType = new ReportType();
        reportType.setId(id);
        return reportType;
    }
}
