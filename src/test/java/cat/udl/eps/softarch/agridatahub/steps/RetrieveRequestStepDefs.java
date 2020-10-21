package cat.udl.eps.softarch.agridatahub.steps;

import cat.udl.eps.softarch.agridatahub.domain.Request;
import cat.udl.eps.softarch.agridatahub.repository.RequestRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.springframework.http.MediaType;

import java.util.Date;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class RetrieveRequestStepDefs {
    final StepDefs stepDefs;
    final RequestRepository requestRepository;

    public RetrieveRequestStepDefs(StepDefs stepDefs, RequestRepository requestRepository) {
        this.stepDefs = stepDefs;
        this.requestRepository = requestRepository;
    }


    @Given("There exists a created request with description {string}")
    public void thereExistsACreatedRequestWithDescription(String descrip) throws Throwable {
        Date date = new Date();
        Request request = new Request();
        request.setCreationDate(date);
        request.setDescription(descrip);
        requestRepository.save(request);
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
}
