package cat.udl.eps.softarch.agridatahub.steps;

import cat.udl.eps.softarch.agridatahub.domain.Dataset;
import cat.udl.eps.softarch.agridatahub.repository.DatasetRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.springframework.http.MediaType;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class RetrieveDatasetStepDefs {
    final StepDefs stepDefs;
    final DatasetRepository datasetRepository;

    public RetrieveDatasetStepDefs(StepDefs stepDefs, DatasetRepository datasetRepository) {
        this.stepDefs = stepDefs;
        this.datasetRepository = datasetRepository;
    }

    @Given("There is a created dataset with title {string} and description {string}")
    public void thereIsACreatedDatasetWithTitleAndDescription(String title, String description) {
        Dataset dataset = new Dataset();
        dataset.setTitle(title);
        dataset.setDescription(description);
        datasetRepository.save(dataset);
    }

    @When("I list all the existing datasets in the app")
    public void iListAllTheExistingDatasetsInTheApp() throws Throwable{
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/datasets")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("There has been retrieved {int} datasets")
    public void thereHasBeenRetrievedDatasets(int numDatasets) throws Throwable {
        stepDefs.result.andExpect(jsonPath("$._embedded.datasets", hasSize(numDatasets)));
    }

    @When("I request the dataset with title {string} and description {string}")
    public void iRequestTheDatasetWithTitleAndDescription(String title, String description) throws Throwable {
        Dataset dataset = datasetRepository.findDatasetByTitleAndDescription(title, description);
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/datasets/{id}", dataset == null ? 0 : dataset.getId())
                    .accept(MediaType.APPLICATION_JSON)
                    .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("It has been received the dataset with title {string} and description {string}")
    public void itHasBeenReceivedTheDatasetWithTitleAndDescription(String title, String description) throws Throwable {
        stepDefs.result.andExpect(jsonPath("$.title", is(title)))
                       .andExpect(jsonPath("$.description", is(description)));
    }

    @When("I search all the existing datasets in the app with text {string} in title or with text {string} in description")
    public void iSearchAllTheExistingDatasetsInTheAppWithTextInTitleOrWithTextInDescription(String title, String description) throws Exception {
        // List<Dataset> datasets = datasetRepository.findByTitleContainingOrDescriptionContaining(text);
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/datasets/search/findByTitleContainingOrDescriptionContaining?title={title}&description={description}", title, description)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @Given("There is a created dataset with text {string} in title {string} or description {string}")
    public void thereIsACreatedDatasetWithTextInTitleOrDescription(String text) {
        Dataset dataset = new Dataset();
        dataset.setDescription(text);
        datasetRepository.save(dataset);
    }

}
