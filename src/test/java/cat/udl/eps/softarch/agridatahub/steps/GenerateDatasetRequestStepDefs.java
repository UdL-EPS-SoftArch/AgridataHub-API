package cat.udl.eps.softarch.agridatahub.steps;

import cat.udl.eps.softarch.agridatahub.domain.DatasetRequest;
import cat.udl.eps.softarch.agridatahub.repository.DatasetRequestRepository;
import cat.udl.eps.softarch.agridatahub.repository.UserRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class GenerateDatasetRequestStepDefs {

    final StepDefs stepDefs;
    final UserRepository userRepository;
    final DatasetRequestRepository datasetRequestRepository;

    public GenerateDatasetRequestStepDefs(StepDefs stepDefs, UserRepository userRepository, DatasetRequestRepository datasetRequestRepository) {
        this.stepDefs = stepDefs;
        this.userRepository = userRepository;
        this.datasetRequestRepository = datasetRequestRepository;
    }


    @Given("There is registered user with username \"([^\"]*)\"$")
    public void thereIsRegisteredUserWithUsername(String user) {
        Assert.assertTrue("user \""
                        + user + "\"should exit",
                userRepository.existsById(user));
    }

    @When("I create a new DatasetRequest with value {string} and boolean {string}")
    public void iCreateANewDatasetRequestWithValueAndBoolean(Long id, Boolean granted) throws Throwable {

        DatasetRequest DatasetRequest = new DatasetRequest();
        DatasetRequest.setId(id);
        DatasetRequest.setGranted(granted);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/DatasetRequest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(DatasetRequest.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                    .andDo(print());
    }

    @And("It has been created a new DatasetRequest with value {string}")
    public void itHasBeenCreatedANewDatasetRequestWithValueAndBoolean(Long id) {
        Assert.assertTrue("DatasetRequest should exit ",
                datasetRequestRepository.existsById(id));
    }
}

