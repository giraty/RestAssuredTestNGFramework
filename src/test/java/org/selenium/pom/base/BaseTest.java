package org.selenium.pom.base;

import org.openqa.selenium.WebDriver;
import org.selenium.pom.factory.DriverManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {
    private ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    //private WebDriver driver;

    private void setDriver(WebDriver driver){
        this.driver.set(driver);
    }

    protected WebDriver getDriver(){
        return this.driver.get();
    }

    //@Parameters("browser")
    //lagi di comment krn sedang tidak pakai TestNG, direct run dari IDE
    @BeforeMethod
    public void startDriver(@Optional String browser){
        //browser = System.getProperty("browser", browser); // ini untuk pakai dengan JVM
        //driver = new DriverManager().initializeDriver(browser); hasil JUnit gagal
        if(browser == null){
            browser = "CHROME";
        }

        setDriver(new DriverManager().initializeDriver(browser));
        System.out.println("CURRENT THREAD: " + Thread.currentThread().getId() + ", " + "DRIVER= " + getDriver());
    }

    @AfterMethod

    public void quitDriver() throws InterruptedException {
        Thread.sleep(100);
        System.out.println("CURRENT THREAD: " + Thread.currentThread().getId() + ", " + "DRIVER= " + getDriver());
        getDriver().quit();
    }

}
