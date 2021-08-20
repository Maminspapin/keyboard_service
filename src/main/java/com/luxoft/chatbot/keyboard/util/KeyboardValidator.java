package com.luxoft.chatbot.keyboard.util;

import com.luxoft.chatbot.keyboard.model.dto.KeyboardDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class KeyboardValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return KeyboardDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "buttons", "field.required");

        KeyboardDTO request = (KeyboardDTO) target;
        int buttonsInARow = request.getButtonsInARow();
        if (buttonsInARow < 1 || buttonsInARow > 5) {
            errors.rejectValue(
                    "buttonsInARow",
                    "field.int_range",
                    "ButtonsInARow should be 1-5");
        }
    }
}
