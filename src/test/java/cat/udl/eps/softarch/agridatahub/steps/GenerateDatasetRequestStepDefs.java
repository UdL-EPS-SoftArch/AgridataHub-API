package cat.udl.eps.softarch.agridatahub.steps;

import cat.udl.eps.softarch.agridatahub.domain.DatasetRequest;
import cat.udl.eps.softarch.agridatahub.repository.DatasetRequestRepository;
import cat.udl.eps.softarch.agridatahub.repository.UserRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GenerateDatasetRequestStepDefs {

    final StepDefs stepDefs;
    final UserRepository userRepository;
    final DatasetRequestRepository datasetRequestRepository;

    public GenerateDatasetRequestStepDefs(StepDefs stepDefs, UserRepository userRepository, DatasetRequestRepository datasetRequestRepository) {
        this.stepDefs = stepDefs;
        this.userRepository = userRepository;
        this.datasetRequestRepository = datasetRequestRepository;
    }


    @When("I create a new DatasetRequest with status value {string}")
    public void iCreateANewDatasetRequestWithValueAndBoolean(String granted) throws Throwable {

        DatasetRequest datasetRequest = new DatasetRequest();
        datasetRequest.setGranted(Boolean.parseBoolean(granted));

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/datasetRequests")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(stepDefs.mapper.writeValueAsString(datasetRequest))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                    .andDo(print());
    }

    @And("It has been created a new DatasetRequest")
    public void itHasBeenCreatedANewDatasetRequestWithValueAndBoolean() throws Exception {
        String id = stepDefs.result.andReturn().getResponse().getHeader("Location");
        stepDefs.result = stepDefs.mockMvc.perform(
                get(id)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(status().isOk());
    }
}

