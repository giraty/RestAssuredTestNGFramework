package org.selenium.pom.factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.selenium.pom.constants.DriverType;

import java.time.Duration;
import java.util.Locale;

public class DriverManager {

    public WebDriver initializeDriver(String browser){
        WebDriver driver;


        // jika pagai testng.xml, hapus template jvm
        switch (DriverType.valueOf(browser)) {
            case CHROME -> {
                WebDriverManager.chromedriver().cachePath("Drivers").setup();
                driver = new ChromeDriver();
            }
            case FIREFOX -> {
                WebDriverManager.firefoxdriver().cachePath("Drivers").setup();
                driver = new FirefoxDriver();
            }
            default -> throw new IllegalStateException("Invalid browser name: " + browser);
        }
        //

        //

        driver.manage().window().maximize();
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        return driver;
    }
}
