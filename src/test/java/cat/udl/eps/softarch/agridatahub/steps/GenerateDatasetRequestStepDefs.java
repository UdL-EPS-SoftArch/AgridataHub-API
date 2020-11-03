package cat.udl.eps.softarch.agridatahub.steps;

import cat.udl.eps.softarch.agridatahub.domain.DatasetRequest;
import cat.udl.eps.softarch.agridatahub.domain.Request;
import cat.udl.eps.softarch.agridatahub.repository.DatasetRepository;
import cat.udl.eps.softarch.agridatahub.repository.DatasetRequestRepository;
import cat.udl.eps.softarch.agridatahub.repository.RequestRepository;
import cat.udl.eps.softarch.agridatahub.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.http.MediaType;

import java.util.Date;

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
    final DatasetRepository datasetRepository;
    final RequestRepository requestRepository;

    public static DatasetRequest datasetRequest;
    public static String id;
    public static Date date;

    public GenerateDatasetRequestStepDefs(StepDefs stepDefs, UserRepository userRepository, DatasetRequestRepository datasetRequestRepository, DatasetRepository datasetRepository, RequestRepository requestRepository) {
        this.stepDefs = stepDefs;
        this.userRepository = userRepository;
        this.datasetRequestRepository = datasetRequestRepository;
        this.datasetRepository = datasetRepository;
        this.requestRepository = requestRepository;
    }

    @When("I create a new DatasetRequest associate with Dataset {string} and {string} and Request {string} with status value {string}")
    public void iCreateANewDatasetRequestAssociateWithDatasetAndAndRequestWithStatusValue(String title, String descrip1, String descrip2, String granted) throws Exception {
        datasetRequest = new DatasetRequest();
        datasetRequest.setGranted(Boolean.parseBoolean(granted));
        datasetRequest.setRequestOf(datasetRepository.findDatasetByTitleAndDescription(title, descrip1));
        datasetRequest.setRequestedIn(requestRepository.findByCreationDateAndAndDescription(date,descrip2));

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
        id = stepDefs.result.andReturn().getResponse().getHeader("Location");
        stepDefs.result = stepDefs.mockMvc.perform(
                get(id)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @And("It has not been created any DatasetRequest")
    public void itHasNotBeenCreatedAnyDatasetRequest() {
        Assert.assertEquals(0, datasetRequestRepository.count());
    }
}

