package com.cu.sci.lambdaserver.classGroup;

import com.cu.sci.lambdaserver.classGroup.dto.ClassGroupDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class CourseClassGroupController {
    private final CourseClassGroupService service;

    public CourseClassGroupController(CourseClassGroupService service) {
        this.service = service;
    }

    @GetMapping
    public List<CourseClassGroup> getAllGroups() {
        return service.getAllCourseClassGroups();
    }

    @GetMapping("/{id}")
    public CourseClassGroup getGroupById(@PathVariable Long id) {
        return service.getCourseClassGroupById(id)
                .orElseThrow(() -> new RuntimeException("Group not found"));
    }
    @PostMapping
    public ResponseEntity<CourseClassGroup> createCourseClassGroup(@RequestBody ClassGroupDto group) {
        CourseClassGroup cg = service.createCourseClassGroup(group);
        if(cg==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(cg, HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<CourseClassGroup> updateCourseClassGroup(@RequestBody ClassGroupDto group) {
        // need to check if the class is indeed available
        CourseClassGroup cg = service.updateCourseClassGroup(group);
        if(cg==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(cg, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteGroup(@RequestBody ClassGroupDto group) {
        // check if the group exists, return a different http request when that happens
        service.deleteCourseClassGroup(group);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/create")
    public void initializeCourseClasses() {
        service.init();
        new ResponseEntity<>("", HttpStatus.CREATED);
    }
}
