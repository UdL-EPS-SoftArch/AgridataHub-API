package cat.udl.eps.softarch.agridatahub.steps;

import cat.udl.eps.softarch.agridatahub.domain.Dataset;
import cat.udl.eps.softarch.agridatahub.repository.DatasetRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class UpdateDatasetStepDefs {
    final StepDefs stepDefs;
    final DatasetRepository datasetRepository;
    private String newResourceUri;

    public UpdateDatasetStepDefs(StepDefs stepDefs, DatasetRepository datasetRepository) {
        this.stepDefs = stepDefs;
        this.datasetRepository = datasetRepository;
    }

    @When("I change the title of the dataset with title {string} and description {string} to {string}")
    public void iChangeTheTitleOfTheDatasetWithTitleAndDescriptionTo(String title, String description, String newtitle) throws Throwable {
        Dataset dataset = datasetRepository.findDatasetByTitleAndDescription(title, description);
        stepDefs.result = stepDefs.mockMvc.perform(
                patch("/datasets/{id}", dataset == null ? 0 : dataset.getId())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content((new JSONObject().put("title", newtitle)).toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
        ).andDo(print());

        if (dataset == null) {
            newResourceUri = "/datasets/0";
        } else {
            newResourceUri = dataset.getUri();
        }
    }

    @And("The previously updated dataset has now title {string}")
    public void thePreviouslyUpdatedDatasetHasNowTitle(String newTitle) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.title", is(newTitle)));
    }

    @When("I change the description of the dataset with title {string} and description {string} to {string}")
    public void iChangeTheDescriptionOfTheDatasetWithTitleAndDescriptionTo(String title, String description, String newDescription) throws Throwable{
        Dataset dataset = datasetRepository.findDatasetByTitleAndDescription(title, description);
        newResourceUri = dataset.getUri();
        stepDefs.result = stepDefs.mockMvc.perform(
                patch("/datasets/{id}", dataset == null ? 0 : dataset.getId())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content((new JSONObject().put("description", newDescription)).toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
        ).andDo(print());
    }

    @And("The previously updated dataset has now description {string}")
    public void thePreviouslyUpdatedDatasetHasNowDescription(String newDescription) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.description", is(newDescription)));
    }
}
