package com.munsun.system_projects.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
