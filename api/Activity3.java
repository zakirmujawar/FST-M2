package activities;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.equalTo;

public class Activity3 {
        RequestSpecification requestSpec;
        ResponseSpecification responseSpec;

        @BeforeClass
        public void setUp(){
            // Create request specification
            requestSpec = new RequestSpecBuilder()
                    .setContentType(ContentType.JSON)
                    .setBaseUri("https://petstore.swagger.io/v2/pet")
                    .build();

            responseSpec = new ResponseSpecBuilder()
                    .expectStatusCode(200)
                    .expectContentType("application/json")
                    .expectBody("status", equalTo("alive"))
                    .build();
        }
        @DataProvider
        public Object[] [] petInfoProvider(){
            // setting parameters to pass test case
        Object[][] testData = new Object[][] {
                { 77232, "Riley", "alive" },
                { 77233, "Hansel", "alive" }
        };
        return testData;
        }

        @Test(priority = 1)
        public void postRequestforPet(){
            String reqBody = "{\"id\": 77232, \"name\": \"Riley\", \"status\": \"alive\"}";

            Response response = given().spec(requestSpec) // Use requestSpec
                .body(reqBody) // Send request body
                .when().post(); // Send POST request
            System.out.println(response.asPrettyString());

            reqBody = "{\"id\": 77233, \"name\": \"Hansel\", \"status\": \"alive\"}";
            response = given().spec(requestSpec) // Use requestSpec
                    .body(reqBody) // Send request body
                    .when().post(); // Send POST request
            System.out.println(response.asPrettyString());


            // Assertions
            response.then().spec(responseSpec); // Use responseSpec
        }

        @Test (dataProvider = "petInfoProvider", priority = 2)
        public void getRequestforPet(int id, String name, String status){
            Response response = given().spec(requestSpec)
                    .pathParams("petId", id)
                            .when().get("/{petId}");
            System.out.println(response.asPrettyString());

            response.then()
                .spec(responseSpec)
                .body("name", equalTo(name));
            System.out.println(response.asPrettyString());

        }

        @Test (dataProvider = "petInfoProvider", priority = 3)
        public void deleteRequestforPet(int id, String name, String status){
            Response response = given().spec(requestSpec)
                            .pathParams("petId", id)
                                    .when().delete("/{petId}");
            System.out.println(response.asPrettyString());

            response.then().body("code", equalTo(200));

        }
}
