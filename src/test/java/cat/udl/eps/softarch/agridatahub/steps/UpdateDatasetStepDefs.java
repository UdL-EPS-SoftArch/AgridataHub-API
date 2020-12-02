package cat.udl.eps.softarch.agridatahub.steps;

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

    @When("I change the title of the dataset with id {string} to {string}")
    public void iChangeTheTitleOfTheDatasetIdTo(String id, String newTitle) throws Throwable {
        newResourceUri = "/datasets/"+ id;
        stepDefs.result = stepDefs.mockMvc.perform(
                patch(newResourceUri)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content((new JSONObject().put("title", newTitle)).toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
        ).andDo(print());
    }

    @When("I change the description of the dataset with id {string} to {string}")
    public void iChangeTheDescriptionOfTheDatasetWithIdTo(String id, String newDescription) throws Throwable{
        newResourceUri = "/datasets/"+ id;
        stepDefs.result = stepDefs.mockMvc.perform(
                patch(newResourceUri)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content((new JSONObject().put("description", newDescription)).toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
        ).andDo(print());
    }

    @And("The previously updated dataset has now title {string}")
    public void thePreviouslyUpdatedDatasetHasNowTitle(String newTitle) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.title", is(newTitle)));
    }

    @And("The previously updated dataset has now description {string}")
    public void thePreviouslyUpdatedDatasetHasNowDescription(String newDescription) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.description", is(newDescription)));
    }

}
