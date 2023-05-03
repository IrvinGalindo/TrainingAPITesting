package org.automation.Armando;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertEquals;

public class ArmandoTest {

    @BeforeClass
    public void serUpRestAssured(){
        RestAssured.baseURI = "https://reqres.in/";
        RestAssured.basePath = "/api";
    }

    @Test
    public void shouldAnswerWithTrue() {
        //   RestAssured.baseURI = "https://reqres.in/";
        //   RestAssured.basePath = "/api";
        // RestAssured tambi´pen se opuede quitar esto y solo importarlo en el given sin punto
        given() //obtenemos nuevo resultado
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
        //    RestAssured.baseURI = "https://reqres.in/"; //Esto se repite arriba asì que no es necesario volverlo a poner
        //    RestAssured.basePath = "/api";
        Response jsonPath = given()
                .log().all()
                .pathParam("userId",2)
                .when()
                .get("/users/{userId}")
                .then()
                .log().all()
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

    @Test
    public void shouldValidateUserWithMap(){
        HashMap user = new HashMap();
        user.put("first_name", "Janet");
        user.put("last_name", "Weaver");
        user.put("email", "janet.weaver@reqres.in");
        user.put("avatar", "http://reqres.in/img/faces/2-image.jpg");


        given()
                .log().all()
                .pathParam("userId",2)
                .when()
                .get("/users/{userId}")
                .then()
                .log().all()
                .body("data",is(user));


    }

}