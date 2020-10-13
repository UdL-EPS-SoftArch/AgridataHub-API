package cat.udl.eps.softarch.agridatahub.steps;

import cat.udl.eps.softarch.agridatahub.domain.Request;

import cat.udl.eps.softarch.agridatahub.repository.RequestRepository;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Date;


public class RequestStepDefs {
    final StepDefs stepDefs;
    final RequestRepository requestRepository;

    RequestStepDefs(StepDefs stepDefs, RequestRepository requestRepository) {
        this.stepDefs = stepDefs;
        this.requestRepository = requestRepository;
    }


    @When("I create a new request for a Dataset")
    public void iCreateANewRequestForADataset() {

        Date date = new Date();
        Request request = new Request();
        request.setCreationDate(date);
    }

    @Then("the response code is {int}")
    public void theResponseCodeIs(int result) {
    }

    @Given("There is no created requests by user with username {string}")
    public void thereIsNoCreatedRequestsByUserWithUsername(String username) {

    }

    @And("There is a registered user with username {string}")
    public void thereIsARegisteredUserWithUsername(String username) {

    }

    @And("No DatasetRequests with id {int}")
    public void noDatasetRequestsWithId(int arg0) {
        
    }

    @When("I create a Request for a datasetRequest with id {int}")
    public void iCreateARequestForADatasetRequestWithId(int arg0) {
    }
}
