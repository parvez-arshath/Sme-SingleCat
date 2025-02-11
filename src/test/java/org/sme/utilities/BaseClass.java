package org.sme.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class BaseClass {

	public static WebDriver driver;

	public static void launchBrowser(String browser) {

		if (browser.equals("chrome")) {
			driver = new ChromeDriver();
		}
		if (browser.equals("ie")) {
			driver = new InternetExplorerDriver();
		}
		if (browser.equals("firefox")) {
			driver = new FirefoxDriver();
		}

		if (browser.equals("edge")) {
			driver = new EdgeDriver();
		}

	}

	public static void maximizeWindow() {
		driver.manage().window().maximize();
	}

	public static void quitBrowser() {
		driver.quit();
	}

	public static void launchUrl(String url) {
		driver.get(url);
	}

	public static void fillTextBox(WebElement ele, String name) {
		ele.sendKeys(name);
	}

	public static void clickButton(WebElement btn) {
		btn.click();
	}

	/*
	 * public static void dateAndTime() { Date d = new Date();
	 * System.out.println(d);
	 * 
	 * }
	 */
	public static String getPageUrl() {
		String currentUrl = driver.getCurrentUrl();
		return currentUrl;

	}

	public static String getPageTitle() {

		String title = driver.getTitle();
		return title;
	}

	public static void impWait() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));

	}

	public static Properties loginData() throws IOException {
		FileReader reader = new FileReader(
				System.getProperty("user.dir") + "\\target\\DatasForDistributor\\LoginData.properties");
		Properties props = new Properties();
		props.load(reader);
		return props;

	}

	public static Properties createQuoteData() throws IOException {
		FileReader reader = new FileReader(
				System.getProperty("user.dir") + "\\target\\DatasForDistributor\\createQuote.properties");
		Properties props = new Properties();
		props.load(reader);
		return props;

	}

	/*
	 * public static void jsSendKeys(WebElement args) { JavascriptExecutor js =
	 * (JavascriptExecutor) driver;
	 * js.executeScript("arguments[0].setAttribute('value','')", args);
	 * 
	 * 
	 * }
	 */
	public static Select selectDropDownData(WebElement element, String value) {
		Select s = new Select(element);
		s.selectByValue(value);
		return s;
	}

	public static WebElement dynamicWait(WebElement element) {
		WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(10));
		w.until(ExpectedConditions.elementToBeClickable(element));
		return element;
	}

	public static void jsClick(WebElement args) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click()", args);

	}

	public static String currentDate() {
		LocalDate currentDate = LocalDate.now();

		LocalDate tommrowDate = currentDate.plusDays(1);
		String formattedDate = tommrowDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

		return formattedDate;

	}

	public static Properties calculatorData() throws IOException {
		FileReader reader = new FileReader(
				System.getProperty("user.dir") + "\\target\\DatasForDistributor\\CalculationData.properties");
		Properties props = new Properties();
		props.load(reader);
		return props;

	}

	// base premium query
	public static String basePremiumAIAW(String emirate, String tpa, String plan) throws Exception {

		// Database connection details
		String dbURL = "jdbc:mysql://qa-database.cwfjz6cyloxy.me-south-1.rds.amazonaws.com:3306";
		String dbUsername = "appuser";
		String dbPassword = "appuseradmin";

		// Variables to hold user inputs
		String emirateName, tpaName, planName;

		/*
		 * // Scanner to get user inputs Scanner scanner = new Scanner(System.in);
		 */

		try (Connection connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword)) {
			// Prompt the user for inputs
			/* System.out.print("Enter Emirate Name (e.g., Dubai): "); */
			emirateName = emirate;

			/* System.out.print("Enter TPA Name (e.g., Mednet): "); */
			tpaName = tpa;

			/* System.out.print("Enter Plan Name (e.g., Gold): "); */
			planName = plan;

			// Step 1: Get Active Version ID
			String activeVersionQuery = " WITH ActiveVersion AS (\r\n" + "                    SELECT pv.id\r\n"
					+ "                    FROM 7002_group_medical_dni_transactions.product_versions pv\r\n"
					+ "                    WHERE pv.status = 1 AND pv.effective_date <= CURDATE()\r\n"
					+ "                    ORDER BY pv.effective_date DESC\r\n" + "                    LIMIT 1\r\n"
					+ "                )\r\n" + "                SELECT id FROM ActiveVersion;";

			int activeVersionId = 0;
			try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(activeVersionQuery)) {
				if (rs.next()) {
					activeVersionId = rs.getInt("id");
					 System.out.println(activeVersionId); 
				}
			}

			// Step 2: Get Group ID based on Active Version
			String groupQuery = "SELECT id FROM 7002_group_medical_dni_transactions.group WHERE status = 1 AND version_id = ?";
			int groupId = 0;
			try (PreparedStatement pstmt = connection.prepareStatement(groupQuery)) {
				pstmt.setInt(1, activeVersionId);
				try (ResultSet rs = pstmt.executeQuery()) {
					if (rs.next()) {
						groupId = rs.getInt("id");
						 System.out.println(groupId); 
					}
				}
			}

			// Step 3: Get Emirate ID based on Group ID and Emirate Name
			String emirateQuery = "SELECT id FROM 7002_group_medical_dni_transactions.emirate WHERE group_id = ? AND emirate_name LIKE ?";
			int emirateId = 0;
			try (PreparedStatement pstmt = connection.prepareStatement(emirateQuery)) {
				pstmt.setInt(1, groupId);
				pstmt.setString(2, "%" + emirateName + "%");
				try (ResultSet rs = pstmt.executeQuery()) {
					if (rs.next()) {
						emirateId = rs.getInt("id");
						 System.out.println(emirateId); 
					}
				}
			}

			// Step 4: Get TPA ID based on Group ID, Emirate ID, and TPA Name
			String tpaQuery = "SELECT id FROM 7002_group_medical_dni_transactions.tpa WHERE group_id = ? AND emirate_id = ? AND tpa_name LIKE ?";
			int tpaId = 0;
			try (PreparedStatement pstmt = connection.prepareStatement(tpaQuery)) {
				pstmt.setInt(1, groupId);
				pstmt.setInt(2, emirateId);
				pstmt.setString(3, "%" + tpaName + "%");
				try (ResultSet rs = pstmt.executeQuery()) {
					if (rs.next()) {
						tpaId = rs.getInt("id");
						 System.out.println(tpaId); 
					}
				}
			}

			// Step 5: Get Plan ID based on TPA ID and Plan Name
			String planQuery = "SELECT id FROM 7002_group_medical_dni_transactions.plan WHERE tpa_id = ? AND Plan_name LIKE ?";
			int planId = 0;
			try (PreparedStatement pstmt = connection.prepareStatement(planQuery)) {
				pstmt.setInt(1, tpaId);
				pstmt.setString(2, "%" + planName + "%");
				try (ResultSet rs = pstmt.executeQuery()) {
					if (rs.next()) {
						planId = rs.getInt("id");
						 System.out.println(planId); 
					}
				}
			}

			// Step 6: Fetch Premium Details
			String premiumQuery = "SELECT * FROM 7002_group_medical_dni_transactions.premium WHERE plan_id = ? AND status = 1";

			String valueOf = String.valueOf(planId);

			premiumQuery = premiumQuery.replace("?", valueOf);

			 System.out.println(premiumQuery); 

			return premiumQuery;

		}

	}

	public static void fetchDataFromDatabase(String dbURL, String dbUsername, String dbPassword, String query,
			String excelFilePath, int sheetNum) throws SQLException {
		Connection connection = null;

		try {
			// Connect to the database
			connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			// Open the Excel file
			FileInputStream fileInputStream = new FileInputStream(new File(excelFilePath));
			Workbook workbook = new XSSFWorkbook(fileInputStream);
			Sheet sheet = workbook.getSheetAt(sheetNum); // Modify index if needed

			// Remove existing rows except the first row
			int lastRow = sheet.getLastRowNum();
			for (int i = 1; i <= lastRow; i++) {
				Row row = sheet.getRow(i);
			}

			// Write new data to the Excel sheet
			int rowCount = 1; // Start writing from the second row

			while (resultSet.next()) {
				Row row = sheet.getRow(rowCount);
				if (row == null) {
					row = sheet.createRow(rowCount); // Create a new row if it doesn't exist
				}

				// Loop through each column and write data
				for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
					// Stop writing after column 'O' (15th column, index 14)
					if (i > 15) {
						break;
					}

					Cell cell = row.getCell(i - 1);
					if (cell == null) {
						cell = row.createCell(i - 1);
					}

					// Preserve formulas: Skip overwriting if the cell is a formula
					if (cell.getCellType() == CellType.FORMULA) {
						continue; // Skip cells with formulas
					}

					// Set new value from the database
					cell.setCellValue(resultSet.getString(i));
					System.out.println(cell.getStringCellValue());

				}

				rowCount++; // Move to the next row
			}

			// Close the input stream
			fileInputStream.close();

			// Save the changes to the Excel file
			FileOutputStream fileOutputStream = new FileOutputStream(new File(excelFilePath));
			workbook.write(fileOutputStream);
			fileOutputStream.close();

			// Close workbook
			workbook.close();

			System.out.println("Data has been overwritten in the Excel file successfully.");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void toFetchCensusSheet(String sourceFilePath, String targetFilePath, int sourceSht, int targetSht) {
		/*
		 * String sourceFilePath =
		 * "C:\\Users\\impelox-pc-048\\Desktop\\New folder\\census_ab_automation.xlsx";
		 * // Source // Excel // file String targetFilePath =
		 * "C:\\Users\\impelox-pc-048\\eclipse-workspace\\CalculationSME\\target\\Arshad AIAW.xlsx"
		 * ; // Target // Excel // file
		 */
		try {
// Open the source Excel file
			FileInputStream sourceFile = new FileInputStream(new File(sourceFilePath));
			Workbook sourceWorkbook = new XSSFWorkbook(sourceFile);
			Sheet sourceSheet = sourceWorkbook.getSheetAt(sourceSht); // Source sheet

// Open the target Excel file
			FileInputStream targetFile = new FileInputStream(new File(targetFilePath));
			Workbook targetWorkbook = new XSSFWorkbook(targetFile);
			Sheet targetSheet = targetWorkbook.getSheetAt(targetSht); // Target sheet (modify index as needed)

// Remove existing rows in the target sheet, except the first row
			int lastRow = targetSheet.getLastRowNum();
			for (int i = 1; i <= lastRow; i++) {
				Row row = targetSheet.getRow(i);
				if (row != null) {
					targetSheet.removeRow(row);
				}
			}

// Copy data from source sheet to target sheet
			int rowCount = 1; // Start from the second row to preserve headers

			for (int i = 1; i <= sourceSheet.getLastRowNum(); i++) { // Skip the first row
				Row sourceRow = sourceSheet.getRow(i);
				Row targetRow = targetSheet.createRow(rowCount);

				if (sourceRow != null) {
					for (int j = 0; j < sourceRow.getLastCellNum(); j++) {
						Cell sourceCell = sourceRow.getCell(j);
						Cell targetCell = targetRow.createCell(j);

						if (sourceCell != null) {
// Preserve formulas: Skip overwriting if the cell is a formula
							if (targetCell.getCellType() == CellType.FORMULA) {
								continue;
							}

// Copy the cell value based on the type
							switch (sourceCell.getCellType()) {
							case STRING:
								targetCell.setCellValue(sourceCell.getStringCellValue());
								break;
							case NUMERIC:
								if (DateUtil.isCellDateFormatted(sourceCell)) {
									// Format the date as dd-MM-yyyy
									Date date = sourceCell.getDateCellValue();
									SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
									targetCell.setCellValue(dateFormat.format(date));
								} else {
									targetCell.setCellValue(sourceCell.getNumericCellValue());
								}
								break;

							case BOOLEAN:
								targetCell.setCellValue(sourceCell.getBooleanCellValue());
								break;
							case FORMULA:
								targetCell.setCellFormula(sourceCell.getCellFormula());
								break;
							case BLANK:
								targetCell.setBlank();
								break;
							default:
								break;
							}

						}
					}
				}
				rowCount++;
			}

// Close the input files
			sourceFile.close();
			targetFile.close();

// Save the changes to the target Excel file
			FileOutputStream outputStream = new FileOutputStream(new File(targetFilePath));
			targetWorkbook.write(outputStream);
			outputStream.close();

// Close the workbooks
			sourceWorkbook.close();
			targetWorkbook.close();

			System.out.println("Data copied from source Excel to target Excel successfully.");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String benefitsAIAW(String clientReferenceNumber) {
		// Database connection details
		String dbURL = "jdbc:mysql://qa-database.cwfjz6cyloxy.me-south-1.rds.amazonaws.com:3306";
		String dbUsername = "appuser";
		String dbPassword = "appuseradmin";

		// Construct the query manually
		String benefitsQuery = "SELECT * FROM 7002_group_medical_dni_transactions.benefits_table "
				+ "WHERE client_reference_number LIKE '%" + clientReferenceNumber.trim() + "%'";

		try (Connection connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword)) {
			return benefitsQuery.replaceAll(" /1", "");// Return the clean SQL query string
		} catch (SQLException e) {
			e.printStackTrace();
			return "Error while generating benefits query: " + e.getMessage();
		}
	}

	// nationality loading
	public static String nationalityLoadingQueryAIAW(String emirate, String tpa) {

		String nationalityLoadingsQuery = null;
		// Database connection details
		String dbURL = "jdbc:mysql://qa-database.cwfjz6cyloxy.me-south-1.rds.amazonaws.com:3306";
		String dbUsername = "appuser";
		String dbPassword = "appuseradmin";

		// Variables to hold user inputs
		String emirateName, tpaName;

		// Scanner to get user inputs
		/* Scanner scanner = new Scanner(System.in); */

		try (Connection connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword)) {
			// Prompt the user for inputs
			/* System.out.print("Enter Emirate Name (e.g., Dubai): "); */
			emirateName = emirate;

			/* System.out.print("Enter TPA Name (e.g., Mednet): "); */
			tpaName = tpa;

			// Step 1: Get Active Version ID
			String activeVersionQuery = "WITH ActiveVersion AS (\r\n" + "		                SELECT pv.id\r\n"
					+ "		                FROM 7002_group_medical_dni_transactions.product_versions pv\r\n"
					+ "		                WHERE pv.status = 1 AND pv.effective_date <= CURDATE()\r\n"
					+ "		                ORDER BY pv.effective_date DESC\r\n" + "		                LIMIT 1\r\n"
					+ "		            )\r\n" + "		            SELECT id FROM ActiveVersion;";

			int activeVersionId = 0;
			try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(activeVersionQuery)) {
				if (rs.next()) {
					activeVersionId = rs.getInt("id");
				}
			}

			// Step 2: Get Group ID based on Active Version
			String groupQuery = "SELECT id FROM 7002_group_medical_dni_transactions.group WHERE status = 1 AND version_id = ?";
			int groupId = 0;
			try (PreparedStatement pstmt = connection.prepareStatement(groupQuery)) {
				pstmt.setInt(1, activeVersionId);
				try (ResultSet rs = pstmt.executeQuery()) {
					if (rs.next()) {
						groupId = rs.getInt("id");
					}
				}
			}

			// Step 3: Get Emirate ID based on Group ID and Emirate Name
			String emirateQuery = "SELECT id FROM 7002_group_medical_dni_transactions.emirate WHERE group_id = ? AND emirate_name LIKE ?";
			int emirateId = 0;
			try (PreparedStatement pstmt = connection.prepareStatement(emirateQuery)) {
				pstmt.setInt(1, groupId);
				pstmt.setString(2, "%" + emirateName + "%");
				try (ResultSet rs = pstmt.executeQuery()) {
					if (rs.next()) {
						emirateId = rs.getInt("id");
					}
				}
			}

			// Step 4: Get UW Rules Schema Name based on Group ID, Emirate ID, and TPA Name
			String uwRulesQuery = " SELECT uw_rules_schema_name \r\n"
					+ "		        FROM 7002_group_medical_dni_transactions.tpa \r\n"
					+ "		        WHERE group_id = ? AND emirate_id = ? AND tpa_name LIKE ?";

			String uwRulesSchemaName = "";
			try (PreparedStatement pstmt = connection.prepareStatement(uwRulesQuery)) {
				pstmt.setInt(1, groupId);
				pstmt.setInt(2, emirateId);
				pstmt.setString(3, "%" + tpaName + "%");
				try (ResultSet rs = pstmt.executeQuery()) {
					if (rs.next()) {
						uwRulesSchemaName = rs.getString("uw_rules_schema_name");
					}
				}
			}

			// Step 5: Generate the updated Nationality Loadings query
			if (!uwRulesSchemaName.isEmpty()) {
				nationalityLoadingsQuery = "SELECT ng.group_name, n.nationality, ng.loading_discount " + "FROM "
						+ uwRulesSchemaName + ".nationality_group_mapping gm " + "LEFT JOIN " + uwRulesSchemaName
						+ ".nationality_group ng " + "ON ng.nationality_group_id = gm.nationality_group_id "
						+ "LEFT JOIN " + uwRulesSchemaName + ".nationality n "
						+ "ON n.nationality_id = gm.nationality_id " + "WHERE gm.version_id = " + activeVersionId + ";";

				/* System.out.println("Updated Nationality Loadings Query:"); */
				 System.out.println(nationalityLoadingsQuery); 
			} else {
				System.out.println("No UW Rules Schema Name found.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			/* scanner.close(); */
		}
		return nationalityLoadingsQuery;

	}

	// industry loading
	public static String industryLoadingQueryAIAW(String emirate, String tpa) {
		// Database connection details
		String dbURL = "jdbc:mysql://qa-database.cwfjz6cyloxy.me-south-1.rds.amazonaws.com:3306";
		String dbUsername = "appuser";
		String dbPassword = "appuseradmin";

		// Variables to hold user inputs
		String emirateName, tpaName;
		String industryLoadingsQuery = null;

		// Scanner to get user inputs
		Scanner scanner = new Scanner(System.in);

		try (Connection connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword)) {
			// Prompt the user for inputs
			/* System.out.print("Enter Emirate Name (e.g., Dubai): "); */
			emirateName = emirate;

			/* System.out.print("Enter TPA Name (e.g., Mednet): "); */
			tpaName = tpa;

			// Step 1: Get Active Version ID
			String activeVersionQuery = "WITH ActiveVersion AS (" + "SELECT pv.id "
					+ "FROM 7002_group_medical_dni_transactions.product_versions pv "
					+ "WHERE pv.status = 1 AND pv.effective_date <= CURDATE() " + "ORDER BY pv.effective_date DESC "
					+ "LIMIT 1" + ") " + "SELECT id FROM ActiveVersion;";

			int activeVersionId = 0;
			try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(activeVersionQuery)) {
				if (rs.next()) {
					activeVersionId = rs.getInt("id");
				}
			}

			// Step 2: Get Group ID based on Active Version
			String groupQuery = "SELECT id FROM 7002_group_medical_dni_transactions.group WHERE status = 1 AND version_id = ?";
			int groupId = 0;
			try (PreparedStatement pstmt = connection.prepareStatement(groupQuery)) {
				pstmt.setInt(1, activeVersionId);
				try (ResultSet rs = pstmt.executeQuery()) {
					if (rs.next()) {
						groupId = rs.getInt("id");
					}
				}
			}

			// Step 3: Get Emirate ID based on Group ID and Emirate Name
			String emirateQuery = "SELECT id FROM 7002_group_medical_dni_transactions.emirate WHERE group_id = ? AND emirate_name LIKE ?";
			int emirateId = 0;
			try (PreparedStatement pstmt = connection.prepareStatement(emirateQuery)) {
				pstmt.setInt(1, groupId);
				pstmt.setString(2, "%" + emirateName + "%");
				try (ResultSet rs = pstmt.executeQuery()) {
					if (rs.next()) {
						emirateId = rs.getInt("id");
					}
				}
			}

			// Step 4: Get UW Rules Schema Name based on Group ID, Emirate ID, and TPA Name
			String uwRulesQuery = "SELECT uw_rules_schema_name " + "FROM 7002_group_medical_dni_transactions.tpa "
					+ "WHERE group_id = ? AND emirate_id = ? AND tpa_name LIKE ?";

			String uwRulesSchemaName = "";
			try (PreparedStatement pstmt = connection.prepareStatement(uwRulesQuery)) {
				pstmt.setInt(1, groupId);
				pstmt.setInt(2, emirateId);
				pstmt.setString(3, "%" + tpaName + "%");
				try (ResultSet rs = pstmt.executeQuery()) {
					if (rs.next()) {
						uwRulesSchemaName = rs.getString("uw_rules_schema_name");
					}
				}
			}

			// Step 5: Generate the updated Industry Loadings query
			if (!uwRulesSchemaName.isEmpty()) {
				industryLoadingsQuery = String.format(
						"SELECT im.industry_name, ig.loading_discount " + "FROM %s.industry_group_mapping igm "
								+ "LEFT JOIN %s.industry_group ig ON ig.industry_group_id = igm.industry_group_id "
								+ "LEFT JOIN %s.industry_master im ON im.industry_master_id = igm.industry_master_id;",
						uwRulesSchemaName, uwRulesSchemaName, uwRulesSchemaName);
			} else {
				System.out.println("No UW Rules Schema Name found.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}

		return industryLoadingsQuery;
	}

	public static String previousInsurerLoadingQueryAIAW(String emirate, String tpa) {
		// Database connection details
		String dbURL = "jdbc:mysql://qa-database.cwfjz6cyloxy.me-south-1.rds.amazonaws.com:3306";
		String dbUsername = "appuser";
		String dbPassword = "appuseradmin";

		// Variables to hold user inputs
		String emirateName, tpaName;
		String previousInsurerLoadingsQuery = null;

		// Scanner to get user inputs
		/* Scanner scanner = new Scanner(System.in); */

		try (Connection connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword)) {
			// Prompt the user for inputs
			/* System.out.print("Enter Emirate Name (e.g., Dubai): "); */
			emirateName = emirate;

			/* System.out.print("Enter TPA Name (e.g., Mednet): "); */
			tpaName = tpa;

			// Step 1: Get Active Version ID
			String activeVersionQuery = "WITH ActiveVersion AS (" + "SELECT pv.id "
					+ "FROM 7002_group_medical_dni_transactions.product_versions pv "
					+ "WHERE pv.status = 1 AND pv.effective_date <= CURDATE() " + "ORDER BY pv.effective_date DESC "
					+ "LIMIT 1" + ") " + "SELECT id FROM ActiveVersion;";

			int activeVersionId = 0;
			try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(activeVersionQuery)) {
				if (rs.next()) {
					activeVersionId = rs.getInt("id");
				}
			}

			// Step 2: Get Group ID based on Active Version
			String groupQuery = "SELECT id FROM 7002_group_medical_dni_transactions.group WHERE status = 1 AND version_id = ?";
			int groupId = 0;
			try (PreparedStatement pstmt = connection.prepareStatement(groupQuery)) {
				pstmt.setInt(1, activeVersionId);
				try (ResultSet rs = pstmt.executeQuery()) {
					if (rs.next()) {
						groupId = rs.getInt("id");
					}
				}
			}

			// Step 3: Get Emirate ID based on Group ID and Emirate Name
			String emirateQuery = "SELECT id FROM 7002_group_medical_dni_transactions.emirate WHERE group_id = ? AND emirate_name LIKE ?";
			int emirateId = 0;
			try (PreparedStatement pstmt = connection.prepareStatement(emirateQuery)) {
				pstmt.setInt(1, groupId);
				pstmt.setString(2, "%" + emirateName + "%");
				try (ResultSet rs = pstmt.executeQuery()) {
					if (rs.next()) {
						emirateId = rs.getInt("id");
					}
				}
			}

			// Step 4: Get UW Rules Schema Name based on Group ID, Emirate ID, and TPA Name
			String uwRulesQuery = "SELECT uw_rules_schema_name " + "FROM 7002_group_medical_dni_transactions.tpa "
					+ "WHERE group_id = ? AND emirate_id = ? AND tpa_name LIKE ?";

			String uwRulesSchemaName = "";
			try (PreparedStatement pstmt = connection.prepareStatement(uwRulesQuery)) {
				pstmt.setInt(1, groupId);
				pstmt.setInt(2, emirateId);
				pstmt.setString(3, "%" + tpaName + "%");
				try (ResultSet rs = pstmt.executeQuery()) {
					if (rs.next()) {
						uwRulesSchemaName = rs.getString("uw_rules_schema_name");
					}
				}
			}

			// Step 5: Generate the updated Previous Insurer Loadings query
			if (!uwRulesSchemaName.isEmpty()) {
				previousInsurerLoadingsQuery = String.format(
						"SELECT pg.group_name, pim.previous_insurer_name, pg.loading_discount "
								+ "FROM %s.previous_insurer_group_mapping pgm "
								+ "LEFT JOIN %s.previous_insurer_group pg ON pg.previous_insurer_group_id = pgm.previous_insurer_group_id "
								+ "LEFT JOIN %s.previous_insurer_master pim ON pim.previous_insurer_master_id = pgm.previous_insurer_master_id;",
						uwRulesSchemaName, uwRulesSchemaName, uwRulesSchemaName);
			} else {
				System.out.println("No UW Rules Schema Name found.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			/* scanner.close(); */
		}

		return previousInsurerLoadingsQuery;
	}

	public static String commissionAIAW(String emirate, String tpa, String plan) throws Exception {

		// Database connection details
		String dbURL = "jdbc:mysql://qa-database.cwfjz6cyloxy.me-south-1.rds.amazonaws.com:3306";
		String dbUsername = "appuser";
		String dbPassword = "appuseradmin";

		// Variables to hold user inputs
		String emirateName, tpaName, planName;

		// Scanner to get user inputs
		/* Scanner scanner = new Scanner(System.in); */

		try (Connection connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword)) {
			// Prompt the user for inputs
			/* System.out.print("Enter Emirate Name (e.g., Dubai): "); */
			emirateName = emirate;

			/* System.out.print("Enter TPA Name (e.g., Mednet): "); */
			tpaName = tpa;

			/* System.out.print("Enter Plan Name (e.g., Gold): "); */
			planName = plan;

			// Step 1: Get Active Version ID
			String activeVersionQuery = " WITH ActiveVersion AS (\r\n" + "                    SELECT pv.id\r\n"
					+ "                    FROM 7002_group_medical_dni_transactions.product_versions pv\r\n"
					+ "                    WHERE pv.status = 1 AND pv.effective_date <= CURDATE()\r\n"
					+ "                    ORDER BY pv.effective_date DESC\r\n" + "                    LIMIT 1\r\n"
					+ "                )\r\n" + "                SELECT id FROM ActiveVersion;";

			int activeVersionId = 0;
			try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(activeVersionQuery)) {
				if (rs.next()) {
					activeVersionId = rs.getInt("id");
				}
			}

			// Step 2: Get Group ID based on Active Version
			String groupQuery = "SELECT id FROM 7002_group_medical_dni_transactions.group WHERE status = 1 AND version_id = ?";
			int groupId = 0;
			try (PreparedStatement pstmt = connection.prepareStatement(groupQuery)) {
				pstmt.setInt(1, activeVersionId);
				try (ResultSet rs = pstmt.executeQuery()) {
					if (rs.next()) {
						groupId = rs.getInt("id");
					}
				}
			}

			// Step 3: Get Emirate ID based on Group ID and Emirate Name
			String emirateQuery = "SELECT id FROM 7002_group_medical_dni_transactions.emirate WHERE group_id = ? AND emirate_name LIKE ?";
			int emirateId = 0;
			try (PreparedStatement pstmt = connection.prepareStatement(emirateQuery)) {
				pstmt.setInt(1, groupId);
				pstmt.setString(2, "%" + emirateName + "%");
				try (ResultSet rs = pstmt.executeQuery()) {
					if (rs.next()) {
						emirateId = rs.getInt("id");
					}
				}
			}

			// Step 4: Get TPA ID based on Group ID, Emirate ID, and TPA Name
			String tpaQuery = "SELECT id FROM 7002_group_medical_dni_transactions.tpa WHERE group_id = ? AND emirate_id = ? AND tpa_name LIKE ?";
			int tpaId = 0;
			try (PreparedStatement pstmt = connection.prepareStatement(tpaQuery)) {
				pstmt.setInt(1, groupId);
				pstmt.setInt(2, emirateId);
				pstmt.setString(3, "%" + tpaName + "%");
				try (ResultSet rs = pstmt.executeQuery()) {
					if (rs.next()) {
						tpaId = rs.getInt("id");
					}
				}
			}

			// Step 5: Get Plan ID based on TPA ID and Plan Name
			String planQuery = "SELECT id FROM 7002_group_medical_dni_transactions.plan WHERE tpa_id = ? AND Plan_name LIKE ?";
			int planId = 0;
			try (PreparedStatement pstmt = connection.prepareStatement(planQuery)) {
				pstmt.setInt(1, tpaId);
				pstmt.setString(2, "%" + planName + "%");
				try (ResultSet rs = pstmt.executeQuery()) {
					if (rs.next()) {
						planId = rs.getInt("id");
					}
				}
			}

			// Step 6: Fetch Commission
			String commissionQuery = "SELECT insurer_fee,tpa_fee,aura_commission,distributor_commission,member_type,total FROM 7002_group_medical_dni_transactions.ceding_commission where plan_id=?;";

			String valueOf = String.valueOf(planId);

			commissionQuery = commissionQuery.replace("?", valueOf);

			/* System.out.println(commissionQuery); */

			return commissionQuery;

		}

	}

	public static void newExcelOverride(String dburl, String dbusername, String dbpassword, String basepremium,
			String benefits, String nationality, String industry, String previousInsurer, String commission) {

		// Database connection details
		String dbURL = dburl;
		String dbUsername = dbusername;
		String dbPassword = dbpassword;

		// Queries to execute
		String[] queries = { basepremium, benefits, nationality, industry, previousInsurer, commission };

		// Excel file path
		String excelFilePath = System.getProperty("user.dir")
				+ "\\target\\ExcelCalculatorForDistributor\\Master Piece  new.xlsx";

		Connection connection = null;

		try {
			// Load MySQL JDBC Driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Establish connection
			connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

			// Load the Excel workbook
			FileInputStream fileInputStream = new FileInputStream(excelFilePath);
			Workbook workbook = new XSSFWorkbook(fileInputStream);
			Sheet sheet = workbook.getSheetAt(0);

			// Remove all rows in the sheet
			int rowCount = sheet.getPhysicalNumberOfRows();
			for (int i = 0; i < rowCount; i++) {
				Row row = sheet.getRow(i);
				if (row != null) {
					sheet.removeRow(row); // Removes the row from the sheet
				}
			}

			int startColumn = 0; // Start writing at column A

			// Execute each query and write results with column gaps
			for (String query : queries) {
				startColumn = writeQueryToSheet(connection, query, workbook, sheet, startColumn);
				startColumn += 2; // Leave one empty column as a gap
			}

			// Recalculate formulas
			workbook.setForceFormulaRecalculation(true);

			// Save the workbook
			try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
				workbook.write(outputStream);
			}

			fileInputStream.close();
			workbook.close();
			System.out.println("Data successfully written to Excel sheet with column gaps.");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close database connection
			try {
				if (connection != null) {
					connection.close();
					System.out.println("Database connection closed.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	private static int writeQueryToSheet(Connection connection, String query, Workbook workbook, Sheet sheet,
			int startColumn) {
		try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {

			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();
			int rowCount = 0;

			// Write headers in the first row
			Row headerRow = sheet.getRow(rowCount);
			if (headerRow == null) {
				headerRow = sheet.createRow(rowCount);
			}
			for (int i = 0; i < columnCount; i++) {
				Cell cell = headerRow.createCell(startColumn + i);
				cell.setCellValue(metaData.getColumnName(i + 1));
			}
			rowCount++;

			// Write data
			while (resultSet.next()) {
				Row row = sheet.getRow(rowCount);
				if (row == null) {
					row = sheet.createRow(rowCount);
				}
				for (int i = 0; i < columnCount; i++) {
					Cell cell = row.createCell(startColumn + i);
					Object value = resultSet.getObject(i + 1);

					if (value instanceof String) {
						cell.setCellValue((String) value);
					} else if (value instanceof Number) {
						cell.setCellValue(((Number) value).doubleValue());
					} else if (value instanceof Boolean) {
						cell.setCellValue((Boolean) value);
					} else if (value instanceof Date) {
						cell.setCellValue(value.toString());
					} else {
						cell.setCellValue(value != null ? value.toString() : "");
					}
				}
				rowCount++;
			}

			System.out.println(
					"Data from query written in columns " + (startColumn + 1) + " to " + (startColumn + columnCount));
			return startColumn + columnCount;

		} catch (SQLException e) {
			System.out.println("Error executing query: " + e.getMessage());
			e.printStackTrace();
			return startColumn;
		}
	}

}
