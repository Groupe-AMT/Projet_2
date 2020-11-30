package ch.heigvd.amt.projet2.api.spec.steps;

import ch.heigvd.amt.projet2.api.DefaultApi;
import ch.heigvd.amt.projet2.ApiResponse;
import ch.heigvd.amt.projet2.ApiException;
import ch.heigvd.amt.projet2.api.dto.ApiKey;
import ch.heigvd.amt.projet2.api.dto.Registration;

import ch.heigvd.amt.projet2.api.spec.helpers.Environment;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RegistrationSteps {
    private final DefaultApi api;

    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private int lastStatusCode;

    Registration registrationPayload = new Registration()
            .name("testRegistration")
            .description("testing the registration processus")
            .contact("administrator");

    public RegistrationSteps(Environment environment) {
        this.api = environment.getApi();
    }

    @Given("there is a server")
    public void there_is_a_server() {
        assertNotNull(api);
    }

    @When("^I send a GET to the /applications endpoint$")
    public void iSendAGETToTheApplicationsEndpoint() {
        try {
            processApiResponse(api.getApplicationsWithHttpInfo());
        } catch (ApiException e) {
            processApiException(e);
        }
    }

    @When("^I send a GET to the /applications endpoint with API Key$")
    public void iSendAGETToTheApplicationsEndpointWithAPIKey() {
        try {
            lastApiResponse = api.getApplicationsWithHttpInfo();
            processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            processApiException(e);
        }
    }

    @When("^I send a POST to the /applications endpoint for registration$")
    public void iSendAPOSTToTheApplicationsEndpointForRegistration() {
        try {
            lastApiResponse = api.registerAppWithHttpInfo(registrationPayload);
            processApiResponse(lastApiResponse);
            ApiKey key = (ApiKey) lastApiResponse.getData();
            assert key.getXapiKey() != null;
            api.getApiClient().setApiKey(key.getXapiKey().toString());
        } catch (ApiException e) {
            processApiException(e);
        }
    }

    @Then("I receive a {int} status code")
    public void i_receive_a_status_code(int expectedStatusCode) {
        assertEquals(expectedStatusCode, lastStatusCode);
    }

    private void processApiResponse(ApiResponse apiResponse) {
        lastApiResponse = apiResponse;
        lastApiException = null;
        lastStatusCode = lastApiResponse.getStatusCode();
    }

    private void processApiException(ApiException apiException) {
        lastApiResponse = null;
        lastApiException = apiException;
        lastStatusCode = lastApiException.getCode();
    }
}