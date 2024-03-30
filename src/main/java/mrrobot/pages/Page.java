package mrrobot.pages;

import mrrobot.core.DriverManager;
import mrrobot.core.Utils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Page {

    @FindBy(id = "asdf")
    WebElement we;

    public Page() {
        PageFactory.initElements(DriverManager.getDriver(), this);
    }

    public Page action1() {
        we.click();
        return this;
    }

    public String printUrlPage() {
        return Utils.printUrlUtils();
    }
}
