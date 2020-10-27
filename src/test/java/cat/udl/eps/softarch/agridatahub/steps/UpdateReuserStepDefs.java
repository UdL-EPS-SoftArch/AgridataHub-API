package cat.udl.eps.softarch.agridatahub.steps;

import cat.udl.eps.softarch.agridatahub.repository.ReuserRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class UpdateReuserStepDefs {
    final StepDefs stepDefs;
    final ReuserRepository reuserRepository;

    UpdateReuserStepDefs(StepDefs stepDefs, ReuserRepository reuserRepository) {
        this.stepDefs = stepDefs;
        this.reuserRepository = reuserRepository;
    }

    @When("I change the email of reuser {string} to {string}")
    public void iChangeTheEmailOfReuserTo(String username, String email) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                patch("/reusers/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content((new JSONObject().put("email", email)).toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
        ).andDo(print());
    }

    @And("It has been updated the email of reuser {string} to {string}")
    public void itHasBeenUpdatedTheEmailOfReuserTo(String username, String email) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/reusers/{username}", username)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.email", is(email)));
    }

    @And("Email of reuser {string} is still {string}")
    public void emailOfReuserIsStill(String username, String email) throws Throwable {
        itHasBeenUpdatedTheEmailOfReuserTo(username, email);
    }
}
