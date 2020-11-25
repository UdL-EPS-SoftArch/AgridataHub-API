package cat.udl.eps.softarch.agridatahub.steps;

import cat.udl.eps.softarch.agridatahub.domain.Dataset;
import cat.udl.eps.softarch.agridatahub.repository.DatasetRepository;
import cat.udl.eps.softarch.agridatahub.repository.ProviderRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.WebApplicationContext;

import java.io.ByteArrayOutputStream;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;



public class UploadDatasetStepdefs {
    final StepDefs stepDefs;
    final DatasetRepository datasetRepository;
    final ProviderRepository providerRepository;
    final WebApplicationContext wac;

    public static String newResourceUri;
    public static Dataset datasetFile;

    public UploadDatasetStepdefs(StepDefs stepDefs, DatasetRepository datasetRepository, ProviderRepository providerRepository, WebApplicationContext wac) {
        this.stepDefs = stepDefs;
        this.datasetRepository = datasetRepository;
        this.providerRepository = providerRepository;
        this.wac = wac;
    }

    @When("I upload a dataset with title {string} and description {string} and contentType {string}")
    public void iUploadADatasetWithTitleAndDescriptionAndSeparator(String title, String description, String contentType) throws Exception {
        datasetFile = datasetRepository.findDatasetByTitleAndDescription(title, description);
        Resource file = wac.getResource("classpath:" + title);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        FileCopyUtils.copy(file.getInputStream(), output);
        datasetFile.setContent(output.toString());
        datasetFile.setContentType(contentType);

        String message = stepDefs.mapper.writeValueAsString(datasetFile);

        stepDefs.result = stepDefs.mockMvc.perform(
                patch("/datasets/{id}", datasetFile == null ? 0 : datasetFile.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(message)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

    }

    @Then("The dataset contains a file with title {string}")
    public void theDatasetContainsAFileWithFilename(String title) throws Exception {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/datasets/{id}", datasetFile == null ? 0 : datasetFile.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.title").value(title));
    }


    @And("The dataset content contains {string} content")
    public void theDatasetContentContainsContent(String title) throws Exception {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/datasets/{id}", datasetFile == null ? 0 : datasetFile.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.title", is(title)))
                .andExpect(jsonPath("$.content").value(datasetFile.getContent()));
    }

    @And("The dataset contentType is {string}")
    public void theDatasetSeparatorIs(String contentType) throws Exception {
        stepDefs.result.andExpect(jsonPath("$.contentType").value(contentType));
    }
}
