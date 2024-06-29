package com.cu.sci.lambdaserver.courseclass.service;

import com.cu.sci.lambdaserver.courseclass.entity.CourseClassTiming;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseClassTimingService implements ICourseClassTimingService {
//    private final CourseClassTimingRepository courseClassTimingRepository;
//    private final LocationRepository locationRepository;
//    private final TimingInDtoMapper timingInDtoMapper;
//    private final TimingOutDtoMapper timingOutDtoMapper;

    @Override
    public boolean isIntersect(CourseClassTiming time1, CourseClassTiming time2) {
        return time1.getDay().equals(time2.getDay()) &&
                time1.getStartTime().before(time2.getEndTime()) &&
                time1.getEndTime().after(time2.getStartTime());
    }


//    private List<CourseClassTiming> getCollsionList(Location location, DayOfWeek day, Time startTime, Time endTime) {
//        return courseClassTimingRepository.findByLocationAndDayAndStartTimeLessThanAndEndTimeGreaterThan(
//                location,
//                day,
//                endTime,
//                startTime
//        );
//    }
//
//    String buildConflictError(List<CourseClassTiming> collision) {
//        StringBuilder error = new StringBuilder();
//        for (CourseClassTiming classTiming : collision) {
//            error.append(classTiming.getId())
//                    .append(" which is between ")
//                    .append(classTiming.getStartTime())
//                    .append(" ")
//                    .append(classTiming.getEndTime())
//                    .append(" \n ");
//        }
//        return error.toString();
//    }
//
//    @Override
//    public CourseClassTimingOutDto addCourseClassTiming(@Valid CourseClassTimingInDto courseClassTimingInDto) {
//        if (courseClassTimingInDto.getStartTime().getTime() >= courseClassTimingInDto.getEndTime().getTime())
//            throw new ResponseStatusException(HttpStatus.CONFLICT, "invalid time range end time should be greater than start time");
//
//        Optional<Location> location = locationRepository.findById(courseClassTimingInDto.getLocationId());
//        if (location.isEmpty())
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "location not found with this id " + courseClassTimingInDto.getLocationId());
//
//        List<CourseClassTiming> collision = getCollsionList(
//                location.get(),
//                courseClassTimingInDto.getDay(),
//                courseClassTimingInDto.getStartTime(),
//                courseClassTimingInDto.getEndTime()
//        );
//        if (!collision.isEmpty())
//            throw new ResponseStatusException(HttpStatus.CONFLICT, "this class will make collision with " + buildConflictError(collision));
//        CourseClassTiming courseClassTiming = timingInDtoMapper.mapTo(courseClassTimingInDto, location.get());
//        courseClassTimingRepository.save(courseClassTiming);
//        return timingOutDtoMapper.mapTo(courseClassTiming);
//    }
//
//    @Override
//    public List<CourseClassTimingOutDto> getAllCourseClassTiming() {
//        return courseClassTimingRepository.findAll().stream().map(timingOutDtoMapper::mapTo).collect(Collectors.toList());
//    }
//
//    @Override
//    public CourseClassTimingOutDto getCourseClassTimingById(Long id) {
//        Optional<CourseClassTiming> courseClassTiming = courseClassTimingRepository.findById(id);
//        if (courseClassTiming.isPresent())
//            return timingOutDtoMapper.mapTo(courseClassTiming.get());
//        else
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, " class timing not found ");
//    }
//
//    @Override
//    public CourseClassTimingOutDto updateCourseClassTiming(Long id, CourseClassTimingInDto newCourseClassTiming) {
//        CourseClassTiming courseClassTiming = courseClassTimingRepository.findById(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "CourseClass Not Found With This Id " + id));
//
//        Optional<Location> location = locationRepository.findById(newCourseClassTiming.getLocationId());
//        if (location.isEmpty())
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "location not found with this id " + newCourseClassTiming.getLocationId());
//
//
//        List<CourseClassTiming> collision = getCollsionList(
//                location.get(),
//                newCourseClassTiming.getDay(),
//                newCourseClassTiming.getStartTime(),
//                newCourseClassTiming.getEndTime()
//        );
//        if (!collision.isEmpty())
//            throw new ResponseStatusException(HttpStatus.CONFLICT, "this new timing  will make collision with " + buildConflictError(collision));
//
//        courseClassTiming.setLocation(location.get());
//        courseClassTiming.setStartTime(newCourseClassTiming.getStartTime());
//        courseClassTiming.setEndTime(newCourseClassTiming.getEndTime());
//        courseClassTiming.setDay(newCourseClassTiming.getDay());
//        courseClassTiming.setType(newCourseClassTiming.getType());
//        return timingOutDtoMapper.mapTo(courseClassTimingRepository.save(courseClassTiming));
//    }
//
//    @Override
//    public CourseClassTimingOutDto deleteCourseClassTiming(Long id) {
//        CourseClassTiming courseClassTiming = courseClassTimingRepository.findById(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "timing Not Found With This Id " + id));
//        courseClassTimingRepository.deleteById(id);
//        return timingOutDtoMapper.mapTo(courseClassTiming);
//    }
}
