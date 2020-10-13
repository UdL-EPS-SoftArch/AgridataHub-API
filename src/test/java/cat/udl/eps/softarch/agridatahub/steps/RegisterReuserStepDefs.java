package cat.udl.eps.softarch.agridatahub.steps;

import cat.udl.eps.softarch.agridatahub.domain.Reuser;
import cat.udl.eps.softarch.agridatahub.domain.User;
import cat.udl.eps.softarch.agridatahub.repository.ReuserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RegisterReuserStepDefs {
    final StepDefs stepDefs;
    final ReuserRepository RegisterReuserStepDefs;

    RegisterReuserStepDefs(StepDefs stepDefs, ReuserRepository RegisterReuserStepDefs) {
        this.stepDefs = stepDefs;
        this.RegisterReuserStepDefs = RegisterReuserStepDefs;
    }

    @Given("There is no registered Reuser with username {string}")
    public void thereIsNoRegisteredReuserWithUsername(String username) {
        Assert.assertFalse("Reuser \""
                        +  username + "\"shouldn't exist",
                ReuserRepository.existsById(username));
    }

    @When("I register a new Reuser with username \"([^\\\"]*)\", email \"([^\\\"]*)\" and password \"([^\\\"]*)\\\"$\"")
    public void iRegisterANewReuserWithUsernameEmailAndPassword(String username, String email, String password)
            throws Throwable{
        Reuser reuser = new Reuser();
        reuser.setUsername(username);
        reuser.setEmail(email);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/reusers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new JSONObject(
                                stepDefs.mapper.writeValueAsString(reuser)
                        ).put("password", password).toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

    }

    @And("It has been created a Reuser with username \"([^\\\"]*)\" and email \"([^\\\"]*)\", the password is not returned")
    public void itHasBeenCreatedAReuserWithUsernameAndEmailThePasswordIsNotReturned(String username, String email)
            throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/reusers/{username}", username)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.email", is(email)))
                .andExpect(jsonPath("$.password").doesNotExist());
    }


    @When("I register a new Reuser with username, email and password")
    public void iRegisterANewReuserWithUsernameAndPassword(String username, String email, String password) throws Throwable {
        Reuser reuser = new Reuser();
        reuser.setUsername(username);
        reuser.setEmail(email);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/reusers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new JSONObject(
                                stepDefs.mapper.writeValueAsString(reuser)
                        ).put("password", password).toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }
}
