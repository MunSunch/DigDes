package com.munsun.system_projects.business.tests.service.impl.specification;

import com.munsun.system_projects.business.tests.model.Employee;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class EmployeeSpecification {
    public static Specification<Employee> getSpecEquals(String name, String lastname, String pytronymic, String email) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(!ObjectUtils.isEmpty(name)) {
                predicates.add(criteriaBuilder.equal(root.get("name"), name));
            }
            if(!ObjectUtils.isEmpty(lastname)) {
                predicates.add(criteriaBuilder.equal(root.get("lastname"), lastname));
            }
            if(!ObjectUtils.isEmpty(pytronymic)) {
                predicates.add(criteriaBuilder.equal(root.get("patronymic"), pytronymic));
            }
            if(!ObjectUtils.isEmpty(email)) {
                predicates.add(criteriaBuilder.equal(root.get("email"), email));
            }
            return query.where(criteriaBuilder.and(predicates.toArray(Predicate[]::new)))
                    .getRestriction();
        };
    }
}
