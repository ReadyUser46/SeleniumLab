package mrrobot.autodriver.apiclient.models;

import io.restassured.http.ContentType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * pojo to represesnt request
 */
@Data
@AllArgsConstructor
public class RequestModel {

    private Object body;
    private String baseUri;
    private String basePath;
    private ContentType contentType;
    private HashMap<String, Object> headers;

    public RequestModel() {
        headers = new LinkedHashMap<>();
    }

    public void appendBaseUri(String uri) {
        baseUri = baseUri + uri;
    }

    public void appendBasePath(String path) {
        basePath = basePath + path;
    }
}
