package org.selenium.pom.base;

import io.qameta.allure.Step;
import io.restassured.http.Cookies;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.selenium.pom.constants.DriverType;
import org.selenium.pom.factory.DriverManagerFactory;
import org.selenium.pom.factory.DriverManagerOriginal;
import org.selenium.pom.factory.abstractFactory.DriverManagerAbstract;
import org.selenium.pom.factory.abstractFactory.DriverManagerFactoryAbstract;
import org.selenium.pom.utils.CookieUtils;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class BaseTest {
    //private WebDriver driver;
    private ThreadLocal<DriverManagerAbstract> driverManager = new ThreadLocal<>();
    private ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private void setDriverManager(DriverManagerAbstract driverManager){
        this.driverManager.set(driverManager);
    }

    protected DriverManagerAbstract getDriverManager(){
        return this.driverManager.get();
    }

    private void setDriver(WebDriver driver){
        this.driver.set(driver);
    }

    protected WebDriver getDriver(){
        return this.driver.get();
    }
/*    private ThreadLocal<DriverManagerAbstract> driverManager = new ThreadLocal<>();


    private void setDriverManager(DriverManagerAbstract driverManager){
        this.driverManager.set(driverManager);
    }

    protected DriverManagerAbstract getDriverManager(){
        return this.driverManager.get();
    }*/

    @Parameters("browser")
    //lagi di comment krn sedang tidak pakai TestNG, direct run dari IDE
    @BeforeMethod
    public synchronized void startDriver(@Optional String browser){
        browser = System.getProperty("browser", browser); // ini untuk pakai dengan JVM
        //setDriver(DriverManagerFactory.getManager(DriverType.valueOf(browser)).createDriver());
        if(browser == null){
            browser = "CHROME";
        }
        setDriverManager(DriverManagerFactoryAbstract
                .getManager(DriverType.valueOf(browser)));


        setDriver(getDriverManager().getDriver());

        //setDriver(new DriverManagerOriginal().initializeDriver(browser));
        System.out.println("CURRENT THREAD: " + Thread.currentThread().getId() + ", " + "DRIVER= " + getDriver());
    }
    @Parameters("browser")
    @AfterMethod

    public synchronized void quitDriver(@Optional String browser, ITestResult result) throws InterruptedException, IOException {
        Thread.sleep(100);
        System.out.println("CURRENT THREAD: " + Thread.currentThread().getId() + ", " + "DRIVER= " + getDriver());
        //getDriver().quit();
        if(result.getStatus() == ITestResult.FAILURE){
            File destFile = new File("scr" + File.separator + browser + File.separator +
                    result.getTestClass().getRealClass().getSimpleName() + "_" +
                    result.getMethod().getMethodName() + ".png");
            //takeScreenshot(destFile); cara testng
            takeScreenshotUsingAShot(destFile);
        }
        getDriverManager().getDriver().quit();
    }
    public void injectCookiesToBrowser(Cookies cookies){
        List<Cookie> seleniumCookies = new CookieUtils().convertRestAssuredCookiesToSeleniumCookies(cookies);
        for(Cookie cookie: seleniumCookies){
            getDriver().manage().addCookie(cookie);

        }
    }
    private void takeScreenshot(File destFile) throws IOException {
        TakesScreenshot takesScreenshot = (TakesScreenshot) getDriver();
        File srcFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile, destFile);
    }

    private void takeScreenshotUsingAShot(File destFile){
        Screenshot screenshot = new AShot()
                .shootingStrategy(ShootingStrategies.viewportPasting(100))
                .takeScreenshot(getDriver());
        try{
            ImageIO.write(screenshot.getImage(), "PNG", destFile);
        }catch(IOException e){
            e.printStackTrace();
        }

    }

}
