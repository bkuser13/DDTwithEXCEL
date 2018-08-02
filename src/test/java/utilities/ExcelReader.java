package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.SalaryCalculatorPage;

public class ExcelReader {

	WebDriver driver;
	FileInputStream inStream;
	FileOutputStream outStream;
	Workbook workBook;
	Sheet worksheet;
	String filePath = "./MOCK_DATA.xlsx";
	SalaryCalculatorPage sPage;
	@BeforeClass
	public void setUp() throws Exception {
	
		driver = Driver.getDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().fullscreen();
		
		
		inStream = new FileInputStream(filePath);
		workBook = WorkbookFactory.create(inStream);
		worksheet = workBook.getSheetAt(0);
		driver.get(ConfigReader.getValue("url"));

	}

	@AfterClass(enabled = true)
	public void tearDown() throws IOException {
		outStream = new FileOutputStream(filePath);
		workBook.write(outStream);
		outStream.close();
		workBook.close();
		inStream.close();
		driver.close();
	}

	@Test
	public void salaryCalculation() {
		
		sPage = new SalaryCalculatorPage();
		
		for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {

			Row currentRow = worksheet.getRow(i);
			int salaryPerHour = (int) currentRow.getCell(2).getNumericCellValue();
			int hoursWorked = (int) currentRow.getCell(3).getNumericCellValue();
			int daysWorked = (int) currentRow.getCell(4).getNumericCellValue();

			sPage.salaryAmount.clear();
			sPage.salaryAmount.sendKeys(String.valueOf(salaryPerHour));
			sPage.hoursPerWeekBox.clear();
			sPage.hoursPerWeekBox.sendKeys(String.valueOf(hoursWorked));
			sPage.daysPerWeekBox.clear();
			sPage.daysPerWeekBox.sendKeys(String.valueOf(daysWorked));
			sPage.calculateBtn.click();
			
			String daily = sPage.dailySalaryResult.getText().replace("$","").replace(",", "");
			if(currentRow.getCell(5) == null) {
				currentRow.createCell(5);
			}
			currentRow.getCell(5).setCellValue(daily);
			
			String weekly = sPage.weeklySalaryResult.getText().replace("$","").replace(",", "");
			if(currentRow.getCell(6) == null) {
				currentRow.createCell(6);
			}
			currentRow.getCell(6).setCellValue(weekly);
			
		
			String monthly = sPage.monthlySalaryResult.getText().replace("$","").replace(",", "");
			if(currentRow.getCell(7) == null) {
				currentRow.createCell(7);
			}
			currentRow.getCell(7).setCellValue(monthly);
			
			double anual = Double.parseDouble(sPage.anualSalaryResult.getText().replace("$", "").replace(",", ""));
			if(currentRow.getCell(8) == null) {
				currentRow.createCell(8);
			}
			currentRow.getCell(8).setCellValue(anual);
			
			
			
			
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
