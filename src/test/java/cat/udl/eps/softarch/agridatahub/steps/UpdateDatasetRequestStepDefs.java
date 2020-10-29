package cat.udl.eps.softarch.agridatahub.steps;

import cat.udl.eps.softarch.agridatahub.domain.DatasetRequest;
import cat.udl.eps.softarch.agridatahub.repository.DatasetRepository;
import cat.udl.eps.softarch.agridatahub.repository.DatasetRequestRepository;
import cat.udl.eps.softarch.agridatahub.repository.RequestRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class UpdateDatasetRequestStepDefs {

    final StepDefs stepDefs;
    final DatasetRequestRepository datasetRequestRepository;
    final DatasetRepository datasetRepository;
    final RequestRepository requestRepository;

    public static DatasetRequest datasetRequest;
    public String id;

    UpdateDatasetRequestStepDefs(StepDefs stepDefs, DatasetRequestRepository datasetRequestRepository, DatasetRepository datasetRepository, RequestRepository requestRepository) {
        this.stepDefs = stepDefs;
        this.datasetRequestRepository = datasetRequestRepository;
        this.datasetRepository = datasetRepository;
        this.requestRepository = requestRepository;
    }

    @When("I change the status value of DatasetRequest to {string}")
    public void iChangeTheStatusValueOfDatasetRequestA(String granted) throws Exception {
        id = stepDefs.result.andReturn().getResponse().getHeader("Location");
        stepDefs.result = stepDefs.mockMvc.perform(
                patch(id)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(String.valueOf((new JSONObject().put("granted", granted))))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
        ).andDo(print());
    }

    @And("It has been updated the status value of DatasetRequest to {string}")
    public void itHasBeenUpdatedTheStatusValueOfDatasetRequestTo(String granted) throws Exception {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(id)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.granted", is(Boolean.parseBoolean(granted))));
    }
}
