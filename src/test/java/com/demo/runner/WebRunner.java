package com.demo.runner;

import com.demo.config.ConfigFileReader;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import java.io.File;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = { "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
		features = {"classpath:features/"},
		glue = {"com.demo.steps", "com.demo.hooks"},
		tags = "@smoke and not @ignore"
)
public class WebRunner {
}
