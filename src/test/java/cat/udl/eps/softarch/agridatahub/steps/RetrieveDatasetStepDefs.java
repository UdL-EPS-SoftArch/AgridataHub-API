package cat.udl.eps.softarch.agridatahub.steps;

import cat.udl.eps.softarch.agridatahub.domain.Dataset;
import cat.udl.eps.softarch.agridatahub.domain.Provider;
import cat.udl.eps.softarch.agridatahub.repository.DatasetRepository;
import cat.udl.eps.softarch.agridatahub.repository.ProviderRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class RetrieveDatasetStepDefs {
    final StepDefs stepDefs;
    final DatasetRepository datasetRepository;
    final ProviderRepository providerRepository;

    public RetrieveDatasetStepDefs(StepDefs stepDefs, DatasetRepository datasetRepository, ProviderRepository providerRepository) {
        this.stepDefs = stepDefs;
        this.datasetRepository = datasetRepository;
        this.providerRepository = providerRepository;
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

    @When("I search all the existing datasets in the app containing text {string} in title or containing text {string} in description")
    public void iSearchAllTheExistingDatasetsInTheAppContainingTextInTitleOrContainingTextInDescription(String title, String description) throws Exception {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/datasets/search/findByTitleContainingOrDescriptionContaining?title={title}&description={description}", title, description)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @When("I list all my datasets")
    public void iListAllMyDatasets() throws Throwable {
        String currUsername = AuthenticationStepDefs.currentUsername;
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/datasets/search/findByProvidedBy?provider=/providers/"+currUsername)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @When("I request the dataset with id {string}")
    public void iRequestTheDatasetWithId(String id) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/datasets/"+ id)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @Given("There is a created dataset with title {string} and description {string} and provided by {string}")
    public void thereIsACreatedDatasetWithTitleAndDescriptionAndProvidedBy(String title, String description, String username) {
        Dataset dataset = new Dataset();
        dataset.setTitle(title);
        dataset.setDescription(description);
        dataset.setProvidedBy(providerRepository.findById(username).get());
        datasetRepository.save(dataset);
    }
}
