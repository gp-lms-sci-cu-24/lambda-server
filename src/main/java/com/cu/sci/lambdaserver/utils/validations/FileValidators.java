package com.cu.sci.lambdaserver.utils.validations;

import org.springframework.web.multipart.MultipartFile;

public class FileValidators {
    public static boolean isImage(MultipartFile image) {
        String contentType = image.getContentType();
        return contentType != null && contentType.startsWith("image");
    }

    public static boolean isSizeLessThan(MultipartFile image, long size) {
        return image.getSize() <= size;
    }
}
