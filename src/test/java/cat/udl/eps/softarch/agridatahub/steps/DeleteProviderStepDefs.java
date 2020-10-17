package cat.udl.eps.softarch.agridatahub.steps;

import cat.udl.eps.softarch.agridatahub.repository.ProviderRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeleteProviderStepDefs {
    final StepDefs stepDefs;
    final ProviderRepository providerRepository;

    DeleteProviderStepDefs(StepDefs stepDefs, ProviderRepository providerRepository) {
        this.stepDefs = stepDefs;
        this.providerRepository = providerRepository;
    }

    @When("I delete the provider with username {string}")
    public void iDeleteTheProviderWithWithUsername(String username) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                delete("/providers/{username}", username)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON)
                    .with(AuthenticationStepDefs.authenticate()))
                          .andDo(print());
    }

    @And("It does not exist a provider with username {string}")
    public void itDoesNotExistAProviderWithUsername(String username) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/providers/{username}", username)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .with(AuthenticationStepDefs.authenticate()))
                          .andDo(print())
                          .andExpect(status().isNotFound());
    }

    @And("It has not been deleted a provider with username {string}")
    public void itHasNotBeenDeletedAProviderWithUsername(String username) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/providers/{username}", username)
                    .accept(MediaType.APPLICATION_JSON)
                    .with(AuthenticationStepDefs.authenticate()))
                          .andDo(print())
                          .andExpect(jsonPath("$.id", is(username)));
    }
}
