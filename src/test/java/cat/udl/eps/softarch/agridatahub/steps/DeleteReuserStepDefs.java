package cat.udl.eps.softarch.agridatahub.steps;

import cat.udl.eps.softarch.agridatahub.repository.ReuserRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeleteReuserStepDefs {
    final StepDefs stepDefs;
    final ReuserRepository reuserRepository;

    DeleteReuserStepDefs(StepDefs stepDefs, ReuserRepository reuserRepository) {
        this.stepDefs = stepDefs;
        this.reuserRepository = reuserRepository;
    }

    @When("I delete the reuser with username {string}")
    public void iDeleteTheReuserWithWithUsername(String username) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                delete("/reusers/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("It does not exist a reuser with username {string}")
    public void itDoesNotExistAReuserWithUsername(String username) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/reusers/{username}", username)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @And("It has not been deleted a reuser with username {string}")
    public void itHasNotBeenDeletedAReuserWithUsername(String username) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/reusers/{username}", username)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.id", is(username)));
    }
}
