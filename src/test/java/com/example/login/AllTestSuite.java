package com.example.login;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.example.login.model.UserTest;
import com.example.login.model.LoginRequestTest;
import com.example.login.model.LoginResponseTest;
import com.example.login.service.MockAuthServiceTest;
import com.example.login.controller.LoginControllerTest;

@RunWith(Suite.class)
@SuiteClasses({
    UserTest.class,
    LoginRequestTest.class,
    LoginResponseTest.class,
    MockAuthServiceTest.class,
    LoginControllerTest.class
})
public class AllTestSuite {
    // This class remains empty, it is used only as a holder for the above annotations
}