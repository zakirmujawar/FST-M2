package Liveproject;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class GitHub_RestAssured_Project {
    // Declaration of Request & Response specification object.
    RequestSpecification requestSpec;

    ResponseSpecification responseSpec;

    // Variable Declarations
    String sshkey = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQC2h571Z6ocs4i1iTSer26YJ8xJtLf9ZZWHn6XHkzRp7OzGjy9n38VzO67sbbXHGTUkwKhEJByzyXeVIni1J457CpBxzkyW1ebjDTLgK6wh0y4jCHZ6ae26l6gK9s5UK0GBJmVEq4/KWsKBPDW9THl285sVDb8FeGLjsV8AyYiyIxiQu3+zdpuQ9FPkcH4WWabU6608Oora4qNqNWfYm5ekL5NxRLof9Aakr78ZvD49r4BIlBZ3+nKVXDSCGgQijl3J3dvAKaEDOlKRKhkGjgtYfE6Ab7dyUWD/JraXst3fDcW7lGshuTSaBFpG+mo9q2ACpdUskqgFftS25cumdcw5";
    int sshID;

    @BeforeClass
    public void Setup(){
        // Request specification

        requestSpec = new RequestSpecBuilder().
                //add base uri
                setBaseUri("https://api.github.com/user/keys").
                setContentType(ContentType.JSON).
                addHeader("Authorization", "token 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").
                build();

        responseSpec = new ResponseSpecBuilder().
                expectResponseTime(lessThan(4000L)).
                build();
    }

    @Test(priority = 1)
    public void postRequest(){
        // Request Body
        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put("title", "TestAPIKey");
        reqBody.put("key", sshkey);

        //Generate response
        Response response = given().spec(requestSpec).body(reqBody).
                when().post();
        System.out.println(response.getBody().asPrettyString());
        sshID = response.then().extract().path("id");

        //Assertions
        response.then().statusCode(201);
    }

    @Test(priority = 2)
    public void getRequest(){
        given().spec(requestSpec).pathParam("sshID", sshID).
                when().get("/{sshID}").
                then().spec(responseSpec).statusCode(200);

    }

    @Test(priority = 3)
    public void deleteRequest(){
        given().spec(requestSpec).pathParam("sshID", sshID).
                when().delete("/{sshID}").
                then().spec(responseSpec).statusCode(204);
    }
}
