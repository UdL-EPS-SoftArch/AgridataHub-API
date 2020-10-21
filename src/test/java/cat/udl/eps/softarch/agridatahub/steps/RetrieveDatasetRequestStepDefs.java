package cat.udl.eps.softarch.agridatahub.steps;

import cat.udl.eps.softarch.agridatahub.repository.DatasetRequestRepository;
import cat.udl.eps.softarch.agridatahub.repository.UserRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.springframework.http.MediaType;
import static org.hamcrest.Matchers.hasSize;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class RetrieveDatasetRequestStepDefs {

    final StepDefs stepDefs;
    final DatasetRequestRepository datasetRequestRepository;

    RetrieveDatasetRequestStepDefs(StepDefs stepDefs, DatasetRequestRepository datasetRequestRepository) {
        this.stepDefs = stepDefs;
        this.datasetRequestRepository = datasetRequestRepository;
    }

    @When("I list all my DatasetRequests in the app.")
    public void iListAllTheExistingDatasetRequestsInTheApp() throws Exception {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/datasetRequests")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("There has been retrieved {int} DatasetRequest")
    public void thereHasBeenRetrievedDatasetRequest(int numDatasetRequest) throws Throwable {
        stepDefs.result.andExpect(jsonPath("$._embedded.datasetRequests", hasSize(numDatasetRequest)));
    }



}
