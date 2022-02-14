package com.demo.config;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileReader {

	private static ConfigFileReader instance;
	private static Properties properties;
	private final String propertyFilePath = "src/test/resources/configs/configuration.properties";

	public ConfigFileReader() {
		//Declare a properties object
		properties = new Properties();
		//Read configuration.properties file
		try {
			properties.load(new FileInputStream(propertyFilePath));
			//prop.load(this.getClass().getClassLoader().getResourceAsStream("configuration.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
		}
	}

	public static ConfigFileReader getInstance() {
		return (instance == null) ? new ConfigFileReader() : instance;
	}

	public String getDriverPath() {
		String driverPath = properties.getProperty("driverPath");
		if (driverPath != null) {
			return driverPath;
		} else {
			throw new RuntimeException("Driver Path not specified in the Configuration.properties file for the Key:driverPath");
		}
	}

	public long getImplicitlyWait() {
		String implicitlyWait = properties.getProperty("implicitlyWait");
		if (implicitlyWait != null) {
			try {
				return Long.parseLong(implicitlyWait);
			} catch (NumberFormatException e) {
				throw new RuntimeException("Not able to parse value : " + implicitlyWait + " in to Long");
			}
		}
		return 30;
	}

	public String getApplicationUrl() {
		String url = properties.getProperty("url");
		if (url != null) {
			return url;
		} else {
			throw new RuntimeException("Application Url not specified in the Configuration.properties file for the Key:url");
		}
	}

	public String getSeleniumGridUrl() {
		String url = properties.getProperty("grid_url");
		if (url != null) {
			return url;
		} else {
			throw new RuntimeException("Selenium grid Url not specified in the Configuration.properties file for the Key:grid_url");
		}
	}

	public String getBrowser() {
		String browserName = properties.getProperty("browser");
		if (browserName == null || browserName.equals("chrome")) {
			return "chrome";
		} else if (browserName.equalsIgnoreCase("firefox")) {
			return "firefox";
		} else if (browserName.equals("iexplorer")) {
			return "ie";
		} else {
			throw new RuntimeException("Browser Name Key value in Configuration.properties is not matched : " + browserName);
		}
	}

	public String getEnvironment() {
		String environmentName = properties.getProperty("environment");
		if (environmentName == null || environmentName.equalsIgnoreCase("local")) {
			return "local";
		} else if (environmentName.equals("remote")) {
			return "remote";
		} else {
			throw new RuntimeException("Environment Type Key value in Configuration.properties is not matched : " + environmentName);
		}
	}

	public Boolean getBrowserWindowSize() {
		String windowSize = properties.getProperty("windowMaximize");
		if (windowSize != null) {
			return Boolean.valueOf(windowSize);
		}
		return true;
	}

	public String getReportConfigPath(){
		String reportConfigPath = properties.getProperty("reportConfigPath");
		if(reportConfigPath!= null) return reportConfigPath;
		else throw new RuntimeException("Report Config Path not specified in the Configuration.properties file for the Key:reportConfigPath");
	}

}
