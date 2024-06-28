package com.cu.sci.lambdaserver.dashboard;

import com.cu.sci.lambdaserver.dashboard.service.DashBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = ("/api/v1/dashboard"))
@RequiredArgsConstructor
public class DashBoardController {

    private final DashBoardService dashBoardService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public DashBoardDto getDashBoard() {
        return dashBoardService.getDashBoard();
    }

}
