package mrrobot.autodriver;

import mrrobot.autodriver.common.enums.Browser;
import mrrobot.autodriver.common.enums.Version;
import mrrobot.autodriver.common.model.DriverDetail;
import mrrobot.autodriver.core.DriverDownloader;

public class Launcher {

    public static void main(String[] args) {

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
}
