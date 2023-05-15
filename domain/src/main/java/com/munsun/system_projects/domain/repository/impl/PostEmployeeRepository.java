package com.munsun.system_projects.domain.repository.impl;

import com.munsun.system_projects.domain.mapping.read_write_files.Mapper;
import com.munsun.system_projects.domain.model.PostEmployee;
import com.munsun.system_projects.domain.repository.ReadWriteFile;
import com.munsun.system_projects.domain.repository.Repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Set;

public class PostEmployeeRepository implements Repository<PostEmployee>, ReadWriteFile {
    protected Set<PostEmployee> objects;
    protected String path;
    protected Mapper<PostEmployee> mapper;

    public PostEmployeeRepository(String path, Mapper<PostEmployee> mapper) {
        this.objects = new HashSet<>();
        this.path = path;
        this.mapper = mapper;
    }

    @Override
    public void readFile() {
        try(BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while((line = reader.readLine()) != null) {
                PostEmployee obj = mapper.mapObject(line);
                objects.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
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

    @Override
    public Set<PostEmployee> getAll() {
        return new HashSet<PostEmployee>(objects);
    }

    @Override
    public void update(int id, PostEmployee newPost) {
        deleteById(id);
        create(newPost);
    }

    @Override
    public PostEmployee deleteById(int id) {
        var deleteAccount = getById(id);
        objects.remove(deleteAccount);
        return deleteAccount;
    }

    @Override
    public PostEmployee getById(int id) {
        return objects.stream()
                .filter(x -> x.getId()==id)
                .toList()
                .get(0);
    }

    @Override
    public void close() {
        writeFile();
        clear();
    }

    @Override
    public void clear() {
        objects.clear();
    }

    @Override
    public void create(PostEmployee obj) {
        objects.add(obj);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
