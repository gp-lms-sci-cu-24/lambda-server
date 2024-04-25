package com.cu.sci.lambdaserver.upload;

import com.cu.sci.lambdaserver.upload.service.ImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/image")
@RequiredArgsConstructor
public class ImageUploadController {

    private final ImageUploadService imageUploadService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadImage(MultipartFile image) {
        return imageUploadService.uploadUserImage(image);
    }

}
