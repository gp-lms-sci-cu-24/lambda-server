package com.cu.sci.lambdaserver.timingregister.service;

import com.cu.sci.lambdaserver.courseclass.entity.CourseClass;
import com.cu.sci.lambdaserver.courseclass.entity.CourseClassTiming;
import com.cu.sci.lambdaserver.courseclass.repository.CourseClassTimingRepository;
import com.cu.sci.lambdaserver.courseclass.service.CourseClassService;
import com.cu.sci.lambdaserver.timingregister.TimingRegister;
import com.cu.sci.lambdaserver.timingregister.TimingRegisterRepository;
import com.cu.sci.lambdaserver.timingregister.dto.TimingRegisterInDto;
import com.cu.sci.lambdaserver.timingregister.mapper.TimingRegisterInDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TimingRegisterService implements ITimingRegisterService {

    private final TimingRegisterRepository timingRegisterRepository;
    private final CourseClassTimingRepository courseClassTimingRepository;
    private final CourseClassService courseClassService;

    private final TimingRegisterInDtoMapper timingRegisterInDtoMapper;

    @Override
    public TimingRegister createTimingRegister(TimingRegisterInDto timingRegisterInDto) {
        Long courseClassId = timingRegisterInDto.getCourseClassId();
        Long courseClassTimingId = timingRegisterInDto.getCourseClassTimingId();

        CourseClassTiming courseClassTiming = courseClassTimingRepository.findById(courseClassTimingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "course class timing not found with this id"));
        CourseClass courseClass = courseClassService.getCourseClassById(courseClassId);
        if (timingRegisterRepository
                .findTimingRegisterByCourseClass_CourseClassIdAndCourseClassTiming_Id(courseClassId, courseClassTimingId)
                .isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "timing register already exists");
        }
        // this needs more work
        Integer sum = 0;
        for (Integer c : courseClassTiming
                .getCourseClassTimings()
                .stream()
                .map(TimingRegister::getCourseClass)
                .map(CourseClass::getMaxCapacity)
                .toList()) {
            sum += c;
        }
        log.info("Student: {}", sum);

        if (sum + courseClass.getMaxCapacity() > courseClassTiming.getLocation().getMaxCapacity()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "could not register class, too much max capacity");
        }
        TimingRegister timingRegister = new TimingRegister();
        timingRegister.setCourseClass(courseClass);
        timingRegister.setCourseClassTiming(courseClassTiming);
        return timingRegisterRepository.save(timingRegister);
    }

    @Override
    public Page<TimingRegister> getAllTimingRegisters(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return timingRegisterRepository.findAll(pageable);
    }

    @Override
    public TimingRegister getTimingRegister(Long id) {
        return timingRegisterRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "timing register not found with this id"));
    }

    @Override
    public TimingRegister updateTimingRegister(TimingRegisterInDto timingRegisterInDto) {
        return timingRegisterRepository.findById(timingRegisterInDto.getTimingRegisterId())
                .map(courseRegister -> {
                    timingRegisterInDtoMapper.update(timingRegisterInDto, courseRegister);
                    return timingRegisterRepository.save(courseRegister);
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "course register not found with this id"));
    }

    @Override
    public TimingRegister deleteTimingRegister(Long id) {
        TimingRegister timingRegister = getTimingRegister(id);
        timingRegisterRepository.deleteById(id);
        return timingRegister;
    }

    public TimingRegister getTimingRegisterByClassIdAndTimingId(Long classId, Long timingId) {
        return timingRegisterRepository
                .findTimingRegisterByCourseClass_CourseClassIdAndCourseClassTiming_Id(classId, timingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "timing register was not found with this class and timing id"));
    }

    public List<CourseClassTiming> getTimingRegisterByClassId(Long classId) {
        List<TimingRegister> timingRegisters = timingRegisterRepository.findCourseClassTimTimingRegistersByCourseClass_CourseClassId(classId);
        List<CourseClassTiming> courseClassTimings = new ArrayList<>();
        timingRegisters.forEach(timingRegister -> {
            courseClassTimings.add(timingRegister.getCourseClassTiming());
        });
        return courseClassTimings;
    }
}
