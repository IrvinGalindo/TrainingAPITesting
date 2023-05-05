package org.automation.Kim;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class UpdateUser {

    @BeforeClass
    public void setUp(){
        RestAssured.baseURI = "https://reqres.in/";
        RestAssured.basePath = "/api";
    }
}
