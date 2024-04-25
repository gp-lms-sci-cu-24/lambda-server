package com.cu.sci.lambdaserver.upload.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cu.sci.lambdaserver.auth.security.IAuthenticationFacade;
import com.cu.sci.lambdaserver.user.User;
import com.cu.sci.lambdaserver.user.UserRepository;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ImageUploadService implements IimageUploadService {

    @Resource
    private Cloudinary cloudinary;


    private final IAuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;


    /**
     * Upload an image to cloudinary
     * @return url
     */
    @Override
    public String uploadImage(MultipartFile image,String folder) {
        try{

            //check file type
            if(!image.getContentType().startsWith("image")){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Only image files are allowed");
            }

            //check image size
            if(image.getSize() > 2 * 1024 * 1024){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Image size must be less than 2MB");
            }

            //define options
            Map<Object, Object> options = new HashMap<>();
            options.put("folder", folder);
            options.put("transformation",
                    new Transformation()
                            .quality(60)
                            .width(600)
                            .height(600)
                            .fetchFormat("png"));

            //upload the image
            Map uploadResult = cloudinary.uploader().upload(image.getBytes(), options);
            String publicId = (String) uploadResult.get("public_id");
            String url = cloudinary.url().secure(true).generate(publicId) ;

            return url ;

        }catch (Exception e){
            throw new RuntimeException(e) ;
        }
    }

    @Override
    public String uploadUserImage(MultipartFile image) {
        //get the authenticated user
        User user = authenticationFacade.getAuthenticatedUser() ;

        //upload the image
        String url = uploadImage(image,"users") ;

        //save the image url to the user
        user.setProfilePicture(url);
        userRepository.save(user) ;

        return url ;

    }

}
