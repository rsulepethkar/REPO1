package StepDef;

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
//import org.testng.Assert;

import Utils.DriverFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;

public class AddToCart {


    private final WebDriver driver = DriverFactory.getDriver();
    private final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));


    @Given("I open the ebay homepage")
    public void iOpenTheEbayHomepage() {
        driver.get("https://www.ebay.com/");
        System.out.println("Title" + driver.getTitle());
    }

    @When("I search for {string}")
    public void iSearchFor(String Product) {
        WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@title='Search']")));
        searchBox.clear();
        searchBox.sendKeys(Product);
        driver.findElement(By.xpath("//span[text()='Search']")).click();
    }

    @And("I click on the first book and add to cart")
    public void iClickOnTheFirstBookInTheSearchResults() throws InterruptedException {
        String originalWindow = driver.getWindowHandle();
        Set<String> windowhandles = driver.getWindowHandles();


        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[@class='su-styled-text primary default'])[1]")));
        link.click();


        for (String handle : windowhandles) {
            if (!handle.equals(originalWindow)) {
                driver.switchTo().window(handle);

                break;
            }
        }
        Thread.sleep(3000);
        driver.findElement(By.xpath("//span[text()='Add to cart']")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//span[text()='See in cart']")).click();

      driver.switchTo().window(originalWindow);
      driver.navigate().refresh();

    }


    @Then("the cart should show at least one item")
    public void theCartShouldShowAtLeastItem() {

        WebElement cartCountLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[@aria-label='Your shopping cart contains 1 items'])[1]")));
        if (cartCountLink.isDisplayed()){
            System.out.println("Test completed successfully: Item added to cart and cart count verified.");
        }else
        {
            System.out.println("Test failed successfully: Item added to cart and cart count mismatch.");
        }




    }
}
