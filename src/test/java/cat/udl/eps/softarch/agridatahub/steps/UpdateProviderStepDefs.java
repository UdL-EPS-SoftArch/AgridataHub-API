package cat.udl.eps.softarch.agridatahub.steps;

import cat.udl.eps.softarch.agridatahub.repository.ProviderRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class UpdateProviderStepDefs {
    final StepDefs stepDefs;
    final ProviderRepository providerRepository;

    UpdateProviderStepDefs(StepDefs stepDefs, ProviderRepository providerRepository) {
        this.stepDefs = stepDefs;
        this.providerRepository = providerRepository;
    }

    @When("I change the email of provider {string} to {string}")
    public void iChangeTheEmailOfProviderTo(String username, String email) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                patch("/providers/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content((new JSONObject().put("email", email)).toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
        ).andDo(print());
    }

    @And("It has been updated the email of provider {string} to {string}")
    public void itHasBeenUpdatedTheEmailOfProviderTo(String username, String email) throws Throwable{
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/providers/{username}", username)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.email", is(email)));
    }

    @And("Email of provider {string} is still {string}")
    public void emailOfProviderIsStill(String username, String email) throws Throwable {
        itHasBeenUpdatedTheEmailOfProviderTo(username, email);
    }
}
