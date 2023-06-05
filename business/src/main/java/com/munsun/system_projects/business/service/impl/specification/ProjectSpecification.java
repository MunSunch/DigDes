package com.munsun.system_projects.business.tests.service.impl.specification;

import com.munsun.system_projects.business.tests.model.Project;
import com.munsun.system_projects.commons.enums.StatusProject;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProjectSpecification {
    public static Specification<Project> filter(String str, StatusProject... statusProjects) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> textPredicates = new ArrayList<>();
            if(!ObjectUtils.isEmpty(str)) {
                textPredicates.add(criteriaBuilder.equal(root.get("name"), str));
                textPredicates.add(criteriaBuilder.equal(root.get("description"), str));
                textPredicates.add(criteriaBuilder.equal(root.get("code"), str));
            }
            var orTextExpression = criteriaBuilder.or(textPredicates.toArray(Predicate[]::new));

            List<Predicate> statusPredicate = new ArrayList<>();
            if(!ObjectUtils.isEmpty(statusProjects)) {
                for(var status: statusProjects) {
                    statusPredicate.add(criteriaBuilder.equal(root.get("status_project_id"), status.name()));
                }
            }
            var orStatusExpression = criteriaBuilder.or(statusPredicate.toArray(Predicate[]::new));

            return query.where(criteriaBuilder.and(orTextExpression, orStatusExpression))
                    .getRestriction();
        };
    }
}
