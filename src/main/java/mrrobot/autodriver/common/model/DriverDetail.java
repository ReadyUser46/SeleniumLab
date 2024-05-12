package mrrobot.autodriver.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mrrobot.autodriver.common.enums.Browser;
import mrrobot.autodriver.common.enums.Os;
import mrrobot.autodriver.common.enums.Version;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverDetail {

    private Browser browser;
    private Version version;
    private String customVersion;
    private String outputDir;
    private Os os;
    private boolean x86;

    public String getArch() {
        return !x86 ? "64" : "32";
    }

    public String getOsName() {
        return os == null ? Os.WIN.name().toLowerCase() : os.name().toLowerCase();
    }

}
