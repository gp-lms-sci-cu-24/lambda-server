package com.cu.sci.lambdaserver.coursePrerequisties;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = ("/api/v1/prerequisite"))
@RequiredArgsConstructor
public class CoursePrerequisitesController {
    private final CoursePrerequisitesService coursePrerequisitesService;

    @PostMapping
    public void createPrerequisite(@RequestBody CoursePrerequisites coursePrerequisites) {
        coursePrerequisitesService.createPrerequisite(coursePrerequisites);
    }

    @GetMapping("/{id}")
    public List<Long> getPrerequisite(@PathVariable(name = "id") Long id) {
        return coursePrerequisitesService.getPrerequisite(id);
    }

}