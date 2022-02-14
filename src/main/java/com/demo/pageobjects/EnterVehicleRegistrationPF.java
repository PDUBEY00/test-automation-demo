package com.demo.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class EnterVehicleRegistrationPF {

	private WebDriver driver;
	@FindBy(id = "wizard_vehicle_enquiry_capture_vrn_vrn")
	private WebElement registrationNumber;

	@FindBy(id = "submit_vrn_button")
	private WebElement submit;

	public EnterVehicleRegistrationPF(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public void enterRegistrationNo(String regNo) {
		registrationNumber.sendKeys(regNo);
	}

	public void submitRegDetails() {
		registrationNumber.click();
	}

}
