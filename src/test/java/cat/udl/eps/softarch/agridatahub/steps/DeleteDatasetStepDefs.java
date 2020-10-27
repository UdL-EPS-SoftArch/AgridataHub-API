package cat.udl.eps.softarch.agridatahub.steps;

import cat.udl.eps.softarch.agridatahub.domain.Dataset;
import cat.udl.eps.softarch.agridatahub.repository.DatasetRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeleteDatasetStepDefs {
    final StepDefs stepDefs;
    final DatasetRepository datasetRepository;

    public DeleteDatasetStepDefs(StepDefs stepDefs, DatasetRepository datasetRepository) {
        this.stepDefs = stepDefs;
        this.datasetRepository = datasetRepository;
    }

    @When("I delete the dataset with title {string} and description {string}")
    public void iDeleteTheDatasetWithTitleAndDescription(String title, String description) throws Throwable {
        Dataset dataset = datasetRepository.findDatasetByTitleAndDescription(title, description);
        stepDefs.result = stepDefs.mockMvc.perform(
                delete("/datasets/{id}", dataset == null ? 0 : dataset.getId())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("It has not been deleted a dataset with title {string} and description {string}")
    public void itHasNotBeenDeletedADatasetWithTitleAndDescription(String title, String description) throws Throwable {
        Dataset dataset = datasetRepository.findDatasetByTitleAndDescription(title, description);
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/datasets/{id}", dataset == null ? 0 : dataset.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.title", is(title)))
                .andExpect(jsonPath("$.description", is(description)));
    }

    @And("It does not exist a dataset with title {string} and description {string}")
    public void itDoesNotExistADatasetWithTitleAndDescription(String title, String description) throws Throwable{
        Dataset dataset = datasetRepository.findDatasetByTitleAndDescription(title, description);
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/datasets/{id}", dataset == null ? 0 : dataset.getId())
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
