package com.cu.sci.lambdaserver.settings;

import com.cu.sci.lambdaserver.settings.service.SettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/settings")
@RequiredArgsConstructor
public class SettingController {
    private final SettingsService settingService;

    @GetMapping("/{key}")
    @ResponseStatus(HttpStatus.OK)
    public Setting getSetting(@PathVariable String key) {
        return settingService.getSetting(key);
    }

    @PostMapping("/{key}")
    @ResponseStatus(HttpStatus.OK)
    public Setting addSetting(@PathVariable String key, @RequestBody String value) {
        return settingService.addSetting(key, value);
    }

    @PutMapping("/{key}")
    @ResponseStatus(HttpStatus.OK)
    public Setting updateSetting(@PathVariable String key, @RequestBody String value) {
        return settingService.updateSetting(key, value);
    }

    @DeleteMapping("/{key}")
    @ResponseStatus(HttpStatus.OK)
    public Setting deleteSetting(@PathVariable String key) {
        return settingService.deleteSetting(key);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Setting> getAllSettings() {
        return settingService.getAllSettings();
    }
}