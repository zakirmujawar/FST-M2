package Liveproject;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.restassured.http.Header;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

// this is turn as JUNIT class it will run as JUNIT
@ExtendWith(PactConsumerTestExt.class)
public class ConsumerTest {
    // create headers

    Map<String,String> headers = new HashMap<>();

    // resource path
    String resourcePath ="/api/users";
    // create contract
    @Pact(consumer = "UseerConsmer",provider = "UserProvider")
    public RequestResponsePact createPact(PactDslWithProvider builder){
    // add the headers
        headers.put("Content-Type", "application/json");

        // create request and response body

        DslPart requestResponseBody = new PactDslJsonBody()
                .numberType("id", 123)
                .stringType("firstName", "Zakir")
                .stringType("lastName", "Mujawar")
                .stringType("email", "zakir@gmail.com");
        // Record interaction to pact
        return builder.given("A request to create a user")
                .uponReceiving("A request to create a user")
                    .method("POST")
                    .headers(headers)
                    .path(resourcePath)
                    .body(requestResponseBody)
                .willRespondWith()
                    .status(201)
                    .body(requestResponseBody)
                .toPact();
    }
        // test with mock provider
    @Test
    @PactTestFor(providerName = "UserProvider", port = "8282")
    public void consumerTest(){
        String baseURI = "http://localhost:8282/api/users";
        // request body
        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put("id",123);
        reqBody.put("firstName", "Zakir");
        reqBody.put("lastName", "Mujawar");
        reqBody.put("email", "zakir@gmail.com");

        // generate response
        given().headers(headers).body(reqBody).
                when().post(baseURI).
                then().statusCode(201).log().all();
            }
}
