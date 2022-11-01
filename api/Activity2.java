package activities;

import io.restassured.response.Response;

import org.testng.annotations.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Activity2 {
    // Set base URL

    final static String baseURI = "https://petstore.swagger.io/v2/user";
    @Test(priority = 1)
    public void postRequestFromFile() throws IOException {
        // import JSON file
        FileInputStream inputJSON;
        inputJSON = new FileInputStream("src/test/Resources/UserInfo.json");

        // read JSON file as string
        String reqBody = new String(inputJSON.readAllBytes());

        Response response =
                given().contentType(ContentType.JSON)
                        .body(reqBody)
                        .when().post(baseURI);
        inputJSON.close();

        // Assertions
        response.then().body("code", equalTo(200));
        response.then().body("message",equalTo("9901"));
    }

    @Test(priority = 2)
    public void getRequestFromFile(){
        // import JSON file to write
        File outputJSON = new File("src/test/java/Resources/userGETResponse.json");

        Response response =
                given().contentType(ContentType.JSON)
                        .pathParam("username","justinc")
                        .when().get(baseURI + "/{username}");
        String resBody = response.getBody().asPrettyString();

        try {
            // create JSON file
            outputJSON.createNewFile();
            //write response body to external file
            FileWriter writer = new FileWriter(outputJSON.getPath());
            writer.write(resBody);
            writer.close();
        } catch (IOException excp) {
            excp.printStackTrace();
        }
        // Asserttions
        response.then().body("id", equalTo(9901));
        response.then().body("username", equalTo("justinc"));
        response.then().body("firstName", equalTo("Justin"));
        response.then().body("lastName", equalTo("Case"));
        response.then().body("email", equalTo("justincase@mail.com"));
        response.then().body("password", equalTo("password123"));
        response.then().body("phone", equalTo("9812763450"));
    }
    @Test(priority = 3)
    public void deleteRequestParameter() throws IOException{
    Response response =
            given().contentType(ContentType.JSON)
                    .pathParam("username","justinc")
                    .when().delete(baseURI + "/{username}");

    // Assertions
        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("justinc"));
    }
}
