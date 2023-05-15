package com.munsun.system_projects.domain.repository.impl;

import com.munsun.system_projects.domain.mapping.read_write_files.Mapper;
import com.munsun.system_projects.domain.model.Account;
import com.munsun.system_projects.domain.model.Employee;
import com.munsun.system_projects.domain.model.PostEmployee;
import com.munsun.system_projects.domain.model.StatusEmployee;
import com.munsun.system_projects.domain.repository.ReadWriteFile;
import com.munsun.system_projects.domain.repository.Repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Set;

public class EmployeeRepository implements Repository<Employee>, ReadWriteFile {
    private String path;
    private Mapper<Employee> mapper;
    private Set<Employee> objects;
    private PostEmployeeRepository postEmployeeRepository;
    private AccountRepository accountRepository;
    private StatusEmployeeRepository statusEmployeeRepository;

    public EmployeeRepository(String path,
                              Mapper<Employee> mapper,
                              PostEmployeeRepository postEmployeeRepository,
                              AccountRepository accountRepository,
                              StatusEmployeeRepository statusEmployeeRepository)
    {
        this.objects = new HashSet<>();
        this.path = path;
        this.mapper = mapper;
        this.postEmployeeRepository = postEmployeeRepository;
        this.accountRepository = accountRepository;
        this.statusEmployeeRepository = statusEmployeeRepository;
    }

    @Override
    public void readFile() {
        accountRepository.readFile();
        statusEmployeeRepository.readFile();
        postEmployeeRepository.readFile();
        try(BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while((line = reader.readLine()) != null) {
                Employee employee = mapper.mapObject(line);
                Account account = accountRepository.getById(Integer.parseInt(line.split(",")[5]));
                PostEmployee post = postEmployeeRepository.getById(Integer.parseInt(line.split(",")[4]));
                StatusEmployee status = statusEmployeeRepository.getById(Integer.parseInt(line.split(",")[7]));
                employee.setPostEmployee(post);
                employee.setAccount(account);
                employee.setStatusEmployee(status);
                objects.add(employee);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeFile() {
        postEmployeeRepository.writeFile();
        accountRepository.writeFile();
        statusEmployeeRepository.writeFile();
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for(var object: objects) {
                String line = object.getId() + ","
                        + object.getName() + ","
                        + object.getLastname() + ","
                        + object.getPytronymic() + ","
                        + object.getPostEmployee().getId() + ","
                        + object.getAccount().getId() + ","
                        + object.getEmail() + ","
                        + object.getStatusEmployee().getId();
                writer.write(line);
                writer.write('\n');
                writer.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(int id, Employee newObj) {
        postEmployeeRepository.create(newObj.getPostEmployee());
        accountRepository.create(newObj.getAccount());
        statusEmployeeRepository.create(newObj.getStatusEmployee());
        deleteById(id);
        create(newObj);
    }

    @Override
    public Employee deleteById(int id) {
        var deleteEmployee = getById(id);
        objects.remove(deleteEmployee);
        return deleteEmployee;
    }

    @Override
    public void create(Employee obj) {
        postEmployeeRepository.create(obj.getPostEmployee());
        accountRepository.create(obj.getAccount());
        statusEmployeeRepository.create(obj.getStatusEmployee());
        objects.add(obj);
    }

    @Override
    public Employee getById(int id) {
        return objects.stream()
                    .filter(x -> x.getId()==id)
                    .toList()
                .get(0);
    }

    @Override
    public Set<Employee> getAll() {
        return new HashSet<Employee>(objects);
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

    public PostEmployeeRepository getPostEmployeeRepository() {
        return postEmployeeRepository;
    }

    public AccountRepository getAccountRepository() {
        return accountRepository;
    }

    public StatusEmployeeRepository getStatusEmployeeRepository() {
        return statusEmployeeRepository;
    }
}
