package cat.udl.eps.softarch.agridatahub.steps;

import cat.udl.eps.softarch.agridatahub.domain.Request;
import cat.udl.eps.softarch.agridatahub.repository.RequestRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.springframework.http.MediaType;

import java.util.Date;


import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class UpdateRequestStepDefs {
    final StepDefs stepDefs;
    final RequestRepository requestRepository;
    Request requestGlobal;

    UpdateRequestStepDefs(StepDefs stepDefs, RequestRepository requestRepository) {
        this.stepDefs = stepDefs;
        this.requestRepository = requestRepository;
    }


    @When("I change the description of the previous request to {string}")
    public void iChangeTheDescriptionOfThePreviousRequestTo(String description) throws Throwable {

        stepDefs.result = stepDefs.mockMvc.perform(
                patch(requestGlobal.getUri())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content((new JSONObject().put("description", description)).toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
        ).andDo(print());
    }

    @And("It has been updated the request description to {string}")
    public void itHasBeenUpdatedTheRequestDescriptionTo(String description) throws Throwable {

        stepDefs.result = stepDefs.mockMvc.perform(
                get(requestGlobal.getUri())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.description", is(description)));
    }

    @And("Exists a created request with description {string}")
    public void existsACreatedRequestWithDescription(String description) {
        Request request = new Request();
        request.setDescription(description);
        requestGlobal = requestRepository.save(request);
    }

    @When("I change the date of the previous request to current date")
    public void iChangeTheDateOfThePreviousRequestToCurrentDate() throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                patch(requestGlobal.getUri())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content((new JSONObject().put("creationDate", new Date())).toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
        ).andDo(print());
    }
}
