package cat.udl.eps.softarch.agridatahub.steps;

import cat.udl.eps.softarch.agridatahub.domain.Request;
import cat.udl.eps.softarch.agridatahub.repository.RequestRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.springframework.http.MediaType;

import java.util.Date;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RetrieveRequestStepDefs {
    final StepDefs stepDefs;
    final RequestRepository requestRepository;

    String newResourceUri;

    public RetrieveRequestStepDefs(StepDefs stepDefs, RequestRepository requestRepository) {
        this.stepDefs = stepDefs;
        this.requestRepository = requestRepository;
    }


    @Given("There exists a created request with description {string}")
    public void thereExistsACreatedRequestWithDescription(String descrip) throws Throwable {
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

    @When("I list all the existing requests in the app")
    public void iListAllTheExistingRequestsInTheApp() throws  Throwable{
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/requests")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("There has been retrieved {int} requests")
    public void thereHasBeenRetrievedRequests(int numRequests) throws Throwable {
        stepDefs.result.andExpect(jsonPath("$._embedded.requests", hasSize(numRequests)));
    }

    @And("It has been retrieved a Request by {string}")
    public void itHasBeenRetrievedARequestBy(String username) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri + "/requestedBy")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(username)));

    }


}
