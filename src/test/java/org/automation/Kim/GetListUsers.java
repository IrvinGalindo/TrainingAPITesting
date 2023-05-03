package org.automation.Kim;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertEquals;

public class GetListUsers {

    @BeforeClass
    public void setUp(){
        RestAssured.baseURI = "https://reqres.in/";
        RestAssured.basePath = "/api";

    }

    @Test
    public void shouldIValidatePageIs2(){

        given() //cuando yo te doy esta información
                .queryParam("page", 2)//se pone despues del given y es la información que estás dando //Construye los queryparam internamente
                .when() //Tu la vas a poner aquí la información
                .get("/users")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("page", is(2));

    }

    @Test
    public void shouldIValidateTotalUsersInPage2Is12(){

        Response jsonPath = given()
                .queryParam("page",2)
                .when()
                .get("/users")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        int actualUsersPerPage = jsonPath.path("per_page");
        int actualTotal = jsonPath.path("total");
        int actualTotalPages = jsonPath.path("total_pages");
        int expectedResult = actualTotalPages * actualUsersPerPage;

        assertEquals(expectedResult, actualTotal, " Total users is not equal to 12");


    }


}
