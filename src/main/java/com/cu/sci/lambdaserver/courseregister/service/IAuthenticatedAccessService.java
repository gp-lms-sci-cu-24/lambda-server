package com.cu.sci.lambdaserver.courseregister.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

public interface IAuthenticatedAccessService {
    @Transactional
    void checkAccessRegisterOrResultResource(String studentUsername);

    @PreAuthorize("hasAnyRole('STUDENT')")
    String getCurrentStudentUsername();
}
