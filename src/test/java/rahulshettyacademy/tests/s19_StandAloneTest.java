package rahulshettyacademy.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.CheckoutPage;
import rahulshettyacademy.pageobjects.ConfirmationPage;
import rahulshettyacademy.pageobjects.LandingPage;
import rahulshettyacademy.pageobjects.ProductCatalogue;

import java.time.Duration;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class s19_StandAloneTest {

	public static void main(String[] args) throws InterruptedException  {
		// TODO Auto-generated method stub
		
		//CODIGO PARA INICIAR SESION, SELECCIONAR UN PRODUCTO, IR AL CARRITO, PROCESO DE PAGO
		//Y PRESIONAR BOTON PARA PAGAR
		
		//PRODUCTO QUE DESEO
		String productName = "IPHONE 13 PRO";
		
		//ACCEDER A LA PAGINA
		WebDriverManager.edgedriver().setup();
		WebDriver driver = new EdgeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
		
		//ENVIAR "driver" a class LandingPage
		LandingPage landingPage = new LandingPage(driver);
		
		//IR AL URL
		landingPage.goTo();
		
		//INICIAR SESION
		landingPage.loginApp("anshika@gmail.com","Iamking@000");
		
		//SELECCIONAR PRODUCTO DESEADO Y ESPERAR SELECCION EN CARRITO
		ProductCatalogue prodCat = new ProductCatalogue(driver);
		List <WebElement> products = prodCat.getProductList();
		prodCat.addToCart(productName);
			
		//IR AL CARRITO
		prodCat.navigateToCart();
		
//		//VERIFICAR QUE EN EL CARRITO ESTA EL PRODUCTO DESEADO
		CartPage cartPage = new CartPage(driver);
		Boolean match = cartPage.VerifyProductDisplay(productName);
		System.out.println("Is the expected product? "+match);
	
//		//IR A PAGAR, SELECCIONAR PAIS
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("India");
		
//		//HACER CLICK EN CONFIRMAR ORDEN
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
		driver.quit();
		System.out.println("YOU DID IT");
	

	}

}
