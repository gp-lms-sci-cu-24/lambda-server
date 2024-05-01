//package com.cu.sci.lambdaserver.storage.cloudinary;
//
//import com.cloudinary.Transformation;
//import com.cu.sci.lambdaserver.storage.service.FileUploadServiceOptions;
//import lombok.Builder;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Objects;
//
//@EqualsAndHashCode(callSuper = true)
//@Data
//@Builder
//public class CloudinaryUploadServiceOptions extends FileUploadServiceOptions {
//    private final String folder;
//    private final String publicId;
//    private final Transformation transformation;
//
//    @Override
//    public Map<String, Object> toMap() {
//        Map<String,Object> map = new HashMap<>();
//
//        if(Objects.nonNull(folder)) map.put("folder", folder);
//        if(Objects.nonNull(publicId)) map.put("public_id", publicId);
//        if(Objects.nonNull(transformation)) map.put("transformation", transformation);
//
//        return map;
//    }
//}
