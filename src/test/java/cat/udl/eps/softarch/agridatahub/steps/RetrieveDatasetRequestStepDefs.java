package cat.udl.eps.softarch.agridatahub.steps;

import cat.udl.eps.softarch.agridatahub.domain.DatasetRequest;
import cat.udl.eps.softarch.agridatahub.repository.DatasetRepository;
import cat.udl.eps.softarch.agridatahub.repository.DatasetRequestRepository;
import cat.udl.eps.softarch.agridatahub.repository.RequestRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.springframework.http.MediaType;

import java.util.Date;

import static org.hamcrest.Matchers.hasSize;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class RetrieveDatasetRequestStepDefs {

    final StepDefs stepDefs;
    final DatasetRequestRepository datasetRequestRepository;
    final DatasetRepository datasetRepository;
    final RequestRepository requestRepository;

    public static DatasetRequest datasetRequest;
    public static Date date;


    RetrieveDatasetRequestStepDefs(StepDefs stepDefs, DatasetRequestRepository datasetRequestRepository, DatasetRepository datasetRepository, RequestRepository requestRepository) {
        this.stepDefs = stepDefs;
        this.datasetRequestRepository = datasetRequestRepository;
        this.datasetRepository = datasetRepository;
        this.requestRepository = requestRepository;
    }
    @Given("There is a created DatasetRequest associate with Dataset {string} and {string} and Request {string} with status value {string}")
    public void thereIsACreatedDatasetRequestAssociateWithDatasetAndAndRequestWithStatusValue(String title, String description1, String description2, String granted) {
        datasetRequest = new DatasetRequest();
        datasetRequest.setGranted(Boolean.parseBoolean(granted));
        datasetRequest.setRequestOf(datasetRepository.findDatasetByTitleAndDescription(title, description1));
        datasetRequest.setRequestedIn(requestRepository.findByCreationDateAndAndDescription(date,description2));
        datasetRequestRepository.save(datasetRequest);
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
