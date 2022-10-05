package test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderTests {
    WebDriverWait wait;
    WebDriver driver;

    @BeforeAll
    static void setup(){
        System.setProperty("webdriver.chrome.driver","/Users/arnas.verpecinskas/IdeaProjects/testcourse/drivers/chromedriver");
    }

    @BeforeEach
    void eachSetup(){
        this.driver = new ChromeDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @AfterEach
    void eachEnd(){
        this.driver.close();
    }

    @ParameterizedTest
    @ValueSource(strings = {"data1.txt", "data2.txt"})
    void orderItems(String fileName) throws IOException, InterruptedException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("src/test/resources/" + fileName));
        driver.get("https://demowebshop.tricentis.com/");
        login(driver);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@href='/digital-downloads']")));
        driver.findElement(By.xpath("//a[@href='/digital-downloads']")).click();
        String line;
        while((line = bufferedReader.readLine()) != null && line.length() != 0){
            driver.findElement(By.xpath("//div[h2/a[text()='" + line + "']]/descendant::input[@value='Add to cart']")).click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='ajax-loading-block-window' and @style='display: none;']")));
        }
        driver.findElement(By.xpath("//a[@href='/cart']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='termsofservice']"))).click();
        driver.findElement(By.xpath("//button[@id='checkout']")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//select[@name='billing_address_id']"))).sendKeys("New Address");
        driver.findElement(By.xpath("//select[@name='BillingNewAddress.CountryId']")).sendKeys("United States");
        driver.findElement(By.xpath("//input[@name='BillingNewAddress.City']")).sendKeys("Chicago");
        driver.findElement(By.xpath("//input[@name='BillingNewAddress.Address1']")).sendKeys("Test 1");
        driver.findElement(By.xpath("//input[@name='BillingNewAddress.ZipPostalCode']")).sendKeys("94939");
        driver.findElement(By.xpath("//input[@name='BillingNewAddress.PhoneNumber']")).sendKeys("+1295493201");
        
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@class='button-1 new-address-next-step-button']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@onclick='PaymentMethod.save()']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@onclick='PaymentInfo.save()']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@onclick='ConfirmOrder.save()']"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='section order-completed']")));
    }

    void login(WebDriver driver){
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/login']")));
        driver.findElement(By.xpath("//a[@href='/login']")).click();
        System.out.println("clicked");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@class='email']")));
        driver.findElement(By.xpath("//input[@class='email']")).sendKeys("test01039r92@test.com");
        driver.findElement(By.xpath("//input[@class='password']")).sendKeys("test123");
        driver.findElement(By.xpath("//input[@class='button-1 login-button']")).click();
    }
}
