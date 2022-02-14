package com.demo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckRegistrationNumberPage {

	private WebDriver driver;
	private final By registrationNumber = By.id("wizard_vehicle_enquiry_capture_vrn_vrn");
	private final By submit = By.id("submit_vrn_button");

	public CheckRegistrationNumberPage(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getRegistrationNo() {
		return driver.findElement(registrationNumber);
	}

	public void enterRegistrationNo(String regNo) {
		driver.findElement(registrationNumber).sendKeys(regNo);
	}

	public WebElement getSubmitRegDetails() {
		return driver.findElement(submit);
	}

	public void submitRegDetails() {
		driver.findElement(submit).click();
	}
}
