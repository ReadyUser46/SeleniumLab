package mrrobot.autodriver.common.enums;

import lombok.Getter;

@Getter
public enum Browser {

    EDGE("https://msedgedriver.azureedge.net", "edgedriver"),
    CHROME("https://storage.googleapis.com/chrome-for-testing-public", "chromedriver"),
    FIREFOX("https://storage.googleapis.com/chrome-for-testing-public", "geekodriver");

    private final String url;
    private final String driverName;

    Browser(String url, String driverName) {
        this.url = url;
        this.driverName = driverName;
    }
}
