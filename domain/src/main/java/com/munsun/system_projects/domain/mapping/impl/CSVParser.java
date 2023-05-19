package com.munsun.system_projects.domain.mapping.impl;

import com.munsun.system_projects.domain.mapping.Parser;

public class CSVParser implements Parser {
    @Override
    public String[] parse(String line) {
        return line.split(",");
    }
}
