package com.munsun.system_projects.commons.enums;

public enum Permission {
    CREATE_EMPLOYEE("create:employee"),
    UPDATE_EMPLOYEE("update:employee"),
    DELETE_EMPLOYEE("delete:employee"),
    READ_EMPLOYEE("read:employee"),

    CREATE_COMMAND("create:command"),
    UPDATE_COMMAND("update:command"),
    DELETE_COMMAND("delete:command"),
    READ_COMMAND("read:command"),

    CREATE_PROJECT("create:project"),
    UPDATE_PROJECT("update:project"),
    DELETE_PROJECT("delete:project"),
    READ_PROJECT("read:project"),

    CREATE_TASK("create:task"),
    UPDATE_TASK("update:task"),
    DELETE_TASK("delete:task"),
    READ_TASK("read:task");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
