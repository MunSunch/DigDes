package com.munsun.system_projects.domain.repository;


import com.munsun.system_projects.domain.mapping.read_write_files.Mapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public abstract class AbstractRepository<T> {
    protected Set<T> objects;
    protected String path;
    protected Mapper<T> mapper;

    public AbstractRepository(String path, Mapper<T> mapper) {
        this.path = path;
        this.mapper = mapper;
        this.objects = new HashSet<>();
    }

    public void readFile() {
        try(BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while((line = reader.readLine()) != null) {
                T obj = mapper.mapObject(line);
                objects.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        writeFile();
        clear();
    }

    public void clear() {
        objects.clear();
    }

    public void writeFile() {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for(var object: objects) {
                String line = mapper.mapString(object);
                writer.write(line);
                writer.write('\n');
                writer.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Set<T> getAll() {
        return new HashSet<T>(objects);
    }

    public void update(int id, T newObj) {
        deleteById(id);
        create(newObj);
    }

    public T deleteById(int id) {
        var deleteAccount = getById(id);
        objects.remove(deleteAccount);
        return deleteAccount;
    }

    public abstract T getById(int id);

    public void create(T obj) {
        objects.add(obj);
    }

    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
}