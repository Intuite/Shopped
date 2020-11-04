package com.intuite.shopped.service.mapper;


import com.intuite.shopped.domain.*;
import com.intuite.shopped.service.dto.ReportPostDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReportPost} and its DTO {@link ReportPostDTO}.
 */
@Mapper(componentModel = "spring", uses = {ReportTypeMapper.class, PostMapper.class, UserMapper.class})
public interface ReportPostMapper extends EntityMapper<ReportPostDTO, ReportPost> {

    @Mapping(source = "type.id", target = "typeId")
    @Mapping(source = "type.name", target = "typeName")
    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "post.caption", target = "postCaption")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    ReportPostDTO toDto(ReportPost reportPost);

    @Mapping(source = "typeId", target = "type")
    @Mapping(source = "postId", target = "post")
    @Mapping(source = "userId", target = "user")
    ReportPost toEntity(ReportPostDTO reportPostDTO);

    default ReportPost fromId(Long id) {
        if (id == null) {
            return null;
        }
        ReportPost reportPost = new ReportPost();
        reportPost.setId(id);
        return reportPost;
    }
}
