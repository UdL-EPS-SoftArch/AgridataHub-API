package cat.udl.eps.softarch.agridatahub.steps;

import cat.udl.eps.softarch.agridatahub.domain.Request;

import cat.udl.eps.softarch.agridatahub.domain.Reuser;
import cat.udl.eps.softarch.agridatahub.repository.RequestRepository;


import cat.udl.eps.softarch.agridatahub.repository.ReuserRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;

import org.junit.Assert;
import org.springframework.http.MediaType;


import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RequestStepDefs {
    final StepDefs stepDefs;
    final RequestRepository requestRepository;
    private ReuserRepository reuserRepository;
    private String newResourceUri;

    RequestStepDefs(StepDefs stepDefs, RequestRepository requestRepository) {
        this.stepDefs = stepDefs;
        this.requestRepository = requestRepository;
    }


    @When("I create a new request with description {string}")
    public void iCreateANewRequestWithDescription(String description) throws Throwable{

        Request request = new Request();
        request.setDescription(description);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stepDefs.mapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");

    }

    @And("It has been created a new Request with description {string}")
    public void itHasBeenCreatedANewRequestWithDescription(String description) throws Throwable{

        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is(description)));

    }

    @And("There is no registered request with description {string}")
    public void thereIsNoRegisteredRequestWithDescription(String descrip) {
        Assert.assertTrue(requestRepository.findRequestByDescription(descrip).isEmpty());

    }

    @And("I cannot create a request with description {string}")
    public void iCannotCreateARequestWithDescription(String descrip) {
        Assert.assertNull(newResourceUri);
    }

    @And("It has been created a new Request by {string}")
    public void itHasBeenCreatedANewRequestBy(String reuser_username) throws Throwable {

        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri + "/requestedBy")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(reuser_username)));

    }

    @And("I create a request with description {string}")
    public void iCreateARequestWithDescription(String descrip) throws  Throwable{
        Request request = new Request();
        request.setDescription(descrip);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stepDefs.mapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
    }

    @And("I retrieve a Request by {string}")
    public void iRetrieveARequestBy(String username) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri + "/requestedBy")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(username)));

    }
}