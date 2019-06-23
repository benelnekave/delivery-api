package com.ben.test_proj;

import com.ben.test_proj.services.interfaces.IValidationService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestProjApplicationTests {

    @Autowired
    private IValidationService validationService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void checkTimeValidation() {
        LocalDateTime date = LocalDateTime.parse(LocalDateTime.now().toString());
        System.out.println(date);
        Assert.assertTrue(validationService.isDateWithinLastWeek(date.toString()));
        date = LocalDateTime.now().minusWeeks(1).plusSeconds(1);
        Assert.assertTrue(validationService.isDateWithinLastWeek(date.toString()));
        Assert.assertFalse(validationService.isDateWithinLastWeek(date.minusHours(2).toString()));
    }

    @Test
    public void checkPhoneNumberValidation() {
        String phoneNumber = "+97524233662";
        Assert.assertTrue(validationService.validatePhoneNumber(phoneNumber));
    }


}
