package cat.udl.eps.softarch.agridatahub.steps;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import cat.udl.eps.softarch.agridatahub.domain.Request;
import cat.udl.eps.softarch.agridatahub.repository.RequestRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.http.MediaType;
import org.springframework.test.util.AssertionErrors;

import java.util.Date;
import java.util.List;

public class DeleteRequestStepDefs {

    final StepDefs stepDefs;
    final RequestRepository requestRepository;
    private Request requestGlobal;


    public DeleteRequestStepDefs(StepDefs stepDefs, RequestRepository requestRepository) {
        this.stepDefs = stepDefs;
        this.requestRepository = requestRepository;
    }
    

    @And("Exists a Request with description {string}")
    public void existsARequestWithDescription(String description) {
        Request request = new Request();
        request.setDescription(description);
        requestGlobal = requestRepository.save(request);
    }



    @And("It does not exist a request with description {string}")
    public void itDoesNotExistARequestWithDescription(String descrip) {
        Assert.assertTrue("Request with \""
                +  descrip + "\" description shouldn't exist", requestRepository.findRequestByDescription(descrip).isEmpty());

    }

    @When("I delete the previously created Request")
    public void iDeleteThePreviouslyCreatedRequest() throws  Throwable {

        stepDefs.result = stepDefs.mockMvc.perform(
                delete(requestGlobal.getUri())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @When("I delete a request")
    public void iDeleteARequest() {
    }
}
