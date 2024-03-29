package mrrobot;

import org.testng.annotations.Test;

public class TcTest extends Base {


    @Test
    public void test1() {
        Page page = new Page();

        System.out.printf("Driver from drivermanager: %s", driverManager.getDriver().getCurrentUrl());
        System.out.printf("Driver from utils: %s", driverManager.getUtils().printUrlUtils());
        System.out.printf("Driver from page: %s", page.printUrlPage());

        System.out.println("stop");
    }

}
