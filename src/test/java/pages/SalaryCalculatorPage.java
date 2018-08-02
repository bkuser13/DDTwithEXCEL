package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.Driver;

public class SalaryCalculatorPage {
	
	WebDriver driver;
	public SalaryCalculatorPage() {
		driver = Driver.getDriver();
		PageFactory.initElements(driver, this);
	}

	@FindBy(linkText="Financial")
	public WebElement financialLink;
	
	
	
	
}
