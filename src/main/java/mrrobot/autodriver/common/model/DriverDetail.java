package mrrobot.autodriver.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mrrobot.autodriver.common.enums.Browser;
import mrrobot.autodriver.common.enums.Version;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverDetail {

    private Browser browser;
    private Version version;
    private String outputPath;
    private String customVersion;
    private String arch = "x64";


}
