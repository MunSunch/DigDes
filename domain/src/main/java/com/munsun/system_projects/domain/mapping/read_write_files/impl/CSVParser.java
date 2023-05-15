package com.munsun.system_projects.domain.mapping.read_write_files.impl;

import com.munsun.system_projects.domain.mapping.read_write_files.Parser;

public class CSVParser implements Parser {
    @Override
    public String[] parse(String line) {
        return line.split(",");
    }
}
