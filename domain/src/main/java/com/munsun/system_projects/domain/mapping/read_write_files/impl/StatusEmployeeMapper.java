package com.munsun.system_projects.domain.mapping.read_write_files.impl;

import com.munsun.system_projects.domain.mapping.read_write_files.Mapper;
import com.munsun.system_projects.domain.mapping.read_write_files.Parser;
import com.munsun.system_projects.domain.model.StatusEmployee;

public class StatusEmployeeMapper extends Mapper<StatusEmployee> {
    public StatusEmployeeMapper(Parser parser) {
        super(parser);
    }

    @Override
    public StatusEmployee mapObject(String line) {
        String[] values = parser.parse(line);
        StatusEmployee status = new StatusEmployee();
        status.setId(Integer.parseInt(values[0]));
        status.setName(values[1]);
        return status;
    }

    @Override
    public String mapString(StatusEmployee object) {
        return object.getId() + ","
                + object.getName();
    }
}
