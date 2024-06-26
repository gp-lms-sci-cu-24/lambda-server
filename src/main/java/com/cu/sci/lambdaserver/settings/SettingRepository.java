package com.cu.sci.lambdaserver.settings;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingRepository extends JpaRepository<Setting, Long> {
    Setting findByKey(String key);

    boolean existsByKey(String key);

}