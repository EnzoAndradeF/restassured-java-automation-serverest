package api.users;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class UserTest {

    private String gerarEmailUnico() {
    return "enzo.qa+" + System.currentTimeMillis() + "@test.com";
    }

    @BeforeAll
    public static void setup(){
        RestAssured.baseURI = "https://serverest.dev";
    }

    @Test
    public void quandoCrioUsuarioValidoEntaoStatus201(){
        String email = gerarEmailUnico();
        Response response =
            given()
                .contentType("application/json")
                .body(String.format("""
                    {
                      "nome": "Enzo QA",
                      "email": "%s",
                      "password": "123456",
                      "administrador": "true"
                    }
                """, email))
            .when()
                .post("/usuarios");

        System.out.println(response.getBody().asString());
        assertEquals(201, response.getStatusCode());

        String message = response.jsonPath().getString("message");
        assertEquals("Cadastro realizado com sucesso", message);
    }

    @Test
    public void quandoListoUsuariosComTokenValidoEntao200(){
        //Login
        Response loginResponse =
            given()
                .contentType("application/json")
                .body("""
                    {
                      "email": "enzo.qa@test.com",
                      "password": "123456"
                    }
                """)
            .when()
                .post("/login");

        System.out.println(loginResponse.getBody().asString());
        assertEquals(200, loginResponse.getStatusCode());

        String token = loginResponse.jsonPath().getString("authorization");
        assertNotNull(token);

        //Listar Usuários
        Response userResponse = 
                given()
                    .header("Authorization", token)
                .when()
                    .get("/usuarios");

        System.out.println(userResponse.getBody().asString());
        assertEquals(200, userResponse.getStatusCode());

    }

    @Test
    void quandoListoUsuariosEntaoListaValida() {
        Response response =
            given()
            .when()
                .get("/usuarios");

        System.out.println(response.getBody().asString());
        assertEquals(200, response.getStatusCode());
        assertTrue(response.jsonPath().getList("usuarios").size() > 0);
    }
        
}
