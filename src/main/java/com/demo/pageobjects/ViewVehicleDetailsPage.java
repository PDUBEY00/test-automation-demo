package com.demo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ViewVehicleDetailsPage {

	private WebDriver driver;
	private final By registrationNumber = By.className("reg-mark");
	private final By taxDue = By.cssSelector("div.govuk-panel__body");
	public ViewVehicleDetailsPage(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement registrationNumberDetails() {
		return driver.findElement(registrationNumber);
	}

	public WebElement taxDueDetails() {
		return driver.findElement(taxDue);
	}
}
