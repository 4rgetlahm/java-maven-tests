package net.Arnas.testcourse;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args){
        System.setProperty("webdriver.chrome.driver","/Users/arnas.verpecinskas/IdeaProjects/testcourse/drivers/chromedriver");
        WebDriver driver = new ChromeDriver();

        try {
            driver.get("https://google.com");

            driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[3]/span/div/div/div/div[3]/div[1]/button[1]/div")).click();
            TimeUnit.SECONDS.sleep(1);
            driver.findElement(By.xpath("/html/body/div[1]/div[3]/form/div[1]/div[1]/div[1]/div/div[2]/input")).sendKeys("recursion");
            driver.findElement(By.xpath("/html/body/div[1]/div[3]/form/div[1]/div[1]/div[3]/center/input[1]")).click();
            TimeUnit.SECONDS.sleep(1);

            while (true) {
                driver.findElement(By.xpath("/html/body/div[7]/div/div[11]/div[1]/div[1]/div[1]/p/a")).click();
                TimeUnit.SECONDS.sleep(1);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        driver.quit();
    }
}
