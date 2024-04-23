package mrrobot.autodriver.core;

import lombok.Data;
import lombok.SneakyThrows;
import mrrobot.autodriver.enums.Browser;
import mrrobot.autodriver.enums.Version;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class DriverDownloader {

    private static final String BASE_URL_CHROME_API = "";
    private Browser browser;
    private Version version;
    private String localPath;

    public static DriverDownloader getInstance() {
        return new DriverDownloader();
    }

    public static void main(String[] args) {
        getChromeVersion();
    }

    @SneakyThrows
    public static String getChromeVersion() {
        // Comando para obtener la versión del navegador Chrome en Windows
        Process process = Runtime.getRuntime().exec("reg query \"HKEY_CURRENT_USER\\Software\\Google\\Chrome\\BLBeacon\" /v version");

        String outputStr = getString(process);
        Pattern pattern = Pattern.compile("version\\s+REG_SZ\\s+(\\S+)");
        Matcher matcher = pattern.matcher(outputStr);
        if (matcher.find()) {
            return matcher.group(1);
        }

        return null;
    }

    private static String getString(Process process) throws IOException {
        InputStream inputStream = process.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line;
        StringBuilder output = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            output.append(line);
        }

        // Cerrar los flujos
        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();

        // Extraer la versión del navegador Chrome
        return output.toString();
    }

    public DriverDownloader setBrowser(Browser browser) {
        this.browser = browser;
        return this;
    }

    public DriverDownloader setVersion(Version version) {
        this.version = version;
        return this;
    }

    public DriverDownloader setLocalPath(String path) {
        this.localPath = path;
        return this;
    }

    public void downloadDriver() {

    }
}
