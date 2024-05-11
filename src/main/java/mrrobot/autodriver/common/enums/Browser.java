package mrrobot.autodriver.common.enums;

import lombok.Getter;

@Getter
public enum Browser {

    EDGE("https://storage.googleapis.com/chrome-for-testing-public/"),
    CHROME("https://storage.googleapis.com/chrome-for-testing-public/"),
    FIREFOX("https://storage.googleapis.com/chrome-for-testing-public/");

    private final String url;

    Browser(String url) {
        this.url = url;
    }

}
