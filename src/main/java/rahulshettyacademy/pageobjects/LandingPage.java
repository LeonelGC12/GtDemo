package rahulshettyacademy.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rahulshettyacademy.AbstractComponents.AbstractComponent;

public class LandingPage extends AbstractComponent{
	
	//INICIAR SESION CON NUEVO METODO REEMPLAZANDO PASOS DE CODIGO PRINCIPAL

	WebDriver driver;
	
	public LandingPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}
	

//	WebElement userEmails = driver.findElement(By.id("userEmail"));
	@FindBy(id="userEmail")
	WebElement user;
//	driver.findElement(By.id("userPassword")).sendKeys("Iamking@000");
	@FindBy(id="userPassword")
	WebElement password;
//	driver.findElement(By.id("login")).click();
	@FindBy(id="login")
	WebElement submit;
	
	@FindBy(css="[class*='flyInOut']")
	WebElement errorMessage;
	
	public void goTo()
	{
		driver.get("https://rahulshettyacademy.com/client");
	}
	
	public ProductCatalogue loginApp(String email, String psw)
	{
		user.sendKeys(email);
		password.sendKeys(psw);
		submit.click();
		return new ProductCatalogue(driver);
	}
	
	public String getErrorMessage()
	{
		waitForErrorToAppear(errorMessage);
		return errorMessage.getText();
	}

}
