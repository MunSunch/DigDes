package com.munsun.system_projects.business.service.impl.specification;

import com.munsun.system_projects.business.model.Account;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AccountSpecification {
    public static Specification<Account> getSpecEquals(Account account) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(!ObjectUtils.isEmpty(account.getLogin()))
                predicates.add(criteriaBuilder.equal(root.get("login"), account.getLogin()));
            return query.where(predicates.get(0)).getRestriction();
        };
    }
}
