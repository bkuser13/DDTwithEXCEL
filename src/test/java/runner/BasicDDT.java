package runner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputFilter.Config;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.prestashop.pages.GasMilageCalculatorPage;
import com.prestashop.utilities.ConfigurationReader;
import com.prestashop.utilities.Driver;

public class BasicDDT {

	WebDriver driver;
	Workbook workbook;
	Sheet worksheet;
	FileInputStream inStream;
	FileOutputStream outStream;
	GasMilageCalculatorPage page;
	public static final int CURRENT_OD_COLUMN = 1;
	public static final int PREV_OD_COLUMN = 2;
	public static final int GAS_COLUMN = 3;

	@BeforeClass
	public void setUp() throws Exception {
		inStream = new FileInputStream(ConfigurationReader.getProperty("gasmileage.test.data.path"));
		workbook = WorkbookFactory.create(inStream);
		worksheet = workbook.getSheetAt(0);
		driver = Driver.getDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().fullscreen();
		driver.get("https://www.calculator.net/gas-mileage-calculator.html");

	}

	@AfterClass
	public void tearDown() throws IOException {
		outStream = new FileOutputStream(ConfigurationReader.getProperty("gasmileage.test.data.path"));
		workbook.write(outStream);
		outStream.close();
		workbook.close();
		inStream.close();
		driver.close();
	}

	@Test
	public void dataDrivenMilageCalculator() {

		for (int rownum = 1; rownum < worksheet.getPhysicalNumberOfRows(); rownum++) {

			Row currentRow = worksheet.getRow(rownum);

			//check control column. If it doesn't say Y, then skip this row
			if(!currentRow.getCell(0).toString().equalsIgnoreCase("Y")) {
				if(currentRow.getCell(6)==null) {
					currentRow.getCell(6);
				}
				currentRow.getCell(6).setCellValue("Skip requested");
				continue;
			}



			double currentOr = currentRow.getCell(CURRENT_OD_COLUMN).getNumericCellValue();
			double previousOr = currentRow.getCell(PREV_OD_COLUMN).getNumericCellValue();
			double gas = currentRow.getCell(GAS_COLUMN).getNumericCellValue();

			page = new GasMilageCalculatorPage();
			page.currentOdometer.clear();
			page.currentOdometer.sendKeys(String.valueOf(currentOr));
			page.previousOdometer.clear();
			page.previousOdometer.sendKeys(String.valueOf(previousOr));
			page.gas.clear();
			page.gas.sendKeys(String.valueOf(gas));
			page.calculate.click();
			String[] result = page.result.getText().split(" ");
			System.out.println(result[0]);
			String actualResult = result[0].replace(",", "");
			// actual result
			if (currentRow.getCell(5) == null) {
				currentRow.createCell(5);
			}
			currentRow.getCell(5).setCellValue(actualResult);

			// String result = page.result.getText();
			double calculationResult = ((currentOr - previousOr) / gas);
			DecimalFormat format = new DecimalFormat("##.00");

			System.out.println(format.format(calculationResult));
			if (currentRow.getCell(4) == null) {
				currentRow.createCell(4);
			}
			currentRow.getCell(4).setCellValue(format.format(calculationResult));

			if (actualResult.equals(format.format(calculationResult))) {
				System.out.println("Pass");
				if (currentRow.getCell(6) == null) {
					currentRow.createCell(6);
				}
				currentRow.getCell(6).setCellValue("Pass");

			} else {
				System.out.println("Fail");
				if (currentRow.getCell(6) == null) {
					currentRow.createCell(6);
				}
				currentRow.getCell(6).setCellValue("Fail");
			}

			//write current time
			if (currentRow.getCell(7) == null) {
				currentRow.createCell(7);
			}
			currentRow.getCell(7).getCellStyle().setWrapText(true);
			currentRow.getCell(7).setCellValue(LocalDateTime.now().toString());

		}

	}

}
