package cat.udl.eps.softarch.agridatahub.steps;

import cat.udl.eps.softarch.agridatahub.domain.Dataset;
import cat.udl.eps.softarch.agridatahub.repository.DatasetRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class CreateDatasetStepDefs {
    final StepDefs stepDefs;
    final DatasetRepository datasetRepository;
    private String newResourceUri;

    public CreateDatasetStepDefs(StepDefs stepDefs, DatasetRepository datasetRepository) {
        this.stepDefs = stepDefs;
        this.datasetRepository = datasetRepository;
    }

    @When("I create a new dataset with title {string} and description {string}")
    public void iCreateANewDatasetWithTitleAndDescription(String title, String description) throws Throwable {
        Dataset dataset = new Dataset();
        dataset.setTitle(title);
        dataset.setDescription(description);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/datasets")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(stepDefs.mapper.writeValueAsString(dataset))
                    .accept(MediaType.APPLICATION_JSON)
                    .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("It has been created a dataset with title {string} and description {string}")
    public void itHasBeenCreatedADatasetWithTitleAndDescription(String title, String description) throws Throwable {
        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                    .accept(MediaType.APPLICATION_JSON)
                    .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.title", is(title)))
                .andExpect(jsonPath("$.description", is(description)));
    }

    @And("It has not been created any dataset")
    public void itHasNotBeenCreatedAnyDataset() {
        Assert.assertTrue(datasetRepository.count()==0);
    }
}
