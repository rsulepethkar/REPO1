package com.example.tests.stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import Utils.DriverFactory;

public class Hooks {

    @Before
    public void setUp() {
        // Driver initialized in DriverFactory.getDriver()
        DriverFactory.getDriver();

    }

    @After
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
