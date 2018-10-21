package com.osinskik.playground.loggingtest

import com.jayway.restassured.RestAssured
import org.springframework.boot.test.context.TestConfiguration
import javax.annotation.PostConstruct

/**
 * @author kamil.osinski
 */
@TestConfiguration
class TestConfig {

  @PostConstruct
  fun postConstruct() {
    RestAssured.baseURI = "http://localhost:8080"
//    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
  }
}