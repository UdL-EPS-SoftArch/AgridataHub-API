package cat.udl.eps.softarch.agridatahub.steps;

import cat.udl.eps.softarch.agridatahub.repository.ReuserRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class ListDataSetsStepDefs {
    final StepDefs stepDefs;
    final ReuserRepository ListDataSetsRepository;

    ListDataSetsStepDefs(StepDefs stepDefs, ReuserRepository ListDataSetsRepository) {
        this.stepDefs = stepDefs;
        this.ListDataSetsRepository = ListDataSetsRepository;
    }

    @Given("There is already a list called {string}")
    public void thereIsAlreadyAListCalled(String nameList) {
    }

    @When("I create a new list called {string}")
    public void iCreateANewListCalled(String nameList) {
    }

    @And("It asks if I want to overwrite the old list")
    public void itAsksIfIWantToOverwriteTheOldList() {
    }

    @Given("There is no list")
    public void thereIsNoList() {
        
    }

    @And("I cannot create new list with name {string}")
    public void iCannotCreateNewListWithName(String nameList) {
    }

}
