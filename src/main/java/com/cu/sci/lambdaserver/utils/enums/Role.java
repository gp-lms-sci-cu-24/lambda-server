package com.cu.sci.lambdaserver.utils.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,
    SUPER_ADMIN,
    ADMIN,
    STUDENT,
    PROFESSOR,
    STUDENT_AFFAIR,
    ACADEMIC_ADVISOR,
    STAFF;

    @Override
    public String getAuthority() {
        return String.format("ROLE_%S", this.name());
    }
}
