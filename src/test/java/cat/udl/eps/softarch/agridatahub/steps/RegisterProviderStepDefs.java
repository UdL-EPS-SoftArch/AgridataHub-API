package cat.udl.eps.softarch.agridatahub.steps;

import cat.udl.eps.softarch.agridatahub.domain.Provider;
import cat.udl.eps.softarch.agridatahub.domain.User;
import cat.udl.eps.softarch.agridatahub.repository.ProviderRepository;
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

public class RegisterProviderStepDefs {
    final StepDefs stepDefs;
    final ProviderRepository providerRepository;

    RegisterProviderStepDefs(StepDefs stepDefs, ProviderRepository providerRepository) {
        this.stepDefs = stepDefs;
        this.providerRepository = providerRepository;
    }

    @Given("There is no registered provider with username {string}")
    public void thereIsNoRegisteredProviderWithUsername(String username) {
        Assert.assertFalse("Provider \""
                        +  username + "\"shouldn't exist",
                providerRepository.existsById(username));
    }

    @When("I register a new provider with username {string}, email {string} and password {string}")
    public void iRegisterANewProviderWithUsernameEmailAndPassword(String username, String email, String password) throws Throwable {
        Provider provider = new Provider();
        provider.setUsername(username);
        provider.setEmail(email);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/providers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new JSONObject(
                                stepDefs.mapper.writeValueAsString(provider)
                        ).put("password", password).toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("It has been created a provider with username {string} and email {string}, the password is not returned")
    public void itHasBeenCreatedAProviderWithUsername(String username, String email) throws Throwable{
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/providers/{username}", username)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.email", is(email)))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Given("There is a registered provider with username {string} and password {string} and email {string}")
    public void thereIsARegisteredProviderWithUsernameAndPasswordAndEmail(String username, String password, String email) {
        if (!providerRepository.existsById(username)) {
            Provider provider = new Provider();
            provider.setEmail(email);
            provider.setUsername(username);
            provider.setPassword(password);
            provider.encodePassword();
            providerRepository.save(provider);
        }
    }

    @And("It has not been created a provider with username {string}")
    public void itHasNotBeenCreatedAProviderWithUsername(String username) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/providers/{username}", username)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andExpect(status().isNotFound());
    }
}
