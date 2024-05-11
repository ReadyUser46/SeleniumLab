import mrrobot.front.Base;
import mrrobot.front.DriverManager;
import mrrobot.front.Page;
import org.testng.annotations.Test;

public class AustraliaTest extends Base {

    private static final String TCNAME = "tc1";

    public AustraliaTest() {
        super(TCNAME, "https://stackoverflow.com");
        System.out.println("constructor tc1");
    }

    @Test(priority = 1)
    public void test1() {
        Page page = new Page();


        System.out.printf("[TC1] Driver from drivermanager: %s%n", DriverManager.getDriver().hashCode());
        //System.out.printf("[TC1] Driver from instace: %s%n", Driver.getInstance().hashCode());
        System.out.printf("[TC1] Driver from utils: %s%n", driverManager.getUtils().driver.hashCode());
        System.out.printf("[TC1] Driver from page: %s%n", page.driver.hashCode());

        System.out.println("------- URL ---------");
        System.out.printf("[TC2] Driver from instace: %s%n", DriverManager.getDriver().getCurrentUrl());

    }

}
