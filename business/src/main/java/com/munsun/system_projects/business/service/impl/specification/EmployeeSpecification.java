package com.munsun.system_projects.business.service.impl.specification;

import com.munsun.system_projects.business.model.Employee;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class EmployeeSpecification {
    public static Specification<Employee> getSpecEquals(Employee emp) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(!ObjectUtils.isEmpty(emp.getName()))
                predicates.add(criteriaBuilder.equal(root.get("name"), emp.getName()));
            if(!ObjectUtils.isEmpty(emp.getLastname()))
                predicates.add(criteriaBuilder.equal(root.get("lastname"), emp.getLastname()));
            if(!ObjectUtils.isEmpty(emp.getPatronymic()))
                predicates.add(criteriaBuilder.equal(root.get("patronymic"), emp.getPatronymic()));
            if(!ObjectUtils.isEmpty(emp.getEmail()))
                predicates.add(criteriaBuilder.equal(root.get("email"), emp.getEmail()));
            return query.where(criteriaBuilder.and(predicates.toArray(Predicate[]::new)))
                    .getRestriction();
        };
    }
}
