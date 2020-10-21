package cat.udl.eps.softarch.agridatahub.steps;

import cat.udl.eps.softarch.agridatahub.repository.DatasetRequestRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeleteDatasetRequestStepDefs {
    final StepDefs stepDefs;
    final DatasetRequestRepository datasetRequestRepository;

    DeleteDatasetRequestStepDefs(StepDefs stepDefs, DatasetRequestRepository datasetRequestRepository) {
        this.stepDefs = stepDefs;
        this.datasetRequestRepository = datasetRequestRepository;
    }

    @When("I delete a DatasetRequest")
    public void iDeleteTheDatasetRequestWithValue() throws Exception {
        String id = stepDefs.result.andReturn().getResponse().getHeader("Location");
        stepDefs.result = stepDefs.mockMvc.perform(
                delete("/datasetRequests/{id}",id)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("It does not exist the DatasetRequest.")
    public void itDoesNotExistTheDatasetRequest() throws Exception {
        String id = stepDefs.result.andReturn().getResponse().getHeader("Location");
        stepDefs.result = stepDefs.mockMvc.perform(
                get(id)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @And("It has not deleted the DatasetRequest.")
    public void itHasNotDeletedTheDatasetRequest() throws Exception {
        String id = stepDefs.result.andReturn().getResponse().getHeader("Location");
        stepDefs.result = stepDefs.mockMvc.perform(
                get(id)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
