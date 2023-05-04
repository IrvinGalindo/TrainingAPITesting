package org.automation.Marcela;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class UpdateUserPost {

    @BeforeClass
    public void setUp(){
        RestAssured.baseURI="https://reqres.in";
        RestAssured.basePath="/api";
    }

    @Test
    public void userShouldBeUpdatedAndNameIsReturned(){
        given()

    }

}
