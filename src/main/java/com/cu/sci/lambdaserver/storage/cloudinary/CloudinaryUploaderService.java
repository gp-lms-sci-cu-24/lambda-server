package com.cu.sci.lambdaserver.storage.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import com.cu.sci.lambdaserver.storage.service.IImageUploaderService;
import com.cu.sci.lambdaserver.utils.validations.FileValidators;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Qualifier("cloudinary")
@Service
@RequiredArgsConstructor
@Slf4j
public class CloudinaryUploaderService implements IImageUploaderService<CloudinaryUploadServiceOptions> {

    private final Cloudinary cloudinary;
    private final static Integer MAX_UPLOAD_SIZE_IN_BYTES= 2* 1024* 1024;

    @Override
    public String uploadImage(MultipartFile image, CloudinaryUploadServiceOptions options) throws IOException {
        /* Check is Valid Image*/
        if (!FileValidators.isImage(image)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only image files are allowed");
        }
        if (!FileValidators.isSizeLessThan(image,MAX_UPLOAD_SIZE_IN_BYTES)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Image size must be less than 2MB");
        }

        Map uploadResult = cloudinary.uploader().upload(image.getBytes(), options.toMap());
        String publicId = (String) uploadResult.get("public_id");

        return cloudinary.url().secure(true).generate(publicId);
    }


    @Override
    public boolean deleteImage(CloudinaryUploadServiceOptions options) {
        /*@TODO: need to upgrade this method Logic*/

        try {
            ApiResponse apiResponse = cloudinary.api().deleteResources(Collections.singletonList(options.getPublicId()),
                    ObjectUtils.asMap("type", "upload", "resource_type", "image"));
            return true;
        } catch (Exception e) {
            log.error("Error happened while deleting image from cloudinary", e);
            return false;
        }
    }
}
