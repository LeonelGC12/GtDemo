package rahulshettyacademy.tests;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class s18_StandAloneTest {

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
		driver.get("https://rahulshettyacademy.com/client");
		
		//INICIAR SESION
		driver.findElement(By.id("userEmail")).sendKeys("anshika@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Iamking@000");
		driver.findElement(By.id("login")).click();
		
		//SELECCIONAR PRODUCTO DESEADO
		List<WebElement> products =  driver.findElements(By.cssSelector(".mb-3"));
		WebElement prod = products.stream().filter(product->product.findElement(By.
				cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
			
		//ESPERAR AGREGADO A CARRITO E IR AL CARRITO
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("toast-container")));
		wait.until(ExpectedConditions.invisibilityOf(driver.
				findElement(By.cssSelector(".ng-animating"))));
		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
		
		//VERIFICAR QUE EN EL CARRITO ESTA EL PRODUCTO DESEADO
		List <WebElement> carts = driver.findElements(By.cssSelector(".cartSection h3"));
		Boolean match = carts.stream().anyMatch(cart->cart.getText()
				.equalsIgnoreCase(productName));
		System.out.println("Is the expected product? "+match);
		driver.findElement(By.cssSelector(".totalRow button")).click();
		
		//IR A PAGAR, SELECCIONAR PAIS
		Actions a = new  Actions(driver);
		a.sendKeys(driver.findElement(By.cssSelector
				("[placeholder='Select Country']")), "Mexico").build().perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
		Thread.sleep(1000);
		driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[1]")).click();
		
		//HACER CLICK EN CONFIRMAR ORDEN
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating")));
		WebElement placeOrderButton = wait.until(ExpectedConditions.elementToBeClickable(
			    By.cssSelector(".btnn.action__submit.ng-star-inserted")));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", placeOrderButton);
		Thread.sleep(1000);  // Espera un poco para asegurar el desplazamiento
		placeOrderButton.click();
		
		//LINEA DE CODIGO PARA FORZAR CLICK
		//((JavascriptExecutor) driver).executeScript("arguments[0].click();", placeOrderButton);
		//driver.findElement(By.xpath("//a[@class='btnn action__submit ng-star-inserted']")).click();
		
		//VERIFICAR QUE SE SOLICITO LA ORDEN
		String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
		System.out.println(confirmMessage);
		
		
		
		
		

	}

}
