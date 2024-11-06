package com.test.trans.staff.positive.testcases;

import com.test.trans.staff.base.BaseTest;
import org.testng.annotations.Test;

public class ValidStaffLoginTrans extends BaseTest {

    @Test(priority = 1)
    public void validLoginTest() {
        // Call the login method from BaseTest
        login();

        // Additional assertions or actions after login can be added here if necessary
        System.out.println("Login was successful and dashboard reached.");
    }
}
