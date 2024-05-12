package mrrobot.autodriver.core;

import lombok.SneakyThrows;
import mrrobot.autodriver.common.enums.Browser;
import mrrobot.autodriver.common.enums.Version;
import mrrobot.autodriver.common.model.DriverDetail;

import java.io.*;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DriverDownloader {

    private final DriverDetail driverDetail;
    private String outputZip;
    private String targetUrl;

    private DriverDownloader(DriverDetail driverDetail) {
        this.driverDetail = driverDetail;
        setAttributes();
    }

    public static DriverDownloader getInstance(DriverDetail driverDetail) {
        return new DriverDownloader(driverDetail);
    }

    //---------------------------------------------------

    private void setAttributes() {

        Browser browserX = driverDetail.getBrowser();
        String versionX = getTargetVersion(browserX);
        driverDetail.setCustomVersion(versionX);
        String basePathX = browserX.getUrl();
        String arch = driverDetail.getArch();
        String os = driverDetail.getOsName();
        String fileName = String.format("%s_%s%s.zip", browserX.getDriverName(), os, arch); //todo to path
        outputZip = driverDetail.getOutputDir() + fileName;

        switch (browserX) {
            case CHROME:
                targetUrl = String.format("%s/%s/%s%s/%s", basePathX, versionX, os, arch, fileName);
                break;
            case EDGE:
                //https://msedgedriver.azureedge.net/100.0.1158.0/edgedriver_arm64.zip
                targetUrl = String.format("%s/%s/%s", basePathX, versionX, fileName);
                break;
            case FIREFOX:
                //todo dev
                break;
        }

    }

    private String getTargetVersion(Browser browser) {

        if (driverDetail.getVersion().equals(Version.CUSTOM)) {
            String customV = driverDetail.getCustomVersion();
            if (customV != null) return customV;
            else throw new IllegalArgumentException("Custom version not specified in the builder");
        }

        if (driverDetail.getVersion().equals(Version.INSTALLED)) {
            return switch (browser) {
                case CHROME -> getLocalChromeVersion();
                case EDGE -> getLocalEdgeVersion();
                case FIREFOX -> getLocalChromeVersion(); //todo dev;
            };
        }

        return "";
    }

    public DriverDownloader downloadWebDriver() {

        System.out.printf("[LOGGER] Downloading driver: browser = %s, version = %s%n", driverDetail.getBrowser(), driverDetail.getCustomVersion());

        File existingZip = new File(outputZip);
        if (existingZip.exists()) {

            System.out.println("[WARNING] zip file already exists: " + outputZip);
            System.out.println("[LOGGER] Deleting file and retrying...");

            if (!existingZip.delete()) System.out.println("[WARNING] zip file could not be removed");
        }

        try {
            URL fileUrl = new URL(targetUrl);
            InputStream inputStream = fileUrl.openStream();

            OutputStream outputStream = new FileOutputStream(outputZip);

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            inputStream.close();
            outputStream.close();

            System.out.println("[LOGGER] Webdriver susccessfully downaloed: " + outputZip);
        } catch (IOException e) {
            System.out.println("[FAILURE] Error downloading file: \n" + e.getMessage() + System.lineSeparator());
        }

        return this;
    }

    @SneakyThrows
    public DriverDownloader extractDriverExe() {

        System.out.println("[LOGGER] Extracting driver executable from .zip...");

        File outputFolder = new File(driverDetail.getOutputDir());

        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(outputZip));
        ZipEntry zipEntry = zis.getNextEntry();

        while (zipEntry != null) {

            if (zipEntry.getName().endsWith("driver.exe")) {
                File driverExe = createDriverExe(outputFolder, zipEntry);

                FileOutputStream fos = new FileOutputStream(driverExe);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                break;

            } else {
                zipEntry = zis.getNextEntry();
            }

        }

        zis.closeEntry();
        zis.close();

        return this;
    }

    public void cleanUpDir() {

        System.out.println("[LOGGER] Cleaning up output dir...");


        File f = new File(outputZip);
        if (!f.delete()) System.out.println("[WARNING] zip file could not be deleted: " + outputZip);
    }

    //---------------------------------------------------

    @SneakyThrows
    private File createDriverExe(File destinationDir, ZipEntry zipEntry) {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }

    @SneakyThrows
    private String getLocalChromeVersion() {
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

    public String getLocalEdgeVersion() {
        String version = "";
        try {
            Process process = Runtime.getRuntime().exec("reg query HKCU\\Software\\Microsoft\\Edge\\BLBeacon /v version");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("version")) {
                    String[] parts = line.split("\\s+");
                    version = parts[parts.length - 1];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return version;
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
}
