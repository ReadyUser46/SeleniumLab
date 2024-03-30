package mrrobot.tests;

import mrrobot.base.Base;
import mrrobot.core.DriverManager;
import mrrobot.core.Utils;
import mrrobot.pages.Page;
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
        System.out.printf("[TC2] Driver from utils: %s%n", Utils.getWebdriver().hashCode());

        System.out.println("------- URL ---------");
        System.out.printf("[TC2] Driver from instace: %s%n", DriverManager.getDriver().getCurrentUrl());


    }

}
