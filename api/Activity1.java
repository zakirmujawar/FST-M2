package activities;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import static org.hamcrest.CoreMatchers.equalTo;
import io.restassured.response.Response;
import io.restassured.specification.Argument;
import org.testng.annotations.Test;

import java.util.List;

public class Activity1 {
    final static String baseURI = "https://petstore.swagger.io/v2/pet";

    @Test (priority = 1)
    public void postRequest(){
        // create JSON request
        String reqBody = "{"
                + "\"id\": 77232,"
                + "\"name\": \"Riley\","
                + " \"status\": \"alive\""
                + "}";
        Response response =
                given().contentType(ContentType.JSON)
                        .body(reqBody)
                        .when().post(baseURI);

        // Assertion
        response.then().body("id", equalTo(77232));
        response.then().body("name", equalTo("Riley"));
        response.then().body("status", equalTo("alive"));
    }
    @Test(priority = 2)
    public void getRequest(){
        Response response =
                given().contentType(ContentType.JSON) // Set headers
                        .when().pathParam("petId", "77232") // Set path parameter
                        .get(baseURI + "/{petId}"); // Send GET request
        // Assertion
        response.then().body("id", equalTo(77232));
        response.then().body("name", equalTo("Riley"));
        response.then().body("status", equalTo("alive"));
    }

    @Test(priority = 3)
    public void deleteRequest(){
        Response response =
                given().contentType(ContentType.JSON) // Set headers
                        .when().pathParam("petId", "77232") // Set path parameter
                        .delete(baseURI + "/{petId}"); // Send DELETE request
        // Assertion
        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("77232"));
    }
}
