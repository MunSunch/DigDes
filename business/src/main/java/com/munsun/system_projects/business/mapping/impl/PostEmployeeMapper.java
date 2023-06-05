package com.munsun.system_projects.business.mapping.mapping.impl;

import com.munsun.system_projects.business.mapping.mapping.Mapper;
import com.munsun.system_projects.business.model.PostEmployee;
import org.springframework.stereotype.Component;

@Component
public class PostEmployeeMapper implements Mapper<PostEmployee, PostEmployee> {
    @Override
    public PostEmployee map(com.munsun.system_projects.commons.enums.PostEmployee obj) {
        PostEmployee post = new PostEmployee();
        post.setName(obj.name());
        return post;
    }

    @Override
    public com.munsun.system_projects.commons.enums.PostEmployee reverseMap(PostEmployee obj) {
        String name = obj.getName();
        return com.munsun.system_projects.commons.enums.PostEmployee.valueOf(name);
    }
}
