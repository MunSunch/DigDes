package com.munsun.system_projects.domain.mapping.impl;

import com.munsun.system_projects.domain.mapping.Mapper;
import com.munsun.system_projects.domain.model.PostEmployee;

public class PostEmployeeMapper implements Mapper<PostEmployee, com.munsun.system_projects.commons.enums.PostEmployee> {
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
