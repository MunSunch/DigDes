package com.munsun.system_projects.business.service.impl.specification;

import com.munsun.system_projects.business.model.Project;
import com.munsun.system_projects.business.model.Project_;
import com.munsun.system_projects.commons.enums.StatusProject;
import com.munsun.system_projects.dto.entity.in.ProjectDtoIn;
import com.munsun.system_projects.dto.entity.out.ProjectDtoOut;
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
                textPredicates.add(criteriaBuilder.equal(root.get("code"), str));
                textPredicates.add(criteriaBuilder.equal(root.get("description"), str));
            }
            var orTextExpression = criteriaBuilder.or(textPredicates.toArray(Predicate[]::new));

            List<Predicate> statusPredicate = new ArrayList<>();
            if(!ObjectUtils.isEmpty(statusProjects)) {
                for(var status: statusProjects) {
                    statusPredicate.add(criteriaBuilder.equal(root.get(Project_.STATUS).get("name"), status.name()));
                }
            } else {
                for(var status: StatusProject.values()) {
                    statusPredicate.add(criteriaBuilder.equal(root.get(Project_.STATUS).get("name"), status.name()));
                }
            }
            var orStatusesPredicate = criteriaBuilder.or(statusPredicate.toArray(Predicate[]::new));

            return query.where(criteriaBuilder.and(orTextExpression, orStatusesPredicate))
                    .getRestriction();
        };
    }

    public static Specification<Project> equals(ProjectDtoIn projectDtoIn) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
//            predicates.add(criteriaBuilder.equal(root.get("code"), project.getCode()));
            predicates.add(criteriaBuilder.equal(root.get("name"), projectDtoIn.getName()));
            if(!ObjectUtils.isEmpty(projectDtoIn.getDescription()))
                predicates.add(criteriaBuilder.equal(root.get("description"), projectDtoIn.getDescription()));
            return query.where(criteriaBuilder.or(predicates.toArray(Predicate[]::new)))
                    .getRestriction();
        };
    }
}
