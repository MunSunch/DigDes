package com.munsun.system_projects.domain.mapping;

public interface Mapper<K,T> {
    K map(T obj);
    T reverseMap(K obj);
}
