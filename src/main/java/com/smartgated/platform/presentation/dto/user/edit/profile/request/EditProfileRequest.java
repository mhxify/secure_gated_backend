package com.smartgated.platform.presentation.dto.user.edit.profile.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EditProfileRequest {
    private String email;
    private String imageUrl ;
}
