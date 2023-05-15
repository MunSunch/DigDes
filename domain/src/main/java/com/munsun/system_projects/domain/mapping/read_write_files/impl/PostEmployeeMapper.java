package com.munsun.system_projects.domain.mapping.read_write_files.impl;

import com.munsun.system_projects.domain.mapping.read_write_files.Mapper;
import com.munsun.system_projects.domain.mapping.read_write_files.Parser;
import com.munsun.system_projects.domain.model.PostEmployee;

public class PostEmployeeMapper extends Mapper<PostEmployee> {
    public PostEmployeeMapper(Parser parser) {
        super(parser);
    }

    @Override
    public PostEmployee mapObject(String line) {
        var values = parser.parse(line);
        PostEmployee post = new PostEmployee();
        post.setId(Integer.parseInt(values[0]));
        post.setName(values[1]);
        return post;
    }

    @Override
    public String mapString(PostEmployee post) {
        return post.getId() + ","
                + post.getName();
    }
}
