package com.cu.sci.lambdaserver.courseregister.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CourseRegisterTtlTaskConfig {

    private final CourseRegisterConfigProperties courseRegisterConfigProperties;
    private final JdbcTemplate jdbcTemplate;

    @Scheduled(cron = "0 * * * * *") // Cron expression for running every minute
    public void execute() {
        final int ttlDurationSeconds = courseRegisterConfigProperties.getSeconds();

        int result = jdbcTemplate.update("DELETE FROM course_register_sessions WHERE created_at < NOW() - INTERVAL '" + ttlDurationSeconds + " seconds';");
        log.info("Deleted {} records from course_register_sessions table", result);
    }

}
