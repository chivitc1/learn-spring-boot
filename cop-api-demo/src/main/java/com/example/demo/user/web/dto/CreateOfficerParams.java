package com.example.demo.user.web.dto;

import com.example.demo.user.web.validation.ValidCreateUserParams;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ValidCreateUserParams
public class CreateOfficerParams {
    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min=6, max = 100)
    private String password;
}
