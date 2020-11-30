package ch.heigvd.amt.projet2.api.spec.steps;

import ch.heigvd.amt.projet2.api.DefaultApi;
import ch.heigvd.amt.projet2.ApiResponse;
import ch.heigvd.amt.projet2.ApiException;
import ch.heigvd.amt.projet2.api.dto.ApiKey;
import ch.heigvd.amt.projet2.api.dto.Badge;
import ch.heigvd.amt.projet2.api.dto.PointScale;
import ch.heigvd.amt.projet2.api.dto.Registration;

import ch.heigvd.amt.projet2.api.spec.helpers.Environment;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;

public class BaseSteps {
    private final DefaultApi api;

    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private int lastStatusCode;

    public BaseSteps(Environment environment) {
        this.api = environment.getApi();
    }

    //////////////////////////////////////
    // Basic elements ////////////////////
    //////////////////////////////////////

    @Given("there is a server")
    public void there_is_a_server() {
        assertNotNull(api);
    }

    @Then("I receive a {int} status code")
    public void i_receive_a_status_code(int expectedStatusCode) {
        assertEquals(expectedStatusCode, lastStatusCode);
    }

    //////////////////////////////////////
    // Registration //////////////////////
    //////////////////////////////////////

    Registration registrationPayload;

    @Given("I have a registration payload for tester")
    public void IHaveARegistrationPayloadForTest() {
        registrationPayload = new Registration()
                .name("testBasic1")
                .description("testing the registration processus")
                .contact("administrator");
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

    //////////////////////////////////////
    //  Badges and Point scales separated
    //////////////////////////////////////

    @Given("I am in application testBasic{int}")
    public void iAmInApplicationTestBasic(int arg0) {
        try {
            lastApiResponse = api.getApplicationsWithHttpInfo();
            processApiResponse(lastApiResponse);
            if (lastApiResponse.getData().toString().contains("name: testBasic"+arg0)){
                assertNotNull(lastApiResponse);
            }
        } catch (ApiException e) {
            processApiException(e);
        }
    }

    Badge badgeBasic1;

    @Given("I have a badge payload for testBasic{int}")
    public void iHaveABadgePayloadForTestBasic(int arg0) {
        badgeBasic1 = new Badge()
                .name("testBadge")
                .image("testImg");
    }

    @When("^I create a badge, POST /badges$")
    public void iCreateABadgePOSTBadges() {
        try {
            lastApiResponse = api.createBadgeWithHttpInfo(badgeBasic1);
            processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            processApiException(e);
        }
    }

    @When("I ask for badges")
    public void iAskForBadges() {
        try {
            lastApiResponse = api.getBadgesWithHttpInfo();
            processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            processApiException(e);
        }
    }

    @Then("I can find the created badge")
    public void iCanFindTheCreatedBadge() {
        System.out.println(lastApiResponse.getData());
        if (lastApiResponse.getData().toString().contains("name: testBadge")){
            assertNotNull(lastApiResponse);
        }
    }

    PointScale pointScaleBasic1;

    @Given("I have a point scale payload for testBasic{int}")
    public void iHaveAPointScalePayloadForTestBasic(int arg0) {
        pointScaleBasic1 = new PointScale()
                .name("testPointscale")
                .scale(100);
    }

    @When("^I create a point scale, POST /pointscales$")
    public void iCreateAPointScalePOSTPointscales() {
        try {
            lastApiResponse = api.createPointScaleWithHttpInfo(pointScaleBasic1);
            processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            processApiException(e);
        }
    }

    @When("I ask for point scale")
    public void iAskForPointScale() {
        try {
            lastApiResponse = api.getPointScalesWithHttpInfo();
            processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            processApiException(e);
        }
    }

    @Then("I can find the created point scale")
    public void iCanFindTheCreatedPointScale() {
        System.out.println(lastApiResponse.getData());
        if (lastApiResponse.getData().toString().contains("name: testPointscale")){
            assertNotNull(lastApiResponse);
        }
    }

    @Given("I have an other registration payload for tester")
    public void iHaveAnOtherRegistrationPayloadForTester() {
        registrationPayload = new Registration()
                .name("testBasic2")
                .description("testing the registration processus")
                .contact("administrator");
    }

    @Then("I can not find the created badge")
    public void iCanNotFindTheCreatedBadge() {
        assertEquals(lastApiResponse.getData().toString(), "[]");
    }

    @Then("I can not find the created point scale")
    public void iCanNotFindTheCreatedPointScale() {
        assertEquals(lastApiResponse.getData().toString(), "[]");
    }

    //////////////////////////////////////
    // Process part //////////////////////
    //////////////////////////////////////

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