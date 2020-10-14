package cat.udl.eps.softarch.agridatahub.steps;

import cat.udl.eps.softarch.agridatahub.domain.Request;

import cat.udl.eps.softarch.agridatahub.repository.RequestRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.MediaType;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


public class RequestStepDefs {
    final StepDefs stepDefs;
    final RequestRepository requestRepository;

    RequestStepDefs(StepDefs stepDefs, RequestRepository requestRepository) {
        this.stepDefs = stepDefs;
        this.requestRepository = requestRepository;
    }


    @When("I create a new request for a Dataset")
    public void iCreateANewRequestForADataset() throws Throwable {

        Date date = new Date();
        Request request = new Request();
        request.setCreationDate(date);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/requests")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(stepDefs.mapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

    }

    @Given("There is no created requests by user with username {string}")
    public void thereIsNoCreatedRequestsByUserWithUsername(String arg0) {
    }

    @And("There is a registered user with username {string}")
    public void thereIsARegisteredUserWithUsername(String arg0) {
    }
}