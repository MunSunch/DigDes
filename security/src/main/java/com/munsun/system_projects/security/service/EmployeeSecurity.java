package com.munsun.system_projects.security.service;

import com.munsun.system_projects.business.model.Employee;
import com.munsun.system_projects.commons.enums.PostEmployee;
import com.munsun.system_projects.commons.enums.StatusEmployee;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public class EmployeeSecurity implements UserDetails {
    private String login;
    private String password;
    private Set<SimpleGrantedAuthority> authorities;
    private boolean isActive;

    public EmployeeSecurity(String login, String password, Set<SimpleGrantedAuthority> authorities, boolean isActive) {
        this.login = login;
        this.password = password;
        this.authorities = authorities;
        this.isActive = isActive;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    public static UserDetails mapUser(Employee employee) {
        UserDetails user = new User(employee.getAccount().getLogin(),
                employee.getAccount().getPassword(),
                employee.getStatusEmployee().getName().equals(StatusEmployee.ACTIVE.name()),
                employee.getStatusEmployee().getName().equals(StatusEmployee.ACTIVE.name()),
                employee.getStatusEmployee().getName().equals(StatusEmployee.ACTIVE.name()),
                employee.getStatusEmployee().getName().equals(StatusEmployee.ACTIVE.name()),
                PostEmployee.valueOf(employee.getPostEmployee().getName()).getAuthorities());
        return user;
    }
}
