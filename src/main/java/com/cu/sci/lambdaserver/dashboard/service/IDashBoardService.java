package com.cu.sci.lambdaserver.dashboard.service;

import com.cu.sci.lambdaserver.dashboard.dto.DashBoardDto;
import com.cu.sci.lambdaserver.dashboard.dto.StudentCountByYearDto;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface IDashBoardService {

    @PreAuthorize("hasRole('ADMIN')")
    DashBoardDto getDashBoard();

    List<StudentCountByYearDto> getStudentCountByYear();


}
