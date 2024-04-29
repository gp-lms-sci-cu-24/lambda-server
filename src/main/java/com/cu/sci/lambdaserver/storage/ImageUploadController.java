package com.cu.sci.lambdaserver.storage;

import com.cu.sci.lambdaserver.storage.service.ImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/image")
@RequiredArgsConstructor
public class           ImageUploadController {

    private final ImageUploadService imageUploadService;

    @PostMapping(path = "/user")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadImage(MultipartFile image) {
        return imageUploadService.uploadUserImage(image);
    }


    @DeleteMapping(path = "/user")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImage() {
        imageUploadService.deleteUserImage();
    }

}
