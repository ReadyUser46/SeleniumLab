package mrrobot.core;

import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;

public class Utils {

    private static final ThreadLocal<WebDriver> driverThreadLocal = DriverManager.getDriverThreadLocal();

    private Utils() {
    }

    public static WebDriver getWebdriver() {
        return driverThreadLocal.get();
    }

    public static String printUrlUtils() {
        return driverThreadLocal.get().getCurrentUrl();
    }

    @SneakyThrows
    public static void await(int s) {
        Thread.sleep(1000L * s);
    }
}
