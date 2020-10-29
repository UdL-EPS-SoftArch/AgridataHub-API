package cat.udl.eps.softarch.agridatahub.steps;

import cat.udl.eps.softarch.agridatahub.domain.DatasetRequest;
import cat.udl.eps.softarch.agridatahub.repository.DatasetRepository;
import cat.udl.eps.softarch.agridatahub.repository.DatasetRequestRepository;
import cat.udl.eps.softarch.agridatahub.repository.RequestRepository;
import cat.udl.eps.softarch.agridatahub.repository.UserRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;

import org.springframework.http.MediaType;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeleteDatasetRequestStepDefs {
    final StepDefs stepDefs;
    final UserRepository userRepository;
    final DatasetRequestRepository datasetRequestRepository;
    final DatasetRepository datasetRepository;
    final RequestRepository requestRepository;

    public static Long id;
    public static Date date;
    public static DatasetRequest datasetRequest;

    DeleteDatasetRequestStepDefs(StepDefs stepDefs, UserRepository userRepository, DatasetRequestRepository datasetRequestRepository, DatasetRepository datasetRepository, RequestRepository requestRepository) {
        this.stepDefs = stepDefs;
        this.userRepository = userRepository;
        this.datasetRequestRepository = datasetRequestRepository;
        this.datasetRepository = datasetRepository;
        this.requestRepository = requestRepository;
    }

    @When("I delete a DatasetRequest with status value {string} and associate request {string}")
    public void iDeleteADatasetRequestWithStatusValueAndAssociateRequest(String granted, String description) throws Exception {
        datasetRequest = datasetRequestRepository.findByGrantedAndRequestedIn(Boolean.parseBoolean(granted),
                requestRepository.findByCreationDateAndAndDescription(date,description));
        stepDefs.result = stepDefs.mockMvc.perform(
                delete("/datasets/{id}", datasetRequest == null ? 0 : datasetRequest.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }
    @When("I delete a DatasetRequest with status value {string} and associate dataset {string} and {string}")
    public void iDeleteADatasetRequestWithStatusValueAndAssociateDatasetAnd(String granted, String title, String description) throws Exception {
        datasetRequest = datasetRequestRepository.findByGrantedAndRequestOf(Boolean.parseBoolean(granted),
                datasetRepository.findDatasetByTitleAndDescription(title,description));
        stepDefs.result = stepDefs.mockMvc.perform(
                delete("/datasets/{id}", datasetRequest == null ? 0 : datasetRequest.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("It does not exist the DatasetRequest.")
    public void itDoesNotExistTheDatasetRequest() throws Exception {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/datasetRequests/{id}",datasetRequest == null ? 0 : datasetRequest.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }



}
