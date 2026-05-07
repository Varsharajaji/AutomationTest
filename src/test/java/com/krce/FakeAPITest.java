package com.krce;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.List;

public class FakeAPITest {
    @BeforeClass
    public void setUp(){
        RestAssured.baseURI = "https://api.escuelajs.co/api/v1";
    }
    @Test
    public void testGetProducts(){
        RestAssured.given()
                .queryParam("price",100)
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                //.body("size()", Matchers.greaterThan(0))
                .body("[0].price",Matchers.equalTo(100));
    }
    @Test
    public void testGetCategories() {
        RestAssured.given()
                .when()
                .get("/categories")
                .then()
                .statusCode(200)
                .body("s", Matchers.instanceOf(List.class));
    }
    @Test
    public void testGetCategoriesById()
    {
        RestAssured.given()
                .pathParam("id",1)
                .when()
                .get("/categories/{id}")
                .then()
                .statusCode(200)
                .body("id",Matchers.equalTo(1));
    }
    @Test
    public void testCreateCategories()
    {
        String body = """
                {
                  "name": "Sakthiiswarya",
                  "image": "https://placeimg.com/640/480/any"
                }""";
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/categories")
                .then()
                .log().all()
                .statusCode(200)
                .body("id",Matchers.equalTo("NashibaMalik"))
                .body("",Matchers.equalTo("https://placeimg.com/640/480/any"));
    }
}
