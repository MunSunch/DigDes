package com.munsun.system_projects.domain.repository;

import java.util.Set;

public interface Repository<T> {
    Set<T> getAll();
    T deleteById(int id);
    T getById(int id);
    void create(T obj);
    void update(int id, T obj);
    void clear();
}
