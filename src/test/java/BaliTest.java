import mrrobot.front.Base;
import mrrobot.front.DriverManager;
import mrrobot.front.Page;
import org.testng.annotations.Test;

public class BaliTest extends Base {

    private static final String TCNAME = "tc2";

    public BaliTest() {
        super(TCNAME, "https://chat.openai.com");
        System.out.println("constructor tc2");
    }

    @Test(priority = 2)
    public void test2() {
        Page page = new Page();

        System.out.printf("[TC2] Driver from drivermanager: %s%n", DriverManager.getDriver().hashCode());
        // System.out.printf("[TC2] Driver from instace: %s%n", Driver.getInstance().hashCode());
        System.out.printf("[TC2] Driver from utils: %s%n", driverManager.getUtils().driver.hashCode());
        System.out.printf("[TC2] Driver from page: %s%n", page.driver.hashCode());

        System.out.println("------- URL ---------");
        System.out.printf("[TC2] Driver from instace: %s%n", DriverManager.getDriver().getCurrentUrl());


    }

}
