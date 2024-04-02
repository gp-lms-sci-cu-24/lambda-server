package com.cu.sci.lambdaserver.coursePrerequisties;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = ("/api/dep"))
@RequiredArgsConstructor
public class CoursePrerequisitesController {
    private final CoursePrerequisitesService coursePrerequisitesService;

    @PostMapping
    public void addDependency(@RequestBody CoursePrerequisites coursePrerequisites) {
        coursePrerequisitesService.addDependencies(coursePrerequisites);
    }

    @GetMapping("/{id}")
    public List<Long> GetDependencies(@PathVariable(name = "id") Long id) {
        return coursePrerequisitesService.findDependenciesForCourse(id);
    }

}