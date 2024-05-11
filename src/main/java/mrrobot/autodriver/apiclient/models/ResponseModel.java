package mrrobot.autodriver.apiclient.models;

import io.restassured.response.Response;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseModel {

    private String body;
    private String error;
    private int statusCode;
    private Response response;
}
