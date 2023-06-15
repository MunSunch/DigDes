package com.munsun.system_projects.commons.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Set;
import java.util.stream.Collectors;

public enum PostEmployee {
    MANAGER(Set.of(Permission.CREATE_EMPLOYEE,
            Permission.UPDATE_EMPLOYEE,
            Permission.DELETE_EMPLOYEE,
            Permission.READ_EMPLOYEE,

            Permission.CREATE_COMMAND,
            Permission.UPDATE_COMMAND,
            Permission.DELETE_COMMAND,
            Permission.READ_COMMAND,

            Permission.CREATE_PROJECT,
            Permission.UPDATE_PROJECT,
            Permission.DELETE_PROJECT,
            Permission.READ_PROJECT,

            Permission.CREATE_TASK,
            Permission.UPDATE_TASK,
            Permission.DELETE_TASK,
            Permission.READ_TASK)),
    LEAD(Set.of(Permission.CREATE_EMPLOYEE,
            Permission.READ_EMPLOYEE,
            Permission.DELETE_EMPLOYEE,
            Permission.UPDATE_EMPLOYEE,
            Permission.UPDATE_TASK,
            Permission.CREATE_TASK,
            Permission.READ_TASK,
            Permission.DELETE_TASK,
            Permission.READ_COMMAND,
            Permission.READ_PROJECT)),
    DEVELOPER(Set.of(Permission.READ_EMPLOYEE,
            Permission.READ_COMMAND,
            Permission.READ_PROJECT,
            Permission.READ_TASK)),
    TESTER(Set.of(Permission.READ_EMPLOYEE,
            Permission.READ_COMMAND,
            Permission.READ_PROJECT,
            Permission.READ_TASK)),
    DESIGNER(Set.of(Permission.READ_EMPLOYEE,
            Permission.READ_COMMAND,
            Permission.READ_PROJECT,
            Permission.READ_TASK));

    private final Set<Permission> permissions;

    PostEmployee(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
