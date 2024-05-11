package mrrobot.autodriver.core;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import mrrobot.autodriver.apiclient.core.RestAssuredLite;
import mrrobot.autodriver.apiclient.models.ResponseModel;
import mrrobot.autodriver.common.enums.Browser;
import mrrobot.autodriver.common.enums.Version;
import mrrobot.autodriver.common.model.DriverDetail;

import java.io.*;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
public class DriverDownloader {

    private DriverDetail driverDetail;
    private Browser browser;
    private String version;

    public static DriverDownloader getInstance(DriverDetail driverDetail) {
        return new DriverDownloader(driverDetail);
    }

    public static void main(String[] args) {
        // URL del archivo a descargar
        String url = "https://storage.googleapis.com/chrome-for-testing-public/124.0.6367.91/win64/chromedriver_win64.zip";

        // Realizar solicitud HTTP GET usando RestAssured
        Response response = RestAssured.get(url);

        // Verificar si la solicitud fue exitosa (código de estado 200)
        if (response.getStatusCode() == 200) {
            try {
                // Abrir conexión al archivo remoto
                URL fileUrl = new URL(url);
                InputStream inputStream = fileUrl.openStream();

                // Crear un flujo de salida para escribir el archivo
                OutputStream outputStream = new FileOutputStream("chromedriver_win32.zip");

                // Leer los datos del archivo remoto y escribirlos en el archivo local
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                // Cerrar flujos
                inputStream.close();
                outputStream.close();

                System.out.println("Archivo descargado correctamente.");
            } catch (IOException e) {
                System.out.println("Error al descargar el archivo: " + e.getMessage());
            }
        } else {
            System.out.println("No se pudo descargar el archivo. Código de estado: " + response.getStatusCode());
        }
    }

    private void setBrowser() {
        browser = driverDetail.getBrowser();
    }

    private void setVersion() {

        if (driverDetail.getVersion().equals(Version.CUSTOM)) {
            String customV = driverDetail.getCustomVersion();
            if (customV != null) version = customV;
            else throw new IllegalArgumentException("Custom version not specified in the builder");
        }

        if (driverDetail.getVersion().equals(Version.INSTALLED)) {
            switch (browser) {
                case CHROME -> getLocalChromeVersion();
                case EDGE -> getLocalEdgeVersion();
                case FIREFOX -> getLocalChromeVersion(); //todo dev;
            }
        }
    }

    public DriverDownloader downloadChromeDriver() {

        setBrowser();
        setVersion();


        String file = "/win64/chromedriver_win64.zip";

        String target = browser.getUrl() + version + file;

        try {
            // Abrir conexión al archivo remoto
            URL fileUrl = new URL(target);
            InputStream inputStream = fileUrl.openStream();

            // Crear un flujo de salida para escribir el archivo
            OutputStream outputStream = new FileOutputStream("chromedriver_win4.zip");

            // Leer los datos del archivo remoto y escribirlos en el archivo local
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            // Cerrar flujos
            inputStream.close();
            outputStream.close();

            System.out.println("Archivo descargado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al descargar el archivo: " + e.getMessage());
        }


    }

    @SneakyThrows
    public String getLocalChromeVersion() {
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

    @SneakyThrows
    public String getLocalEdgeVersion() {
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

    public DriverDownloader setLocalPath(String path) {
        this.localPath = path;
        return this;
    }

    public ResponseModel downloadDriver() {

        String vBrowser = "";
        if (version.equals(Version.INSTALLED)) {
            vBrowser = getChromeVersion();
        }

        return RestAssuredLite.getInstance(true)
                .setContentType(ContentType.BINARY)
                .setBaseUri("https://storage.googleapis.com/chrome-for-testing-public/")
                .sendRequest(Method.GET, "{version}/win64/chromedriver-win64.zip", vBrowser)
                .getResponseModel();
    }
}
