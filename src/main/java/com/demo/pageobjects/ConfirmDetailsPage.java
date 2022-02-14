package com.demo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ConfirmDetailsPage {

	private WebDriver driver;
	private final By confirmYes = By.id("yes-vehicle-confirm");
	private final By confirmNo = By.id("no-vehicle-confirm");
	private final By continueBtn = By.id("capture_confirm_button");

	public ConfirmDetailsPage(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getConfirmRegDetails() {
		return driver.findElement(confirmYes);
	}

	public void confirmRegDetails() {
		driver.findElement(confirmYes).click();
	}

	public WebElement incorrectRegDetails() {
		return driver.findElement(confirmNo);
	}

	public void submitRegDetails() {
		driver.findElement(continueBtn).click();
	}

}
