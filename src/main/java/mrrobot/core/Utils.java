package mrrobot.core;

import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;

public class Utils {

    private static final WebDriver driver = DriverManager.getDriver();

    private Utils() {
    }

    public static WebDriver getWebdriver() {
        return driver;
    }

    public static String printUrlUtils() {
        return driver.getCurrentUrl();
    }

    @SneakyThrows
    public static void await(int s) {
        Thread.sleep(1000L * s);
    }
}
