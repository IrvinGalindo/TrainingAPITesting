package org.automation.Kim;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class UpdateUser {


    @BeforeClass
    public void setUp(){
        RestAssured.baseURI = "https://reqres.in/";
        RestAssured.basePath = "/api";
    }

    @Test
    public void userNameShouldBeUpdated(){
        String updatedName = "Galletotota";
        given()
                .contentType(ContentType.JSON)
                .pathParam("userId", 2)
                .body("{\n" +
                        "    \"name\": \"" + updatedName + "\",\n" +
                        "    \"job\": \"zion resident\"\n" +
                        "}")
                .when()
                .put("/users/{userId}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("name", Matchers.equalTo(updatedName));



    }

    @Test
    public void shouldValidateUserJobHasntBeenUpdated() {
        String expectedName = "Galletotota";
        String expectedJob = "zion resident";
        given() //obtenemos nuevo resultado
                .contentType(ContentType.JSON)
                .pathParam("userId", 2)
                .body("{\n" +
                        "    \"name\": \"Galletotota\",\n" +
                        "    \"job\": \"zion resident\"\n" +
                        "}")
                .when()
                .put("/users/{userId}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("name",is(expectedName))
                .body("job",is(expectedJob));//El body despues del then tenemos acceso al body de la response


    }

}
