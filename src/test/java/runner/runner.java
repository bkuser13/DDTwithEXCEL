package runner;

import org.testng.annotations.Test;
import pages.SalaryCalculatorPage;
import utilities.AvenstackReport;
import utilities.BrowserUtils;
import utilities.ConfigReader;
import utilities.Driver;
import org.testng.annotations.BeforeClass;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;

public class runner extends AvenstackReport {
	WebDriver driver = Driver.getDriver();
	AvenstackReport extendReport;
	SalaryCalculatorPage salaryPage;

	@BeforeClass
	public void beforeClass() {
		driver.manage().window().fullscreen();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(ConfigReader.getValue("url"));
	}

	@AfterClass
	public void afterClass() {
		Driver.closeDriver();
	}

	@Test
	public void f() {
		salaryPage = new SalaryCalculatorPage();
		extentLogger = report.createTest("Reading From Excel Report");
		System.out.println(driver.getTitle());
		extentLogger.assignAuthor("Bolot Kadyraliev");
		extentLogger.info("Reading From Excel Test case");

		

	}

}
