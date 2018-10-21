package com.osinskik.playground.loggingtest

import com.jayway.restassured.RestAssured
import com.jayway.restassured.RestAssured.given
import com.jayway.restassured.builder.RequestSpecBuilder
import com.jayway.restassured.filter.log.RequestLoggingFilter
import com.jayway.restassured.filter.log.ResponseLoggingFilter
import com.jayway.restassured.parsing.Parser
import org.apache.http.HttpStatus
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.equalTo
import org.slf4j.LoggerFactory
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testng.annotations.BeforeClass
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test


/**
 * @author kamil.osinski
 */
@ContextConfiguration(classes = [TestConfig::class])
class AppTest: AbstractTestNGSpringContextTests() {

  @BeforeClass
  fun initClass() {
    RestAssured.defaultParser = Parser.TEXT
    LOGGER.warn("Class init")
  }

  @BeforeMethod
  fun initMethod() {
    LOGGER.warn("Method init")
  }

  @Test
  fun `should be possitive with restAssured`() {
    given()
        .spec(getRequestSpec().build())
        .`when`()
        .get("good_joke")
        .then()
        .statusCode(HttpStatus.SC_OK)
        .body("data", equalTo("good_joke"))
  }

  @Test
  fun `should be negative with restAssured`() {
    given()
        .spec(getRequestSpec().build())
        .`when`()
        .get("bad_joke")
        .then()
        .statusCode(HttpStatus.SC_OK)
        .body("data", equalTo("badum_tss"))
  }


  @Test
  fun `should be possitive with restAssured and assertj`() {
    val response = given()
        .spec(getRequestSpec().build())
        .`when`()
        .get("good_joke")
        .then()
        .statusCode(HttpStatus.SC_OK)
        .extract().`as`(Response::class.java)
    assertThat(response.data).isEqualTo("good_joke")
  }

  @Test
  fun `should be negative with restAssured and assertj`() {
    val response = given()
        .spec(getRequestSpec().build())
        .`when`()
        .get("bad_joke")
        .then()
        .statusCode(HttpStatus.SC_OK)
        .extract().`as`(Response::class.java)
    assertThat(response.data).isEqualTo("badum_tss")
  }


  fun getRequestSpec() = RequestSpecBuilder()
      .addFilter(ResponseLoggingFilter())
      .addFilter(RequestLoggingFilter())

  data class Response (
      val data:String,
      val date: Long
  )

  companion object {
    val LOGGER = LoggerFactory.getLogger(AppTest::class.java)
  }
}