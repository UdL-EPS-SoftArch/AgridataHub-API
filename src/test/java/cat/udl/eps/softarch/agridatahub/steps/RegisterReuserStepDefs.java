package cat.udl.eps.softarch.agridatahub.steps;

import cat.udl.eps.softarch.agridatahub.domain.Reuser;
import cat.udl.eps.softarch.agridatahub.domain.User;
import cat.udl.eps.softarch.agridatahub.repository.ReuserRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
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
    final ReuserRepository reuserRepository;

    RegisterReuserStepDefs(StepDefs stepDefs, ReuserRepository reuserRepository) {
        this.stepDefs = stepDefs;
        this.reuserRepository = reuserRepository;
    }

    @Given("There is no registered reuser with username {string}")
    public void thereIsNoRegisteredReuserWithUsername(String username) {
        Assert.assertTrue("Reuser \""
                        +  username + "\"shouldn't exist",
                reuserRepository.findByUsernameContaining(username).isEmpty());
    }

    @When("^I register a new reuser with username \"([^\"]*)\", email \"([^\"]*)\" and password \"([^\"]*)\"$")
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

    @And("It has been created a reuser with username \"([^\"]*)\" and email \"([^\"]*)\", the password is not returned$")
    public void itHasBeenCreatedAReuserWithUsername(String username, String email) throws Throwable{
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/reusers/{username}", username)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.email", is(email)))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Given("There is a registered reuser with username {string} and password {string} and email {string}")
    public void thereIsARegisteredReuserWithUsernameAndPasswordAndEmail(String username, String password, String email) {
        if (reuserRepository.findByUsernameContaining(username).isEmpty()) {
            Reuser reuser = new Reuser();
            reuser.setEmail(email);
            reuser.setUsername(username);
            reuser.setPassword(password);
            reuser.encodePassword();
            reuserRepository.save(reuser);
        }
    }
    @And("It has not been created a reuser with username {string}")
    public void itHasNotBeenCreatedAReuserWithUsername(String username) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/reusers/{username}", username)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andExpect(status().isNotFound());
    }


}
