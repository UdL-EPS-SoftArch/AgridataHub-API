package cat.udl.eps.softarch.agridatahub.steps;

import cat.udl.eps.softarch.agridatahub.repository.ProviderRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class RetrieveProviderStepDefs {
    final StepDefs stepDefs;
    final ProviderRepository providerRepository;

    RetrieveProviderStepDefs(StepDefs stepDefs, ProviderRepository providerRepository) {
        this.stepDefs = stepDefs;
        this.providerRepository = providerRepository;
    }

    @When("I list all the existing providers in the app")
    public void iListAllTheExistingProvidersInTheApp() throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/providers")
                    .accept(MediaType.APPLICATION_JSON)
                    .with(AuthenticationStepDefs.authenticate()))
                          .andDo(print());
    }

    @And("There has been retrieved {int} providers")
    public void thereHasBeenRetrievedProviders(int numProviders) throws Throwable {
        stepDefs.result.andExpect(jsonPath("$._embedded.providers", hasSize(numProviders)));
    }
}
