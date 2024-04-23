package mrrobot.autodriver;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {


    public static void main(String[] args) {
        try {
            // Obtener el número de versión más reciente de ChromeDriver
            URL versionUrl = new URL("https://chromedriver.storage.googleapis.com/LATEST_RELEASE");
            HttpURLConnection connection = (HttpURLConnection) versionUrl.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                Scanner scanner = new Scanner(versionUrl.openStream());
                String versionNumber = scanner.useDelimiter("\\A").next();
                scanner.close();

                // Construir la URL de descarga
                String downloadUrl = "https://chromedriver.storage.googleapis.com/" + versionNumber + "/chromedriver_win32.zip";

                // Descargar el archivo zip
                URL zipUrl = new URL(downloadUrl);
                HttpURLConnection zipConnection = (HttpURLConnection) zipUrl.openConnection();
                zipConnection.setRequestMethod("GET");
                int zipResponseCode = zipConnection.getResponseCode();
                if (zipResponseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = zipUrl.openStream();
                    FileOutputStream fileOutputStream = new FileOutputStream("chromedriver.zip");
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, bytesRead);
                    }
                    fileOutputStream.close();
                    inputStream.close();

                    // Extraer el archivo zip
                    Path zipFilePath = Paths.get("chromedriver.zip");
                    Path extractPath = Paths.get("");
                    try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipFilePath))) {
                        ZipEntry entry;
                        while ((entry = zipInputStream.getNextEntry()) != null) {
                            Path entryPath = extractPath.resolve(entry.getName());
                            if (!entryPath.toFile().exists()) {
                                Files.createDirectories(entryPath.getParent());
                                Files.createFile(entryPath);
                                byte[] entryData = new byte[(int) entry.getSize()];
                                zipInputStream.read(entryData);
                                Files.write(entryPath, entryData);
                            }
                            zipInputStream.closeEntry();
                        }
                    }

                    // Eliminar el archivo zip descargado
                    Files.delete(zipFilePath);
                    System.out.println("ChromeDriver actualizado correctamente.");
                } else {
                    System.out.println("Error al descargar el archivo zip: " + zipResponseCode);
                }
            } else {
                System.out.println("Error al obtener el número de versión: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
