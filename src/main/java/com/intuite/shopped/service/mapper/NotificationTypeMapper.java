package com.intuite.shopped.service.mapper;


import com.intuite.shopped.domain.*;
import com.intuite.shopped.service.dto.NotificationTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link NotificationType} and its DTO {@link NotificationTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NotificationTypeMapper extends EntityMapper<NotificationTypeDTO, NotificationType> {



    default NotificationType fromId(Long id) {
        if (id == null) {
            return null;
        }
        NotificationType notificationType = new NotificationType();
        notificationType.setId(id);
        return notificationType;
    }
}
