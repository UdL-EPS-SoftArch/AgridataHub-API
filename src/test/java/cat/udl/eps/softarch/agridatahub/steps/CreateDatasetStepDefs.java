package cat.udl.eps.softarch.agridatahub.steps;

import cat.udl.eps.softarch.agridatahub.domain.Dataset;
import cat.udl.eps.softarch.agridatahub.domain.Provider;
import cat.udl.eps.softarch.agridatahub.repository.DatasetRepository;
import cat.udl.eps.softarch.agridatahub.repository.UserRepository;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.http.MediaType;


import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.temporal.ChronoUnit;

import static cat.udl.eps.softarch.agridatahub.steps.AuthenticationStepDefs.authenticate;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CreateDatasetStepDefs {
    private StepDefs stepDefs;
    private DatasetRepository datasetRepository;
    private UserRepository userRepository;
    //private ProviderRepository providerRepository;

    public CreateDatasetStepDefs(StepDefs stepDefs, DatasetRepository datasetRepository, UserRepository userRepository) {
        this.stepDefs = stepDefs;
        this.datasetRepository = datasetRepository;
        this.userRepository = userRepository;
    }

    @And("There are {int} datasets created")
    public void thereAreDatasetsCreated(int count) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(get("/datasets")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.datasets", hasSize(count)));
    }

    @When("I create a dataset with title {string}")
    public void iCreateADatasetWithTitle(String title) throws Throwable{
        Dataset dataset = new Dataset();
        dataset.setTitle(title);
        String message = stepDefs.mapper.writeValueAsString(dataset);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/datasets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(message)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(authenticate()))
                .andDo(print());
    }

    @Then("The new dataset has title {string}")
    public void theNewDatasetHasTitle(String title) throws Throwable {
        stepDefs.result.andExpect(jsonPath("$.title", is(title)));
    }

}
