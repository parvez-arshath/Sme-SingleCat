package org.sme.code;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import org.openqa.selenium.support.ui.Select;
import org.sme.pojo.CreateQuotePojo;
import org.sme.utilities.BaseClass;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SmeDistributorCreateQuote extends BaseClass {

	CreateQuotePojo createQuoteElements;

	@Given("User must be in the create qoute page")
	public void user_must_be_in_the_create_qoute_page() {
		createQuoteElements = new CreateQuotePojo();
		clickButton(createQuoteElements.getCreateQuoteButton());
		assertTrue(createQuoteElements.getCreateNewQuotePage().getText().contains("Create New Quote"));
	}

	@When("User enter company name")
	public void user_enter_company_name() throws IOException {
		fillTextBox(createQuoteElements.getCompanyNameTextBox(), createQuoteData().getProperty("companyName"));
	}

	@When("User enter trade licence number")
	public void user_enter_trade_licence_number() throws IOException {
		fillTextBox(createQuoteElements.getLicenceNumberTextBox(), createQuoteData().getProperty("tradeLicenceNumber"));
	}

	@When("User enter email id")
	public void user_enter_email_id() throws IOException {

		fillTextBox(createQuoteElements.getEmailTextBox(), createQuoteData().getProperty("email"));
	}

	@When("User enter mobile number")
	public void user_enter_mobile_number() throws IOException {

		selectDropDownData(createQuoteElements.getMobileNumberDropDown(), "50");
		fillTextBox(createQuoteElements.getMobileNumberTextBox(), createQuoteData().getProperty("mobileNumber"));
	}

	@When("User select industry categories")
	public void user_select_industry_categories() {
		selectDropDownData(createQuoteElements.getCategoriesDropDown(), "0");
	}

	@When("User click company details next button")
	public void user_click_company_details_next_button() throws InterruptedException {
		Thread.sleep(2000);
		clickButton(createQuoteElements.getCompanyDetailsNextButton());
	}

	@When("User select start date and end date in census")
	public void user_select_start_date_and_end_date_in_census() {
		clickButton(createQuoteElements.getCensusPolicyStartDate());
		createQuoteElements.getCensusPolicyStartDate().clear();
		String currentDate = currentDate();
		System.out.println(currentDate);
		createQuoteElements.getCensusPolicyStartDate().sendKeys(currentDate);
	}

	@When("User select number of categories")
	public void user_select_number_of_categories() {

		selectDropDownData(createQuoteElements.getNumCategoriesDropDown(), "1");
	}

	@When("User enter distributor commission")
	public void user_enter_distributor_commission() {
		fillTextBox(createQuoteElements.getDistributorCommisionTextBox(), "6");
	}

	@When("User enter sales agent")
	public void user_enter_sales_agent() {
		fillTextBox(createQuoteElements.getSalesAgentTextBox(), "aura");
	}

	@When("User click census next button")
	public void user_click_census_next_button() throws InterruptedException {
		Thread.sleep(2000);
		clickButton(createQuoteElements.getCensusNextButton());

	}

	@When("User should choose group")
	public void user_should_choose_group() throws InterruptedException {
		Thread.sleep(2000);
		selectDropDownData(createQuoteElements.getSelectGroupnameDropDown(), "0");
	}

	String emirate;

	@When("User should select emirates category")
	public void user_should_select_emirates_category() {
		Select emirateDropDownData = selectDropDownData(createQuoteElements.getSelectEmiratesDropDownCatA(), "1");
		emirate = emirateDropDownData.getFirstSelectedOption().getText();

	}

	String tpa;

	@When("User should select TPA category")
	public void user_should_select_TPA_category() {

		Select tpaDropDownData = selectDropDownData(createQuoteElements.getSelectTpaCatA(), "0");
		tpa = tpaDropDownData.getFirstSelectedOption().getText();

	}

	String plan;

	@When("User should select plan category")
	public void user_should_select_plan_category() {

		Select planDropDownData = selectDropDownData(createQuoteElements.getSelectPlanCatA(), "0");
		plan = planDropDownData.getFirstSelectedOption().getText();

	}

	@When("User should click and upload template")
	public void user_should_click_and_upload_template() {
		fillTextBox(createQuoteElements.getUploadTemplateId(),
				"C:\\Users\\arsha\\OneDrive\\Desktop\\cencus_a_automation.xlsx");
		clickButton(createQuoteElements.getWarningPopupCancelButton());
	}

	@When("User click proceed")
	public void user_click_proceed() throws InterruptedException {

		Thread.sleep(2000);
		jsClick(createQuoteElements.getProceedBtn());
	}

	@When("User click next button")
	public void user_click_next_button() throws InterruptedException {
		Thread.sleep(3000);
		jsClick(createQuoteElements.getNextButtonChoosePlan());
	}

	@Then("User must displayed with quote created popup message")
	public void user_must_displayed_with_quote_created_popup_message() {

		System.out.println(createQuoteElements.getQouteCreatedPopUpMessage().getText());

	}

	String crn;

	@Then("User should validate the total premium for the created qoute")
	public void user_should_validate_the_total_premium_for_the_created_qoute() throws Exception {
		Thread.sleep(3000);
		crn = createQuoteElements.getCustomerId().getText();
		String benefitsAIAW = benefitsAIAW(crn);
		System.out.println(benefitsAIAW);

		newExcelOverride(calculatorData().getProperty("dbUrlTest"), calculatorData().getProperty("dbUsernameTest"),
				calculatorData().getProperty("dbPasswordTest"), basePremiumAIAW(emirate,tpa, plan),
				benefitsAIAW, nationalityLoadingQueryAIAW(emirate, tpa),
				industryLoadingQueryAIAW(emirate, tpa), previousInsurerLoadingQueryAIAW(emirate, tpa),
				commissionAIAW(emirate, tpa, plan));

		/*
		 * // base premium String basePremiumAIAW = basePremiumAIAW(emirate, tpa, plan);
		 * System.out.println(basePremiumAIAW);
		 * fetchDataFromDatabase(calculatorData().getProperty("dbUrlTest"),
		 * calculatorData().getProperty("dbUsernameTest"),
		 * calculatorData().getProperty("dbPasswordTest"), basePremiumAIAW,
		 * calculatorData().getProperty("excelCalculatorFilePath"), 0);
		 * 
		 * // benefits fetchDataFromDatabase(calculatorData().getProperty("dbUrlTest"),
		 * calculatorData().getProperty("dbUsernameTest"),
		 * calculatorData().getProperty("dbPasswordTest"), benefitsAIAW,
		 * calculatorData().getProperty("excelCalculatorFilePath"), 1);
		 * 
		 * // nationality loadings String nationalityLoadingQueryAIAW =
		 * nationalityLoadingQueryAIAW(emirate, tpa);
		 * System.out.println(nationalityLoadingQueryAIAW);
		 * fetchDataFromDatabase(calculatorData().getProperty("dbUrlTest"),
		 * calculatorData().getProperty("dbUsernameTest"),
		 * calculatorData().getProperty("dbPasswordTest"), nationalityLoadingQueryAIAW,
		 * calculatorData().getProperty("excelCalculatorFilePath"), 4);
		 * 
		 * // industry loading String industryLoadingQueryAIAW =
		 * industryLoadingQueryAIAW(emirate, tpa);
		 * System.out.println(industryLoadingQueryAIAW);
		 * fetchDataFromDatabase(calculatorData().getProperty("dbUrlTest"),
		 * calculatorData().getProperty("dbUsernameTest"),
		 * calculatorData().getProperty("dbPasswordTest"), industryLoadingQueryAIAW,
		 * calculatorData().getProperty("excelCalculatorFilePath"), 5);
		 * 
		 * // previous insurer loading String previousInsurerLoadingQueryAIAW =
		 * previousInsurerLoadingQueryAIAW(emirate, tpa);
		 * System.out.println(previousInsurerLoadingQueryAIAW);
		 * fetchDataFromDatabase(calculatorData().getProperty("dbUrlTest"),
		 * calculatorData().getProperty("dbUsernameTest"),
		 * calculatorData().getProperty("dbPasswordTest"),
		 * previousInsurerLoadingQueryAIAW,
		 * calculatorData().getProperty("excelCalculatorFilePath"), 6);
		 * 
		 * System.out.println(calculatorData().getProperty("queryAIAWCommission")); //
		 * Commission fetchDataFromDatabase(calculatorData().getProperty("dbUrlTest"),
		 * calculatorData().getProperty("dbUsernameTest"),
		 * calculatorData().getProperty("dbPasswordTest"),
		 * calculatorData().getProperty("queryAIAWCommission"),
		 * calculatorData().getProperty("excelCalculatorFilePath"), 9);
		 */
		/*
		 * // Census
		 * toFetchCensusSheet("C:\\Users\\impelox-pc-048\\Desktop\\censuses sheet\\census_a_automation.xlsx"
		 * ,
		 * "C:\\Users\\impelox-pc-048\\eclipse-workspace\\SmeSingleCategory\\target\\ExcelCalculatorForDistributor\\Arshad New Calculator.xlsx"
		 * , 0, 10);
		 */
	}

}
