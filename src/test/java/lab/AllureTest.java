package lab;

import mrrobot.Base;
import mrrobot.Page;
import org.testng.annotations.Test;

public class AllureTest extends Base {


    public AllureTest() {
        super("allure", "https://stackoverflow.com");
    }

    @Test
    public void alluri() {

        Page page = new Page();
        page.step1();
        page.step2();
        page.step3();


    }
}
