package com.intuite.shopped.service.mapper;


import com.intuite.shopped.domain.*;
import com.intuite.shopped.service.dto.ReportCommentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReportComment} and its DTO {@link ReportCommentDTO}.
 */
@Mapper(componentModel = "spring", uses = {ReportTypeMapper.class, CommentMapper.class, UserMapper.class})
public interface ReportCommentMapper extends EntityMapper<ReportCommentDTO, ReportComment> {

    @Mapping(source = "type.id", target = "typeId")
    @Mapping(source = "type.name", target = "typeName")
    @Mapping(source = "comment.id", target = "commentId")
    @Mapping(source = "comment.content", target = "commentContent")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    ReportCommentDTO toDto(ReportComment reportComment);

    @Mapping(source = "typeId", target = "type")
    @Mapping(source = "commentId", target = "comment")
    @Mapping(source = "userId", target = "user")
    ReportComment toEntity(ReportCommentDTO reportCommentDTO);

    default ReportComment fromId(Long id) {
        if (id == null) {
            return null;
        }
        ReportComment reportComment = new ReportComment();
        reportComment.setId(id);
        return reportComment;
    }
}
