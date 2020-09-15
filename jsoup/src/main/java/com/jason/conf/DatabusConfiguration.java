package com.jason.conf;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface DatabusConfiguration {
    Logger LOGGER = LoggerFactory.getLogger(DatabusConfiguration.class);
    void config(WebElement edit, WebDriver driver);
}
