package cat.udl.eps.softarch.agridatahub.steps;

import cat.udl.eps.softarch.agridatahub.domain.Dataset;
import cat.udl.eps.softarch.agridatahub.domain.DatasetRequest;
import cat.udl.eps.softarch.agridatahub.repository.DatasetRepository;
import cat.udl.eps.softarch.agridatahub.repository.DatasetRequestRepository;
import cat.udl.eps.softarch.agridatahub.repository.ProviderRepository;
import cat.udl.eps.softarch.agridatahub.repository.RequestRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.WebApplicationContext;

import java.io.ByteArrayOutputStream;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DownloadDatasetStepDefs {

    final StepDefs stepDefs;
    final DatasetRepository datasetRepository;
    final ProviderRepository providerRepository;
    final DatasetRequestRepository datasetRequestRepository;
    final WebApplicationContext wac;

    public static Dataset datasetFile;
    public static DatasetRequest datasetRequest;
    public static String id;

    public DownloadDatasetStepDefs(StepDefs stepDefs, DatasetRepository datasetRepository, ProviderRepository providerRepository, DatasetRequestRepository datasetRequestRepository, WebApplicationContext wac) {
        this.stepDefs = stepDefs;
        this.datasetRepository = datasetRepository;
        this.providerRepository = providerRepository;
        this.datasetRequestRepository = datasetRequestRepository;
        this.wac = wac;
    }

    @When("I download dataset file with title {string} and description {string} and contentType {string}")
    public void iDownloadDatasetFileWithTitleAndDescriptionAndContentType(String title, String description, String contentType) throws Exception {
        datasetFile = datasetRepository.findDatasetByTitleAndDescription(title, description);
        Resource file = wac.getResource("classpath:" + title);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        FileCopyUtils.copy(file.getInputStream(), output);
        Assert.assertEquals(output.toString(), datasetFile.getContent());
    }


    @And("It has been download dataset file with DatasetRequest associate with Dataset {string} and {string} and Request {string} and status value {string}")
    public void itHasBeenDownloadDatasetFileWithDatasetRequestAssociateWithDatasetAndAndRequestWithIdAndStatusValue(String title, String description1, String description2, String granted) throws Exception {
        datasetFile = datasetRepository.findDatasetByTitleAndDescription(title, description1);
        datasetRequest = datasetRequestRepository.findByGrantedAndRequestOf(Boolean.parseBoolean(granted),datasetFile);
        id = stepDefs.result.andReturn().getResponse().getHeader("Location");
        stepDefs.result = stepDefs.mockMvc.perform(
                get(id)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @And("It has not been download dataset file")
    public void itHasNotBeenDownloadDatasetFile() {
        Assert.assertEquals(0, datasetRequestRepository.count());
    }
}
