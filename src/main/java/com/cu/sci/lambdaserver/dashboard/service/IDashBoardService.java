package com.cu.sci.lambdaserver.dashboard.service;

import com.cu.sci.lambdaserver.dashboard.DashBoardDto;
import org.springframework.security.access.prepost.PreAuthorize;

public interface IDashBoardService {

    @PreAuthorize("hasRole('ADMIN')")
    DashBoardDto getDashBoard();


}
