package mrrobot;

import org.testng.annotations.BeforeMethod;

public class Base {

    private static final String targetUrl = "https://chat.openai.com/";
    protected DriverManager driverManager;


    @BeforeMethod(alwaysRun = true)
    public void init() {


        driverManager = new DriverManager();
        driverManager
                .initDriver()
                .get(targetUrl)
                .maximize()
                .initUtils();

    }
}
