package rahulshettyacademy.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rahulshettyacademy.AbstractComponents.AbstractComponent;

public class ProductCatalogue extends AbstractComponent{
	
	//CODIGO PARA PODER HACER LA SELECCION DEL PRODUCTO, VERIFICAR QUE SE AGREGO
	//AL CARRITO Y TAMBIEN IR AL CARRITO

	WebDriver driver;
	
	public ProductCatalogue(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}
	

//	List<WebElement> products =  driver.findElements(By.cssSelector(".mb-3"));
	@FindBy(css=".mb-3")
	List <WebElement> products;
	
//	wait.until(ExpectedConditions.invisibilityOf(driver.
//		findElement(By.cssSelector(".ng-animating"))));

	
	By items = By.cssSelector(".mb-3");
	By addProduct = By.cssSelector(".card-body button:last-of-type");
	By toastMessage = By.cssSelector("#toast-container");
	By spinner = By.cssSelector(".ngx-spinner-overlay");
	
	public List <WebElement> getProductList()
	{
		waitForElementAppear(items);
		return products;
	}
	
	public WebElement getProductName(String productName)
	{
		WebElement prod = getProductList().stream().filter(product->product.findElement(By.
				cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		return prod;
	}
	
	public void addToCart(String productName) {
	    // Encontrar el producto por nombre
	    WebElement add = getProductName(productName);
	    // Esperar que el spinner desaparezca (por si está presente)
	    waitForElementDisappear(By.cssSelector(".ngx-spinner-overlay"));
	    try {
	        // Intentar hacer clic de manera normal
	        add.findElement(addProduct).click();
	        System.out.println("✅ Clic normal en el botón 'Agregar al carrito' exitoso.");
	    } catch (ElementClickInterceptedException e) {
	        // Si el clic normal falla, forzar el clic con JavaScript
	        System.out.println("⚠️ Clic interceptado, intentando con JavaScript.");
	        forceClick(add.findElement(addProduct));
	    }
	    // Esperar a que aparezca el mensaje de confirmación (toast)
	    waitForElementAppear(toastMessage);
	    // Volver a esperar que desaparezca cualquier spinner
	    waitForElementDisappear(By.cssSelector(".ngx-spinner-overlay"));
	}
	
	public void navigateToCart() {
		goToCart(); // Usa el método del AbstractComponent
	    
	}
	
	public void forceClick(WebElement element) {
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("arguments[0].click();", element);
	}


}
