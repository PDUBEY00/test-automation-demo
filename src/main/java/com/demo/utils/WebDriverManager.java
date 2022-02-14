package com.demo.utils;

import com.demo.config.ConfigFileReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import net.bytebuddy.implementation.bytecode.Throw;
import org.openqa.selenium.ImmutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WebDriverManager {

	private static final String CHROME_DRIVER_PROPERTY = "webdriver.chrome.driver";
	private static String browser;
	private static String environmentType;
	private WebDriver driver;

	public WebDriverManager() {
		browser = ConfigFileReader.getInstance().getBrowser();
		environmentType = ConfigFileReader.getInstance().getEnvironment();
	}

	public WebDriver getDriver() {
		if (driver == null) {
			driver = createDriver();
		}
		return driver;
	}

	private WebDriver createDriver() {
		switch (environmentType) {
			case "local":
				driver = createLocalDriver();
				break;
			case "remote":
				driver = createRemoteDriver();
				break;
		}
		return driver;
	}

	private WebDriver createRemoteDriver() {
		ImmutableCapabilities capabilities = new ImmutableCapabilities("browserName", "chrome");
		try {
			driver = new RemoteWebDriver(new URL(ConfigFileReader.getInstance().getSeleniumGridUrl()), capabilities);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new RuntimeException("Issue with the grid the url in config file");
		}
		setCapabilities();
		return driver;
	}

	private WebDriver createLocalDriver() {
		switch (browser) {
			case "firefox":
				driver = new FirefoxDriver();
				break;
			case "chrome":
				System.setProperty(CHROME_DRIVER_PROPERTY, ConfigFileReader.getInstance().getDriverPath());
				driver = new ChromeDriver();
				break;
		}
		setCapabilities();
		return driver;
	}

	private void setCapabilities(){
		if (ConfigFileReader.getInstance().getBrowserWindowSize()) {
			driver.manage().window().maximize();
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigFileReader.getInstance().getImplicitlyWait()));
	}

}
