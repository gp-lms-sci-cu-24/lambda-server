package com.cu.sci.lambdaserver.settings.service;

import com.cu.sci.lambdaserver.settings.Setting;
import com.cu.sci.lambdaserver.settings.SettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SettingsService implements ISettingService {
    private final SettingRepository settingRepository;

    public Setting getSetting(String key) {
        if (!settingRepository.existsByKey(key)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Setting with key '" + key + "' not found.");
        }
        return settingRepository.findByKey(key);
    }

    public Setting addSetting(String key, String value) {
        if (settingRepository.existsByKey(key)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Setting with key '" + key + "' already exists.");
        }
        Setting setting = Setting.builder().key(key).value(value).build();
        return settingRepository.save(setting);
    }

    public Setting updateSetting(String key, String value) {
        if (!settingRepository.existsByKey(key)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Setting with key '" + key + "' not found.");
        }
        Setting existingSetting = settingRepository.findByKey(key);
        existingSetting.setValue(value);
        return settingRepository.save(existingSetting);
    }

    public Setting deleteSetting(String key) {
        if (!settingRepository.existsByKey(key)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Setting with key '" + key + "' not found.");
        }
        Setting existingSetting = settingRepository.findByKey(key);
        settingRepository.delete(existingSetting);
        return existingSetting;
    }

    public List<Setting> getAllSettings() {
        return settingRepository.findAll();
    }
}