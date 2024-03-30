package tests;

import mrrobot.base.Base;
import mrrobot.core.DriverManager;
import mrrobot.core.Utils;
import org.testng.annotations.Test;

public class AustraliaTest extends Base {

    private static final String TCNAME = "tc1";

    public AustraliaTest() {
        super(TCNAME, "https://stackoverflow.com");
        System.out.println("constructor tc1");
    }

    @Test(priority = 1)
    public void test1() {

        System.out.printf("[TC1] Driver from drivermanager: %s%n", DriverManager.getDriver().hashCode());
        //System.out.printf("[TC1] Driver from instace: %s%n", Driver.getInstance().hashCode());
        System.out.printf("[TC1] Driver from utils: %s%n", Utils.getWebdriver().hashCode());

        System.out.println("------- URL ---------");
        System.out.printf("[TC1] Driver from instace: %s%n", DriverManager.getDriver().getCurrentUrl());

        getPage()
                .action1();
    }


}
