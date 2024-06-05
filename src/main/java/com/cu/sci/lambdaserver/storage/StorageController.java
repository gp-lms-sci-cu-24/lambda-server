package com.cu.sci.lambdaserver.storage;

import com.cu.sci.lambdaserver.auth.security.IAuthenticationFacade;
import com.cu.sci.lambdaserver.storage.service.IStorageService;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/v1/storage")
@RequiredArgsConstructor
public class StorageController {

    @Qualifier("storage-cloudinary")
    private final IStorageService storageService;
    private final IAuthenticationFacade authenticationFacade;

    @PostMapping("/departments/{departmentCode}")
    public MessageResponse uploadDepartmentImage(@PathVariable String departmentCode, MultipartFile image) {
        return storageService.uploadDepartmentImage(departmentCode, image);
    }

    @PostMapping("/locations/{locationId}")
    public MessageResponse uploadLocationImage(@PathVariable Long locationId, MultipartFile image) {
        return storageService.uploadLocationImage(locationId, image);
    }

    @PostMapping("/my/profile")
    public MessageResponse uploadMyProfileImage(MultipartFile image) {
        return storageService.uploadUserImage(authenticationFacade.getAuthenticatedUser(), image);
    }

    @PostMapping("/users/profile/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public MessageResponse uploadUserProfileImage(@PathVariable String username, MultipartFile image) {
        return storageService.uploadUserImage(username, image);
    }
}
