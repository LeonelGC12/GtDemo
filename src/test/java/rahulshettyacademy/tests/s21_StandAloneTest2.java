package rahulshettyacademy.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.CheckoutPage;
import rahulshettyacademy.pageobjects.ConfirmationPage;
import rahulshettyacademy.pageobjects.LandingPage;
import rahulshettyacademy.pageobjects.OrderPage;
import rahulshettyacademy.pageobjects.ProductCatalogue;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class s21_StandAloneTest2 extends BaseTest{
	
		//CODIGO PARA INICIAR SESION, SELECCIONAR UN PRODUCTO, IR AL CARRITO, PROCESO DE PAGO
		//Y PRESIONAR BOTON PARA PAGAR
	
	//PRODUCTO QUE DESEO
	String productName = "IPHONE 13 PRO";
	
	@Test(dataProvider = "getData", groups = "Purchase")
	public void submitOrder(HashMap <String,String> input)throws IOException, InterruptedException
	{
		
		//ACCEDER A LA PAGINA
			//INICIAR SESION
		ProductCatalogue productCatalogue = landingPage.loginApp(input.get("email")
				,input.get("password"));
		
		//SELECCIONAR PRODUCTO DESEADO Y ESPERAR SELECCION EN CARRITO
//		ProductCatalogue prodCat = new ProductCatalogue(driver);
		List <WebElement> products = productCatalogue.getProductList();
		productCatalogue.addToCart(input.get("productName"));
			
		//IR AL CARRITO
		productCatalogue.navigateToCart();
		
//		//VERIFICAR QUE EN EL CARRITO ESTA EL PRODUCTO DESEADO
		CartPage cartPage = new CartPage(driver);
		Boolean match = cartPage.VerifyProductDisplay(input.get("productName"));
		System.out.println("Is the expected product? "+match);
	
//		//IR A PAGAR, SELECCIONAR PAIS
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("India");
		
//		//HACER CLICK EN CONFIRMAR ORDEN
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating")));
		WebElement placeOrderButton = wait.until(ExpectedConditions.elementToBeClickable(
			    By.cssSelector(".btnn.action__submit.ng-star-inserted")));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", placeOrderButton);
		Thread.sleep(1000);  // Espera un poco para asegurar el desplazamiento
		placeOrderButton.click();
		
		//LINEA DE CODIGO PARA FORZAR CLICK
//		//((JavascriptExecutor) driver).executeScript("arguments[0].click();", placeOrderButton);
//		//driver.findElement(By.xpath("//a[@class='btnn action__submit ng-star-inserted']")).click();
	
//		//VERIFICAR QUE SE SOLICITO LA ORDEN
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		System.out.println("YOU DID IT");
		
	}
	
	@Test(dependsOnMethods = {"submitOrder"})
	public void OrderHistoryTest()
	{
		ProductCatalogue productCatalogue = landingPage
				.loginApp("anshika@gmail.com","Iamking@000");
		OrderPage orderPage = productCatalogue.goToOrderPage();
		Assert.assertTrue(orderPage.VerifyOrderDisplay(productName));
		System.out.println("YOUR PRODUCT IS IN THE ORDER HISTORY");
	}
	

	
	@DataProvider
	public Object [][] getData() throws IOException
	{
		List <HashMap<String,String>> data = getJsonDataToMap(System.getProperty("user.dir")+
				"\\src\\test\\java\\rahulshettyacademy\\data\\PurchaseOrder.json");
		return new Object [][] {{data.get(0)},{data.get(1)}};	
	}
	
	

	}


