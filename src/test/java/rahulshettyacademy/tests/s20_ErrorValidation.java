package rahulshettyacademy.tests;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.TestComponents.Retry;
import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.CheckoutPage;
import rahulshettyacademy.pageobjects.ConfirmationPage;
import rahulshettyacademy.pageobjects.ProductCatalogue;

public class s20_ErrorValidation extends BaseTest{
	
		//CODIGO PARA INICIAR SESION, SELECCIONAR UN PRODUCTO, IR AL CARRITO, PROCESO DE PAGO
		//Y PRESIONAR BOTON PARA PAGAR
	
	@Test(groups= {"ErrorHandling"},retryAnalyzer=Retry.class)
	public void LoginErrorValidation() throws IOException, InterruptedException
	{
		
		//PRODUCTO QUE DESEO
		String productName = "IPHONE 13 PRO";
		
		//ACCEDER A LA PAGINA
			//INICIAR SESION
		landingPage.loginApp("anshika@gmail.com","Iamking@00");
		AssertJUnit.assertEquals("Incorrect email or password.",landingPage.getErrorMessage());
		System.out.println("Incorrect email or password.");

	}
	
	@Test
	public void ProductErrorValidation() throws IOException, InterruptedException
	{
		//PRODUCTO QUE DESEO
				String productName = "IPHONE 13 PRO";
				
				//ACCEDER A LA PAGINA
					//INICIAR SESION
				ProductCatalogue productCatalogue = landingPage.loginApp("anshika@gmail.com","Iamking@000");
				
				//SELECCIONAR PRODUCTO DESEADO Y ESPERAR SELECCION EN CARRITO
//				ProductCatalogue prodCat = new ProductCatalogue(driver);
				List <WebElement> products = productCatalogue.getProductList();
				productCatalogue.addToCart(productName);
					
				//IR AL CARRITO
				productCatalogue.navigateToCart();
				
//				//VERIFICAR QUE EN EL CARRITO ESTA EL PRODUCTO DESEADO
				CartPage cartPage = new CartPage(driver);
				Boolean match = cartPage.VerifyProductDisplay("IPHONE 16 PRO");
				Assert.assertFalse(match);
				System.out.println("Is the expected product? "+match);
					
		
	}
	

	}


