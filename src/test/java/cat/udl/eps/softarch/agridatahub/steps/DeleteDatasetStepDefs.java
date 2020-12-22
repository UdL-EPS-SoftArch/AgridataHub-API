package cat.udl.eps.softarch.agridatahub.steps;

import cat.udl.eps.softarch.agridatahub.repository.DatasetRepository;
import io.cucumber.java.en.When;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class DeleteDatasetStepDefs {
    final StepDefs stepDefs;
    final DatasetRepository datasetRepository;

    public DeleteDatasetStepDefs(StepDefs stepDefs, DatasetRepository datasetRepository) {
        this.stepDefs = stepDefs;
        this.datasetRepository = datasetRepository;
    }

    @When("I delete the dataset with id {string}")
    public void iDeleteTheDatasetWithId(String id) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                delete("/datasets/"+ id)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }
}
