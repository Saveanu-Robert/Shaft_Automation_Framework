package com.automation.CucumberSteps;

import com.shaft.api.RestActions;
import com.shaft.driver.DriverFactory;
import com.shaft.validation.Validations;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

public class StepDefinitions {
  private RestActions apiObject = DriverFactory.getAPIDriver("https://datausa.io/api/");
  private Response apiResponse;

  @Given("I send a GET request to {string}")
  public void i_send_a_GET_request_to(String url) {

    apiResponse =
        apiObject
            .buildNewRequest(url, RestActions.RequestType.GET)
            .setUrlArguments("drilldowns=Nation&measures=Population")
            .setTargetStatusCode(200)
            .performRequest();
  }

  @Then("the response body should contain population data for the {string}")
  public void the_response_body_should_contain_population_data_for_the_nation(
      String expectedNation) {
    Validations.assertThat()
        .response(apiResponse)
        .extractedJsonValue("data[0].Nation")
        .isEqualTo(expectedNation)
        .perform();

    Validations.assertThat()
        .response(apiResponse)
        .matchesSchema("country_population.json")
        .perform();
  }
}
