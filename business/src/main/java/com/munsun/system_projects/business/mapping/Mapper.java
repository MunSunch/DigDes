package com.munsun.system_projects.business.mapping.mapping;

public interface Mapper<K,T> {
    K map(T obj);
    T reverseMap(K obj);
}
