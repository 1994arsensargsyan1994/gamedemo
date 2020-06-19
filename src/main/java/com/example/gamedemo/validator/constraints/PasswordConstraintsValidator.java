package com.example.gamedemo.validator.constraints;

import com.example.gamedemo.validator.annotaitons.ValidPassword;
import org.passay.LengthRule;
import org.passay.PasswordValidator;

import com.google.common.base.Joiner;
import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class PasswordConstraintsValidator implements ConstraintValidator<ValidPassword, String> {
    @Override
    public boolean isValid(String o, ConstraintValidatorContext constraintValidatorContext) {
        PasswordValidator passwordValidator = new PasswordValidator(Arrays.asList(
                new LengthRule(8, 16),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.Special, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new WhitespaceRule()));
        RuleResult result = passwordValidator.validate(new PasswordData(o));

        if (result.isValid()) {
            return true;
        }
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(
                Joiner.on(",").join(passwordValidator.getMessages(result))).addConstraintViolation();
        return false;
    }


}
