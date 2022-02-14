package com.demo.steps;

import com.demo.config.ConfigFileReader;
import com.demo.pageobjects.CheckRegistrationNumberPage;
import com.demo.pageobjects.ConfirmDetailsPage;
import com.demo.pageobjects.EnterVehicleRegistrationPF;
import com.demo.pageobjects.ViewVehicleDetailsPage;
import com.demo.utils.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

public class CarDetailsSteps {
   	WebDriver driver;
	TestContext testContext;

	public CarDetailsSteps(TestContext context) {
		this.testContext = context;
		driver = testContext.getWebDriverManager().getDriver();
	}

	@Given("Vehicle inquiry page is available")
	public void vehicle_inquiry_page_is_available() {
		driver.get(ConfigFileReader.getInstance().getApplicationUrl());
	}
	@When("user enters car {string} details")
	public void user_enters_car_details(String regNum) {
		CheckRegistrationNumberPage checkRegistrationNumberPage = new CheckRegistrationNumberPage(driver);
		checkRegistrationNumberPage.enterRegistrationNo(regNum);;
		checkRegistrationNumberPage.submitRegDetails();
	}
	@When("Confirms the details are correct")
	public void confirms_the_details_are_correct() {
		ConfirmDetailsPage confirmDetails = new ConfirmDetailsPage(driver);
		confirmDetails.confirmRegDetails();
		confirmDetails.submitRegDetails();
	}
	@Then("details of car should be displayed which are {string} and tax due date is {string}")
	public void details_of_car_should_be_displayed_which_are_and_tax_due_date_is(String expectedReg, String taxDueOn) {
		ViewVehicleDetailsPage viewVehicleDetails = new ViewVehicleDetailsPage(driver);
		String actualReg = viewVehicleDetails.registrationNumberDetails().getText();
		String taxDueDate = viewVehicleDetails.taxDueDetails().getText();
		boolean checkTaxDueDate = taxDueDate.contains(taxDueOn);
		Assert.assertTrue("Incorrect Details", checkTaxDueDate);
		Assert.assertEquals("Scenario failed", expectedReg, actualReg);
	}
}
