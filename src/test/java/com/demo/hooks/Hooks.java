package com.demo.hooks;

import com.demo.utils.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Hooks {

	private final WebDriver driver;
	TestContext context;

	public Hooks(TestContext context) {
		this.context = context;
		this.driver = context.getWebDriverManager().getDriver();
	}

	@Before
	public void setUp() {
	}

	@After
	public void closeDriver() {
		driver.quit();
	}

	@AfterStep
	public void addScreenshot(Scenario scenario) {
		if(scenario.isFailed()){
			final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			scenario.attach(screenshot, "image/png", "image");
		}


	}

}
