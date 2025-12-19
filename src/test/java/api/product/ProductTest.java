package api.product;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ProductTest {

    private String loginComoAdmin() {

    Response response =
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

    assertEquals(200, response.getStatusCode());

    String token = response.jsonPath().getString("authorization");
    assertNotNull(token);

    return token;
    }

    private String gerarNomeProdutoUnico(){
        return "Produto QA" + System.currentTimeMillis();
    }

    @BeforeAll
    public static void setup(){
        RestAssured.baseURI = "https://serverest.dev";
    }

    @Test
    public void quandoCrioProdutoSemTokenEntaoStatus401(){
        Response response =
            given()
                .contentType("application/json")
                .body("""
                        {
                        "nome": "%s",
                        "preco": 100,
                        "descricao": "Produto sem token",
                        "quantidade": 10
                        }
                        """)
            .when()
                .post("/produtos");

        assertEquals(401, response.getStatusCode());
    }

    @Test
    void quandoCrioProdutoComToken_entaoStatus201() {
        // Login
        String token = loginComoAdmin();

        // CRIAR PRODUTO
        Response produtoResponse =
            RestAssured
                .given()
                    .contentType("application/json")
                    .header("Authorization", token)
                    .body("""
                        {
                        "nome": "%s",
                        "preco": 100,
                        "descricao": "Produto com token",
                        "quantidade": 10
                        }
                        """)
                .when()
                    .post("/produtos");

        assertEquals(201, produtoResponse.getStatusCode());
    }

    @Test
    public void quandoAtualizoProdutoComTokenEntaoStatus200(){
        //Login
        String token = loginComoAdmin();

        //Criar produto
        String nomeProduto = gerarNomeProdutoUnico();
        Response createResponse = 
                    given()
                        .contentType("application/json")
                        .header("Authorization", token)
                        .body(String.format("""
                        {
                        "nome": "%s",
                        "preco": 100,
                        "descricao": "Produto antes do update",
                        "quantidade": 10
                        }
                        """, nomeProduto))
                    .when()
                        .post("/produtos");
        assertEquals(201, createResponse.getStatusCode());

        String productId = createResponse.jsonPath().getString("_id");
        assertNotNull(productId);

        //Atualizar produto
        Response uptadeResponse = 
                        given()
                            .contentType("application/json")
                            .header("Authorization", token)
                            .body(String.format("""
                            {
                            "nome": "%s",
                            "preco": 150,
                            "descricao": "Produto após update",
                            "quantidade": 20
                            }
                            """, nomeProduto))
                        .when()
                            .put("/produtos/" + productId);
        
        assertEquals(200, uptadeResponse.getStatusCode());
    }

    @Test
    public void quandoDeletoProdutoEntaoStatus200(){
        //Login
        String token = loginComoAdmin();

        //Criar produto
        String nomeProduto = gerarNomeProdutoUnico();
        Response createResponse = 
                    given()
                        .contentType("application/json")
                        .header("Authorization", token)
                        .body(String.format("""
                                {
                                "nome": "%s",
                                "preco": 10,
                                "descricao": "Produto temporário",
                                "quantidade": 10
                                }
                                """, nomeProduto))
                    .when()
                        .post("/produtos");
        assertEquals(201, createResponse.getStatusCode());

        String productId = createResponse.jsonPath().getString("_id");
        assertNotNull(productId);

        //Deletar produto
        Response deleteResponse = 
                        given()
                            .header("Authorization", token)
                        .when()
                            .delete("/produtos/" + productId);

        assertEquals(200, deleteResponse.getStatusCode());       
    }
}
