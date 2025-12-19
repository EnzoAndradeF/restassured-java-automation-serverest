package api.auth;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LoginTest {

    @BeforeAll
    public static void setup(){
        RestAssured.baseURI = "https://serverest.dev";
    }

    @Test
    public void quandoLoginValidoEntaoReceboToken(){

        Response response = 
            given()
                .contentType("application/json")
                .body("""
                        {
                        "email": "beltrano@qa.com.br",
                        "password": "teste"
                        }
                        """)
            .when()
                .post("/login");

        System.out.println(response.getBody().asString());
        assertEquals(200, response.getStatusCode());

        String token = response.jsonPath().getString("authorization");
        assertNotNull(token);
    }

}
