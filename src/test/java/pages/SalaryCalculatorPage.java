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
	
	@FindBy(id="camount")
	public WebElement salaryAmount;

	@FindBy(id="chours")
	public WebElement hoursPerWeekBox;
	
	@FindBy(id="cdays")
	public WebElement daysPerWeekBox;
	
	@FindBy(xpath="//input[@type='image']")
	public WebElement calculateBtn;
	
	@FindBy(xpath="(//tbody)[3]/tr[3]/td[3]")
	public WebElement dailySalaryResult;
	
	@FindBy(xpath="(//tbody)[3]/tr[4]/td[3]")
	public WebElement weeklySalaryResult;
	
	@FindBy(xpath="(//tbody)[3]/tr[7]/td[3]")
	public WebElement monthlySalaryResult;
	
	@FindBy(xpath="(//tbody)[3]/tr[9]/td[3]")
	public WebElement anualSalaryResult;
	
	
	
}
