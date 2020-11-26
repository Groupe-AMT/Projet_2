package ch.heigvd.amt.projet2.api.spec.steps;

import ch.heigvd.amt.projet2.fruits.api.DefaultApi;
import ch.heigvd.amt.projet2.fruits.api.spec.helpers.Environment;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.swagger.annotations.ApiResponse;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RegistrationSteps<ApiException> {
    private Environment environment;
    private DefaultApi api;

    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;

    String registrationPayload = "{\n" +
            "\t\"name\": \"stackoverflow\",\n" +
            "\t\"description\": \"bla bla\",\n" +
            "\t\"contactEmail\": \"olivier.lietchi@heig-vd.ch\"\n" +
            "}\n";

    public RegistrationSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
    }

    @Given("there is a Fruits server")
    public void there_is_a_Fruits_server() throws Throwable {
        assertNotNull(api);
    }

    @When("^I send a GET to the /applications endpoint$")
    public void iSendAGETToTheApplicationsEndpoint() {
        try {
            lastApiResponse = api.getApplicationsWithHttpInfo();
            processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            processApiException(e);
        }
    }

    @When("^I send a GET to the /applications endpoint with API Key$")
    public void iSendAGETToTheApplicationsEndpointWithAPIKey() {
        try {
            lastApiResponse = api.getApplicationsWithHttpInfo(lastApiResponse);
            processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            processApiException(e);
        }
    }

    @When("^I send a POST to the /registration endpoint$")
    public void iSendAPOSTToTheRegistrationEndpoint() {
        try {
            lastApiResponse = api.postRegistrationWithHttpInfo(registrationPayload);
            processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            processApiException(e);
        }
    }

    @Then("I receive a {int} status code")
    public void i_receive_a_status_code(int expectedStatusCode) throws Throwable {
        assertEquals(expectedStatusCode, lastStatusCode);
    }

    private void processApiResponse(ApiResponse apiResponse) {
        lastApiResponse = apiResponse;
        lastApiCallThrewException = false;
        lastApiException = null;
        lastStatusCode = lastApiResponse.getStatusCode();
        List<String> locationHeaderValues = (List<String>)lastApiResponse.getHeaders().get("Location");
        lastReceivedLocationHeader = locationHeaderValues != null ? locationHeaderValues.get(0) : null;
    }

    private void processApiException(ApiException apiException) {
        lastApiCallThrewException = true;
        lastApiResponse = null;
        lastApiException = apiException;
        lastStatusCode = lastApiException.getCode();
    }
}
