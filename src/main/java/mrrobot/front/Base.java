package mrrobot.front;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class Base {

    private final String targetUrl;
    protected DriverManager driverManager;
    private final String tcName;

    public Base(String tcName, String targetUrl) {
        this.tcName = tcName;
        this.targetUrl = targetUrl;
    }

    @BeforeMethod(alwaysRun = true)
    public void init() {

        System.out.println("BASE init tc = " + tcName);

        driverManager = new DriverManager();
        driverManager
                .initDriver()
                .get(targetUrl)
                .minimize()
                .initUtils();

    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driverManager.releaseDriver();
    }
}
