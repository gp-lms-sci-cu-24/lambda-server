package com.cu.sci.lambdaserver.coursePrerequisties;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = ("/api/v1/course"))
@RequiredArgsConstructor
public class CoursePrerequisitesController {
    private final CoursePrerequisitesService coursePrerequisitesService;

    @PostMapping("/{id}/prerequisites/{prerequisite}")
    @ResponseStatus(HttpStatus.CREATED)
    public CoursePrerequisites addPrerequisite(@PathVariable(name = "id") Long courseId, @PathVariable(name = "prerequisite") Long prerequisiteId) {
        return coursePrerequisitesService.addPrerequisite(courseId, prerequisiteId);
    }

    @GetMapping("/{id}/prerequisites")
    @ResponseStatus(HttpStatus.OK)
    public List<Long> getPrerequisite(@PathVariable(name = "id") Long id) {
        return coursePrerequisitesService.getPrerequisite(id);
    }

}