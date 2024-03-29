package mrrobot;

import org.openqa.selenium.WebDriver;

public class Utils {

    protected WebDriver driver;

    public Utils(WebDriver driver) {
        this.driver = driver;
    }

    public Utils() {}

    public String printUrlUtils(){
        return driver.getCurrentUrl();
    }
}
