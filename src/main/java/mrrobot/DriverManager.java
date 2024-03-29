package mrrobot;

import lombok.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

@Data
public class DriverManager {

    private WebDriver driver;
    private Utils utils;


    public DriverManager initDriver() {

        System.setProperty("webdriver.chrome.driver", "c:/automation/webdrivers/chromedriver.exe");
        driver = new ChromeDriver();
        return this;
    }

    public DriverManager get(String url) {
        driver.get(url);
        return this;
    }

    public DriverManager maximize() {
        driver.manage().window().maximize();
        return this;
    }

    public DriverManager initUtils() {
        setUtils(new Utils(driver));
        return this;
    }
}
