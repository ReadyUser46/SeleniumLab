package mrrobot.front;

import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;

public class Utils {

    protected WebDriver driver;

/*    public Utils(WebDriver driver) {
        this.driver = driver;
    }*/

    public Utils() {
        driver = DriverManager.getDriver();
    }

    public String printUrlUtils(){
        return driver.getCurrentUrl();
    }

    @SneakyThrows
    public static void await(int s) {
        Thread.sleep(1000L * s);
    }
}
