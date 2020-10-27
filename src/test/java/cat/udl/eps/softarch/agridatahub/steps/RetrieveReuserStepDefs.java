package cat.udl.eps.softarch.agridatahub.steps;

import cat.udl.eps.softarch.agridatahub.repository.ReuserRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class RetrieveReuserStepDefs {
    final StepDefs stepDefs;
    final ReuserRepository reuserRepository;

    RetrieveReuserStepDefs(StepDefs stepDefs, ReuserRepository reuserRepository) {
        this.stepDefs = stepDefs;
        this.reuserRepository = reuserRepository;
    }

    @When("I list all the existing reusers in the app")
    public void iListAllTheExistingReusersInTheApp() throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/reusers")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("There has been retrieved {int} reusers")
    public void thereHasBeenRetrievedReusers(int numReusers) throws Throwable {
        stepDefs.result.andExpect(jsonPath("$._embedded.reusers", hasSize(numReusers)));
    }

    @When("I request the reuser with username {string}")
    public void iRequestTheReuserWithUsername(String username) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/reusers/{username}", username)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("It has been received the reuser with username {string}")
    public void itHasBeenReceivedTheReuserWithUsername(String username) throws Throwable {
        stepDefs.result.andExpect(jsonPath("$.id", is(username)));
    }
}
