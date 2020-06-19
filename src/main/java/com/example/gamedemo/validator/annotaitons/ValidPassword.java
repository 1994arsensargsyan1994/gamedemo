package com.example.gamedemo.validator.annotaitons;


import com.example.gamedemo.validator.constraints.PasswordConstraintsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordConstraintsValidator.class)
@Target({ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ValidPassword {

    String message() default "Password must be equal to or greater than 8 characters and less than 16 characters and contains minimum 1 special, 1 uppercase and 1 digit.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
