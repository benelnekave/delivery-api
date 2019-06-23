package com.ben.test_proj.util;

import com.ben.test_proj.constants.APIMessagesConstants;
import com.ben.test_proj.services.interfaces.IValidationService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ValidationService implements IValidationService {

    @Override
    public boolean isDateWithinLastWeek(String dateToValidate) {
        LocalDateTime dateTime = LocalDateTime.parse(cutTheLastZ(dateToValidate));
        LocalDateTime nowMinusAWeek = LocalDateTime.now().minusWeeks(1).minusMinutes(1); //if an order was deployed now so it should be in
        return dateTime.isAfter(nowMinusAWeek);
    }

    public boolean validatePhoneNumber(String number) {
        return number.matches(APIMessagesConstants.PHONE_VALIDATION_REGEX);
    }

    private String cutTheLastZ(String dateTime) {
        return dateTime.substring(0, dateTime.length() - 1);
    }
}
