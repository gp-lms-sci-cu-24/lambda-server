package com.cu.sci.lambdaserver.storage;

import com.cu.sci.lambdaserver.storage.service.IStorageService;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/storage")
@RequiredArgsConstructor
public class StorageController {

    @Qualifier("storage-cloudinary")
    private final IStorageService storageService;

    @PostMapping("/departments/{departmentCode}")
    public MessageResponse uploadDepartmentImage(@PathVariable String departmentCode, MultipartFile image){
        try {
            return new MessageResponse(storageService.uploadDepartmentImage(departmentCode, image)) ;
         } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
