package cucumber;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(features = "src/test/java/cucumber" 
	, glue = "rahulshettyacademy.stepDefinitions" , monochrome = true 
	, tags = "@ErrorValidation" , plugin = {"html:target/cucumber.html"})
public class s25_TestNGTestRunner extends AbstractTestNGCucumberTests{
	
	

}

