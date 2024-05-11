package mrrobot.front;

import lombok.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

@Data
public class DriverManager {

    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private Utils utils;

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public static void setDriver(WebDriver driver) {
        driverThreadLocal.set(driver);
    }


    public DriverManager initDriver() {

        System.setProperty("webdriver.chrome.driver", "c:/automation/webdrivers/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        setDriver(new ChromeDriver());

//        Driver.setDriverInstance(driver);
        return this;
    }

    public DriverManager get(String url) {
        getDriver().get(url);
        return this;
    }

    public DriverManager maximize() {
        getDriver().manage().window().maximize();
        return this;
    }

    public DriverManager minimize() {
        getDriver().manage().window().minimize();
        return this;
    }

    public void initUtils() {
        setUtils(new Utils());
    }

    public void releaseDriver() {
        getDriver().close();
        getDriver().quit();
    }
}
