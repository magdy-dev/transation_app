package com.app.employeemanager.config;

public class Security {

    private static Long role = 0L;

    public static Long getRole() {
        return role;
    }

    public static void setRole(Long role) {
        Security.role = role;
    }
}
