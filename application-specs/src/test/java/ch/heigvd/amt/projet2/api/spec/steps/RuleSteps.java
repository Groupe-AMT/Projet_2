package ch.heigvd.amt.projet2.api.spec.steps;

import ch.heigvd.amt.projet2.ApiException;
import ch.heigvd.amt.projet2.ApiResponse;
import ch.heigvd.amt.projet2.api.DefaultApi;
import ch.heigvd.amt.projet2.api.dto.ApiKey;
import ch.heigvd.amt.projet2.api.dto.Badge;
import ch.heigvd.amt.projet2.api.dto.Event;
import ch.heigvd.amt.projet2.api.dto.PointScale;
import ch.heigvd.amt.projet2.api.dto.Registration;
import ch.heigvd.amt.projet2.api.dto.Rule;
import ch.heigvd.amt.projet2.api.dto.RuleIf;
import ch.heigvd.amt.projet2.api.dto.RuleThen;
import ch.heigvd.amt.projet2.api.dto.RuleThenPoints;
import ch.heigvd.amt.projet2.api.spec.helpers.Environment;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RuleSteps {
    private final DefaultApi api;

    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private int lastStatusCode;

    public RuleSteps(Environment environment) {
        this.api = environment.getApi();
    }

    @Given("there is a server for rule steps")
    public void there_is_a_server_for_rule_steps() {
        assertNotNull(api);
    }

    @Then("I receive a {int} status code for rule steps")
    public void i_receive_a_status_code_for_rule_steps(int expectedStatusCode) {
        assertEquals(expectedStatusCode, lastStatusCode);
    }



    //////////////////////////////////////
    // Registration //////////////////////
    //////////////////////////////////////

    Registration registration;

    @Given("^I have a registration payload$")
    public void iHaveARegistrationPayload() {
        registration = new Registration()
                .name("TestRule")
                .description("Test pour vérifier le fonctionnement des règles")
                .contact("l'administrateur");
    }

    @When("^I send a POST to the /applications endpoint$")
    public void iSendAPOSTToTheApplicationsEndpoint() {
        try {
            lastApiResponse = api.registerAppWithHttpInfo(registration);
            processApiResponse(lastApiResponse);
            ApiKey key = (ApiKey) lastApiResponse.getData();
            assert key.getXapiKey() != null;
            api.getApiClient().setApiKey(key.getXapiKey().toString());
        } catch (ApiException e) {
            processApiException(e);
        }
    }

    //////////////////////////////////////
    // Badge /////////////////////////////
    //////////////////////////////////////

    Badge badge;

    @Given("^I have a badge payload$")
    public void iHaveABadgePayload() {
        badge = new Badge()
                .name("TestingBadge")
                .image("imagetest");
    }

    @When("^I send a POST to the /badges endpoint$")
    public void iSendAPOSTToTheBadgesEndpoint() {
        try {
            lastApiResponse = api.createBadgeWithHttpInfo(badge);
            processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            processApiException(e);
        }
    }

    //////////////////////////////////////
    // Pointscale ////////////////////////
    //////////////////////////////////////

    PointScale pointScale;

    @Given("^I have a point scale payload$")
    public void iHaveAPointScalePayload() {
        pointScale = new PointScale()
                .name("TestingPointScale")
                .scale(100);
    }

    @When("^I send a POST to the /pointscales endpoint$")
    public void iSendAPOSTToThePointScalesEndpoint() {
        try {
            lastApiResponse = api.createPointScaleWithHttpInfo(pointScale);
            processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            processApiException(e);
        }
    }

    //////////////////////////////////////
    // Rules /////////////////////////////
    //////////////////////////////////////

    Rule rule;

    @Given("^I have a rule payload$")
    public void iHaveARulePayload() {
        RuleIf ruleIf = new RuleIf()
                .action("testing")
                .attribute("0");
        RuleThenPoints ruleThenPoints = new RuleThenPoints()
                .pointscale("TestingPointScale")
                .amount(5);
        RuleThen ruleThen = new RuleThen()
                .badge("TestingBadge")
                .points(ruleThenPoints);
        rule = new Rule()
                .name("TestingRule")
                ._if(ruleIf)
                .then(ruleThen);
    }

    @When("^I send a POST to the /rule endpoint$")
    public void iSendAPOSTToTheRuleEndpoint() {
        try {
            lastApiResponse = api.createRuleWithHttpInfo(rule);
            processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            processApiException(e);
        }
    }

    //////////////////////////////////////
    // Event /////////////////////////////
    //////////////////////////////////////

    Event event;

    @Given("^I have an event payload$")
    public void iHaveAnEventPayload() {
        event = new Event()
                .action("testing")
                .attribute("0");
    }

    @When("^I send a POST to the /event endpoint$")
    public void iSendAPOSTToTheEventEndpoint() {
        try {
            lastApiResponse = api.createEventWithHttpInfo(event);
            processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            processApiException(e);
        }
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
