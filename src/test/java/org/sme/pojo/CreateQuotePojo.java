package org.sme.pojo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.sme.utilities.BaseClass;

public class CreateQuotePojo extends BaseClass {

	public CreateQuotePojo() {
		PageFactory.initElements(driver, this);
	}

	
	
	@FindBy(xpath = "//div[text()='Create quote']//parent::div//div//img")
	private WebElement createQuoteButton;

	@FindBy(xpath = "//div[text()=' Create New Quote ']")
	private WebElement createNewQuotePage;

	@FindBy(xpath = "//div[text()='Company Name']//parent::div//input[@type='text']")
	private WebElement companyNameTextBox;

	@FindBy(xpath = "//div[text()='Trade licence number']//parent::div//input[@type='text']")
	private WebElement licenceNumberTextBox;

	@FindBy(xpath = "//div[text()='E-Mail']//parent::div//input[@type='text']")
	private WebElement emailTextBox;

	@FindBy(xpath = "//div[text()='Mobile number']//parent::div//input[@type='text']")
	private WebElement mobileNumberTextBox;

	@FindBy(xpath = "//button[@type='submit' and text()='Next']")
	private WebElement companyDetailsNextButton;

	@FindBy(xpath = "//button[@type='button' and text()='Next']")
	private WebElement censusNextButton;

	@FindBy(xpath = "//div[text()='Select policy start date']//following-sibling::input[@type='date']")
	private WebElement censusPolicyStartDate;

	@FindBy(xpath = "//div[text()='Select policy End Date']//following-sibling::input[@type='date']")
	private WebElement censusPolicyEndDate;

	@FindBy(xpath = "//div[text()='Industry Categories']//following-sibling::select")
	private WebElement categoriesDropDown;

	@FindBy(xpath = "//div[text()='Number of categories']//following-sibling::select")
	private WebElement numCategoriesDropDown;

	@FindBy(xpath = "//div[text()='Mobile number']//following-sibling::div//select")
	private WebElement mobileNumberDropDown;

	@FindBy(xpath = "//input[@formcontrolname='distributor_commission' and @type='text']")
	private WebElement distributorCommisionTextBox;

	@FindBy(xpath = "//div[text()='Sales Agent']//following-sibling::input[@type='text']")
	private WebElement salesAgentTextBox;

	@FindBy(xpath = "//option[text()='Select Previous Insurer']//parent::select")
	private WebElement selectPreviousInsurerDropDown;

	@FindBy(xpath = "//div[text()='Policy End Date']//following-sibling::div//input[@type='date']")
	private WebElement policyEndDate;

	@FindBy(xpath = "//option[text()='Select group name ']//parent::select")
	private WebElement selectGroupnameDropDown;

	@FindBy(xpath = "(//option[text()='Select Emirates ']//parent::select)[1]")
	private WebElement selectEmiratesDropDownCatA;

	@FindBy(xpath = "(//option[text()='Select Emirates ']//parent::select)[2]")
	private WebElement selectEmiratesDropDownCatB;

	@FindBy(xpath = "(//option[text()='Select TPA']//parent::select)[1]")
	private WebElement selectTpaCatA;

	@FindBy(xpath = "(//option[text()='Select TPA']//parent::select)[2]")
	private WebElement selectTpaCatB;

	@FindBy(xpath = "(//option[text()='Select Plans']//parent::select)[1]")
	private WebElement selectPlanCatA;

	@FindBy(xpath = "(//option[text()='Select Plans']//parent::select)[2]")
	private WebElement selectPlanCatB;

	@FindBy(xpath = "//button[text()='Download Template' and @type='button']")
	private WebElement downloadTemplate;

	@FindBy(xpath = "//label[text()=' Upload Template ' ]")
	private WebElement uploadTemplate;

	@FindBy(xpath = "//input[@id='fileClass']")
	private WebElement uploadTemplateId;

	@FindBy(xpath = "//button[text()='Cancel']")
	private WebElement warningPopupCancelButton;

	@FindBy(xpath = "//div[text()='Warning']//parent::div//span")
	private WebElement popupWarningMessage;

	@FindBy(xpath = "//button[text()='Proceed' and @type='submit']")
	private WebElement proceedBtn;

	@FindBy(xpath = "//button[@type='button' and @class='next-button px-3']")
	private WebElement nextButtonChoosePlan;

	@FindBy(xpath = "//div[@id='toast-container']")
	private WebElement qouteCreatedPopUpMessage;
	
	@FindBy(xpath = "//div[@class='row header ng-star-inserted']//div[contains(text(),'Customer ID')]//following-sibling::div")
	private WebElement customerId;
	
	public WebElement getCustomerId() {
		return customerId;
	}

	public WebElement getQouteCreatedPopUpMessage() {
		return qouteCreatedPopUpMessage;
	}

	public WebElement getNextButtonChoosePlan() {
		return nextButtonChoosePlan;
	}

	public WebElement getProceedBtn() {
		return proceedBtn;
	}

	public WebElement getCreateNewQuotePage() {
		return createNewQuotePage;
	}

	public WebElement getCreateQuoteButton() {
		return createQuoteButton;
	}

	public WebElement getCompanyNameTextBox() {
		return companyNameTextBox;
	}

	public WebElement getLicenceNumberTextBox() {
		return licenceNumberTextBox;
	}

	public WebElement getEmailTextBox() {
		return emailTextBox;
	}

	public WebElement getMobileNumberTextBox() {
		return mobileNumberTextBox;
	}

	public WebElement getMobileNumberDropDown() {
		return mobileNumberDropDown;
	}

	public WebElement getCompanyDetailsNextButton() {
		return companyDetailsNextButton;
	}

	public WebElement getCensusNextButton() {
		return censusNextButton;
	}

	public WebElement getCensusPolicyStartDate() {
		return censusPolicyStartDate;
	}

	public WebElement getCensusPolicyEndDate() {
		return censusPolicyEndDate;
	}

	public WebElement getCategoriesDropDown() {
		return categoriesDropDown;
	}

	public WebElement getSalesAgentTextBox() {
		return salesAgentTextBox;
	}

	public WebElement getSelectPreviousInsurerDropDown() {
		return selectPreviousInsurerDropDown;
	}

	public WebElement getPolicyEndDate() {
		return policyEndDate;
	}

	public WebElement getSelectGroupnameDropDown() {
		return selectGroupnameDropDown;
	}

	public WebElement getSelectEmiratesDropDownCatA() {
		return selectEmiratesDropDownCatA;
	}

	public WebElement getSelectEmiratesDropDownCatB() {
		return selectEmiratesDropDownCatB;
	}

	public WebElement getSelectTpaCatA() {
		return selectTpaCatA;
	}

	public WebElement getSelectTpaCatB() {
		return selectTpaCatB;
	}

	public WebElement getSelectPlanCatA() {
		return selectPlanCatA;
	}

	public WebElement getSelectPlanCatB() {
		return selectPlanCatB;
	}

	public WebElement getDownloadTemplate() {
		return downloadTemplate;
	}

	public WebElement getUploadTemplate() {
		return uploadTemplate;
	}

	public WebElement getWarningPopupCancelButton() {
		return warningPopupCancelButton;
	}

	public WebElement getPopupWarningMessage() {
		return popupWarningMessage;
	}

	public WebElement getNumCategoriesDropDown() {
		return numCategoriesDropDown;
	}

	public WebElement getDistributorCommisionTextBox() {
		return distributorCommisionTextBox;
	}

	public WebElement getUploadTemplateId() {
		return uploadTemplateId;
	}

}
