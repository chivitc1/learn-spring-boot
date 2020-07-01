package com.example.demo.user.web.validation;

import com.example.demo.user.UserService;
import com.example.demo.user.web.dto.CreateOfficerParams;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class CreateUserParamValidator implements ConstraintValidator<ValidCreateUserParams, CreateOfficerParams> {

    private final UserService userService;

    @Override
    public void initialize(ValidCreateUserParams constraintAnnotation) {

    }

    @Override
    public boolean isValid(CreateOfficerParams params, ConstraintValidatorContext context) {
        if (userService.findUserByEmail(params.getEmail()).isPresent()) {
            context.buildConstraintViolationWithTemplate(
                    "There is already a user with the given email address."
            ).addPropertyNode("email")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
