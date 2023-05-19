package com.munsun.system_projects.domain.dao;

import java.util.List;

public interface DAO<T> {
    List<T> getAll();
    T getById(int id);

    int save(T obj);
    int update(int id, T obj);

    T deleteById(int id);
    int delete(T obj);
}