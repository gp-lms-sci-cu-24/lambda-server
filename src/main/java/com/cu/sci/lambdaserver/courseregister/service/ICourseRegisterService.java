package com.cu.sci.lambdaserver.courseregister.service;

import com.cu.sci.lambdaserver.courseregister.CourseRegister;
import com.cu.sci.lambdaserver.courseregister.dto.CourseRegisterInDto;
import org.springframework.data.domain.Page;

public interface ICourseRegisterService {
    CourseRegister createCourseRegister(CourseRegisterInDto courseRegisterInDto);

    Page<CourseRegister> getAllCourseRegisters(Integer pageNo, Integer pageSize);

    CourseRegister getCourseRegister(Long id);

    CourseRegister updateCourseRegister(CourseRegisterInDto courseRegisterInDto);

    CourseRegister deleteCourseRegister(Long id);
}