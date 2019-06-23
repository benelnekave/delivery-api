package com.ben.test_proj.services.interfaces;


public interface IValidationService {
    boolean isDateWithinLastWeek(String dateToValidate);

    boolean validatePhoneNumber(String number);
}
