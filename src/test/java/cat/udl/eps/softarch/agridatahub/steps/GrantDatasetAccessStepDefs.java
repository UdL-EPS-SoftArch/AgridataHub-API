package cat.udl.eps.softarch.agridatahub.steps;

import cat.udl.eps.softarch.agridatahub.repository.DatasetRequestRepository;
import cat.udl.eps.softarch.agridatahub.repository.UserRepository;
import cat.udl.eps.softarch.agridatahub.domain.DatasetRequest;
import cat.udl.eps.softarch.agridatahub.repository.DatasetRequestRepository;
import cat.udl.eps.softarch.agridatahub.repository.UserRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.mockito.internal.matchers.Null;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GrantDatasetAccessStepDefs {

    final StepDefs stepDefs;
    final UserRepository userRepository;
    final DatasetRequestRepository datasetRequestRepository;

    public GrantDatasetAccessStepDefs(StepDefs stepDefs, UserRepository userRepository, DatasetRequestRepository datasetRequestRepository) {
        this.stepDefs = stepDefs;
        this.userRepository = userRepository;
        this.datasetRequestRepository = datasetRequestRepository;
    }

    @And("It has been granted access to DatasetRequest with id {int}")
    public void itHasBeenGrantedAccessToDatasetRequestWithId(Long id) throws Exception {
        DatasetRequest datasetRequest = datasetRequestRepository.findDatasetRequestById(id);
        if (datasetRequest != null)
            datasetRequest.setGranted(true);
        assert datasetRequest != null;
        stepDefs.result = stepDefs.mockMvc.perform(
                patch("/datasetRequests/{id}", datasetRequest.getId())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content((new JSONObject().put("granted", datasetRequest.isGranted())).toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
        ).andDo(print());


    }


    @And("there is created a DatasetRequest with id {int}")
    public void thereIsCreatedADatasetRequestWithId(int arg0) {

    }


}
