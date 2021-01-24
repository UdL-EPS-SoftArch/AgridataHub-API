package cat.udl.eps.softarch.agridatahub.steps;

import cat.udl.eps.softarch.agridatahub.domain.Dataset;
import cat.udl.eps.softarch.agridatahub.repository.DatasetRepository;
import cat.udl.eps.softarch.agridatahub.repository.ProviderRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.WebApplicationContext;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class DownloadDatasetStepDefs {

    final StepDefs stepDefs;
    final DatasetRepository datasetRepository;
    final ProviderRepository providerRepository;
    final WebApplicationContext wac;

    public static Dataset datasetFile;
    ByteArrayOutputStream output = new ByteArrayOutputStream();

    public DownloadDatasetStepDefs(StepDefs stepDefs, DatasetRepository datasetRepository, ProviderRepository providerRepository, WebApplicationContext wac) {
        this.stepDefs = stepDefs;
        this.datasetRepository = datasetRepository;
        this.providerRepository = providerRepository;
        this.wac = wac;
    }

    @When("I download dataset file with title {string} and description {string} and contentType {string}")
    public void iDownloadDatasetFileWithTitleAndDescriptionAndContentType(String title, String description, String contentType) throws Exception {
        datasetFile = datasetRepository.findDatasetByTitleAndDescription(title, description);
        Resource file = wac.getResource("classpath:" + title);
        FileCopyUtils.copy(file.getInputStream(), output);


        stepDefs.result = stepDefs.mockMvc.perform(
                get("/datasets/{id}", datasetFile == null ? 0 : datasetFile.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.content").value(output.toString()));
    }

    @And("It has been download dataset file with title {string} and description {string} and contentType {string}")
    public void itHasBeenDownloadDatasetFileWithTitleAndDescriptionAndContentType(String title, String description, String contentType) throws Exception {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/datasets/{id}", datasetFile == null ? 0 : datasetFile.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.title", is(title)))
                .andExpect(jsonPath("$.description", is(description)))
                .andExpect(jsonPath("$.content").value(output.toString()))
                .andExpect(jsonPath("$.contentType").value(contentType));
    }
}
