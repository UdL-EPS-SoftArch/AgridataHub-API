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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RequestStepDefs {
    final StepDefs stepDefs;
    final RequestRepository requestRepository;
    private String newResourceUri;

    RequestStepDefs(StepDefs stepDefs, RequestRepository requestRepository) {
        this.stepDefs = stepDefs;
        this.requestRepository = requestRepository;
    }


    @And("It has been created a new Request")
    public void itHasBeenCreatedANewRequest() throws Throwable {

        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
        stepDefs.result = stepDefs.mockMvc.perform(

          get(newResourceUri)
                .accept(MediaType.APPLICATION_JSON)
                .with(AuthenticationStepDefs.authenticate()))
            .andExpect(status().isOk());

    }

    @When("I create a new request with description {string}")
    public void iCreateANewRequestWithDescription(String description) throws Throwable{

        Date date = new Date();
        Request request = new Request();
        request.setCreationDate(date);
        request.setDescription(description);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/requests")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(stepDefs.mapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

    }
}