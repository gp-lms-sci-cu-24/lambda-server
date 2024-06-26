package com.cu.sci.lambdaserver.settings.service;

import com.cu.sci.lambdaserver.settings.Setting;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface ISettingService {
    @PreAuthorize("hasRole('ADMIN')")
    Setting getSetting(String key);

    @PreAuthorize("hasRole('ADMIN')")
    Setting addSetting(String key, String value);

    @PreAuthorize("hasRole('ADMIN')")
    Setting updateSetting(String key, String value);

    @PreAuthorize("hasRole('ADMIN')")
    Setting deleteSetting(String key);

    @PreAuthorize("hasRole('ADMIN')")
    List<Setting> getAllSettings();
}
