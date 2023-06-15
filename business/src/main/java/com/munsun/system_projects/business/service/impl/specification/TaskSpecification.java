package com.munsun.system_projects.business.service.impl.specification;

import com.munsun.system_projects.business.model.Task;
import com.munsun.system_projects.business.model.Task_;
import com.munsun.system_projects.commons.enums.StatusTask;
import com.munsun.system_projects.dto.entity.in.TaskDtoIn;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class TaskSpecification {
    public static Specification<Task> filter(TaskDtoIn taskDtoIn, StatusTask ...statusTasks) {
        return (root, query, criteriaBuilder) -> {
            Predicate textPredicates = null;
            if(!ObjectUtils.isEmpty(taskDtoIn.getName()))
                textPredicates = criteriaBuilder.equal(root.get("name"), taskDtoIn.getName());

            List<Predicate> otherPredicates = new ArrayList<>();
            if(!ObjectUtils.isEmpty(statusTasks)) {
                for(var status: statusTasks)
                    otherPredicates.add(criteriaBuilder.equal(root.get(Task_.STATUS_TASK).get("name"), status.name()));
            }
            if(!ObjectUtils.isEmpty(taskDtoIn.getIdEmployeeExecutor()))
                otherPredicates.add(criteriaBuilder.equal(root.get(Task_.EXECUTOR).get("id"), taskDtoIn.getIdEmployeeExecutor()));
            if(!ObjectUtils.isEmpty(taskDtoIn.getIdEmployeeAuthor()))
                otherPredicates.add(criteriaBuilder.equal(root.get(Task_.AUTHOR_TASK).get("id"), taskDtoIn.getIdEmployeeAuthor()));
            if(!ObjectUtils.isEmpty(taskDtoIn.getEndDate())
                && !ObjectUtils.isEmpty(taskDtoIn.getCreateDate())) {
                otherPredicates.add(criteriaBuilder.and(criteriaBuilder.lessThan(root.get("end_date"), taskDtoIn.getEndDate()),
                        criteriaBuilder.greaterThan(root.get("create_date"), taskDtoIn.getCreateDate())));
            }
            var otherPredicate = criteriaBuilder.or(otherPredicates.toArray(Predicate[]::new));

            return query.where(criteriaBuilder.and(textPredicates, otherPredicate))
                    .getRestriction();
        };
    }
}
