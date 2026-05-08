package com.meta12.infoArchive.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordFindRequestDto {

    private String name;
    private String email;
    private String phone;
    private String newPassword;
    private String newPasswordConfirm;
}