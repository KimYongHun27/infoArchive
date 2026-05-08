package com.meta12.infoArchive.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminCreateRequestDto {

    private String name;
    private String email;
    private String password;
    private String phone;
}