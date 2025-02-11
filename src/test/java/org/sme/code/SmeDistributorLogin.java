package org.sme.code;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import org.sme.pojo.DistributorLoginPojo;
import org.sme.utilities.BaseClass;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SmeDistributorLogin extends BaseClass {
	DistributorLoginPojo d;

	@Given("User must be in the login page")
	public void user_must_be_in_the_login_page() throws IOException {
		launchBrowser("chrome");
		launchUrl(loginData().getProperty("url"));
		maximizeWindow();
		impWait();
	}

	@When("User enter valid emailId and password")
	public void user_enter_valid_emailId_and_password() throws InterruptedException, IOException {

		d = new DistributorLoginPojo();
		fillTextBox(d.getUserEmailDistributor(), loginData().getProperty("emailIdDistributor"));
		fillTextBox(d.getUserPasswordDistributor(), loginData().getProperty("passwordDistributor"));
	}

	@When("User click login button")
	public void user_click_login_button() {
		clickButton(d.getUserLoginDistributor());
	}

	@Then("User must be in the home page")
	public void user_must_be_in_the_home_page() throws InterruptedException {
		Thread.sleep(40000);
		String pageUrl = getPageUrl();
		System.out.println(pageUrl);
		assertTrue(pageUrl.contains("dni/distributor"));
	}

}
