package com.munsun.system_projects.domain.repository;

public interface ReadWriteFile {
    void readFile();
    void writeFile();
    void close();
}
