package org.automation;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;


import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertEquals;

public class KimberlyTest {
    @Test
    public void shouldAnswerWithTrue() {
        RestAssured.baseURI = "https://reqres.in/";
        RestAssured.basePath = "/api";
        RestAssured
                .given() //obtenemos nuevo resultado
                .pathParam("userId",2) //a ese nuevo resultado le mandamos llamar el metodo que tiene y le estamos mandando información
                .when() //Obtenemos el objeto que regresa el when
                .get("/users/{userId}")//extraemos el metodo get donde le extraemos información
                .then()//validamos el get
                .statusCode(HttpStatus.SC_OK)
                .body("data.id",is(2))//El body despues del then tenemos acceso al body de la response
                .body("data.first_name",is("Janet"))
                .body("data.last_name",is("Weaver"))
                .body("data.email", is("janet.weaver@reqres.in"));
    }

    @Test
    public void shouldValidateUserWithTestNgAssertions(){
        RestAssured.baseURI = "https://reqres.in/";
        RestAssured.basePath = "/api";
        Response jsonPath = RestAssured
                .given()
                .pathParam("userId",2)
                .when()
                .get("/users/{userId}")
                .then()
                .extract()
                .response();

        //System.out.println(jsonPath.asPrettyString());
        int actualUserId = jsonPath.path("data.id");
        String actualFirstName = jsonPath.path("data.first_name");
        String actualLastName = jsonPath.path("data.last_name");
        String actualEmail = jsonPath.path("data.email");

        assertEquals(jsonPath.statusCode(), HttpStatus.SC_OK,"Status should be 200");
        assertEquals(actualUserId, 2, "User Id should be 2");
        assertEquals(actualFirstName, "Janet", "User name should be Janet");
        assertEquals(actualLastName, "Weaver","User last name should be Weaver");
        assertEquals(actualEmail, "janet.weaver@reqres.in","User email should be janet.weaver@reqres.in");


    }
}
