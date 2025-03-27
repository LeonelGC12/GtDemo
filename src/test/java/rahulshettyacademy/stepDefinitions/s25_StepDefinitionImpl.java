package rahulshettyacademy.stepDefinitions;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.CheckoutPage;
import rahulshettyacademy.pageobjects.ConfirmationPage;
import rahulshettyacademy.pageobjects.LandingPage;
import rahulshettyacademy.pageobjects.ProductCatalogue;

public class s25_StepDefinitionImpl extends BaseTest{
	
	public LandingPage landingPage;
	public ProductCatalogue productCatalogue;
	public ConfirmationPage confirmationPage;
	
	@Given("I landed on Ecommerce Page")
	public void I_landed_on_Ecommerce_Page() throws IOException
	{
		landingPage = launchApplication();
	}
	
    @Given("^Logged in with username (.+) and password (.+)$")
    public void logged_in_with_username_and_password(String username, String password)
    {
    	productCatalogue = landingPage.loginApp(username,password);
    }
    
    @When("^I add product (.+) to Cart$")
    public void I_add_product_to_Cart(String productName) throws InterruptedException
    {
		List <WebElement> products = productCatalogue.getProductList();
		productCatalogue.addToCart(productName);
    }
    
    @When("^Checkout (.+) and submit the order$")
    public void checkout_and_submit_the_order(String productName) throws InterruptedException
    {
		CartPage cartPage = new CartPage(driver);
		productCatalogue.navigateToCart();
		Boolean match = cartPage.VerifyProductDisplay(productName);
		System.out.println("Is the expected product? "+match);
		//IR A PAGAR, SELECCIONAR PAIS
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("India");
		//HACER CLICK EN CONFIRMAR ORDEN
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating")));
		WebElement placeOrderButton = wait.until(ExpectedConditions.elementToBeClickable(
			    By.cssSelector(".btnn.action__submit.ng-star-inserted")));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", placeOrderButton);
		Thread.sleep(1000);  // Espera un poco para asegurar el desplazamiento
		placeOrderButton.click();
		//VERIFICAR QUE SE SOLICITO LA ORDEN
		confirmationPage = checkoutPage.submitOrder();
    }
    
    @Then("{string} message is displayed on ConfirmationPage")
    public void message_displayed_on_ConfirmationPage(String string)
    {
    	String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase(string));
		System.out.println("YOU DID IT");
		driver.close();
    }
    
    //ErrorValidation
    @Then("^\"([^\"]*)\" message is displayed$")
    public void something_message_is_displayed(String strArg1) throws Throwable
    {
		AssertJUnit.assertEquals("Incorrect email or password.",landingPage.getErrorMessage());
		System.out.println("Incorrect email or password.");
		driver.close();
    }

}

