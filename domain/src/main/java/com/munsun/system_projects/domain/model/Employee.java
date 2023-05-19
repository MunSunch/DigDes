package com.munsun.system_projects.domain.model;

import lombok.Data;

@Data
public class Employee {
    private int id;
    private String name;
    private String lastname;
    private String pytronymic;
    //OneToMany
    private PostEmployee postEmployee;
    //OneToOne
    private Account account;
    private String email;
    //OneToMany
    private StatusEmployee statusEmployee;

    public Employee(int id, String name, String lastname, String pytronymic, PostEmployee postEmployee, Account account, String email, StatusEmployee statusEmployee) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.pytronymic = pytronymic;
        this.postEmployee = postEmployee;
        this.account = account;
        this.email = email;
        this.statusEmployee = statusEmployee;
    }

    public Employee(String name, String lastname, String pytronymic, PostEmployee postEmployee, Account account, String email, StatusEmployee statusEmployee) {
        this.name = name;
        this.lastname = lastname;
        this.pytronymic = pytronymic;
        this.postEmployee = postEmployee;
        this.account = account;
        this.email = email;
        this.statusEmployee = statusEmployee;
    }

    public Employee() {
    }

    public boolean equalsEmployee(Employee e) {
        return name.equals(e.getName())
                && lastname.equals(e.getLastname())
                && postEmployee.equals(e.getPostEmployee())
                && account.equals(e.getAccount())
                && statusEmployee.equals(e.getStatusEmployee());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee e = (Employee) o;

        return id==e.getId()
                && name.equals(e.getName())
                && lastname.equals(e.getLastname())
                && postEmployee.equals(e.getPostEmployee())
                && account.equals(e.getAccount())
                && statusEmployee.equals(e.getStatusEmployee());
    }
}
