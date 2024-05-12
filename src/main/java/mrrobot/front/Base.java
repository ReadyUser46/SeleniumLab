package mrrobot.front;

import mrrobot.autodriver.common.enums.Browser;
import mrrobot.autodriver.common.enums.Version;
import mrrobot.autodriver.common.model.DriverDetail;
import mrrobot.autodriver.core.DriverDownloader;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class Base {

    private final String targetUrl;
    protected DriverManager driverManager;
    private final String tcName;

    public Base(String tcName, String targetUrl) {
        this.tcName = tcName;
        this.targetUrl = targetUrl;
    }

    @BeforeSuite
    public void onBeforeSuite() {
        DriverDetail driverDetail1 = DriverDetail.builder()
                .browser(Browser.EDGE)
                .version(Version.INSTALLED)
                .outputDir("c:/automation/webdrivers/")
                .build();

        DriverDownloader.getInstance(driverDetail1)
                .downloadWebDriver()
                .extractDriverExe()
                .cleanUpDir();


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
