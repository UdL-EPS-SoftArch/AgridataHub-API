package cat.udl.eps.softarch.agridatahub.controller;

import cat.udl.eps.softarch.agridatahub.domain.Dataset;
import cat.udl.eps.softarch.agridatahub.domain.User;
import cat.udl.eps.softarch.agridatahub.exception.ForbiddenException;
import cat.udl.eps.softarch.agridatahub.exception.NotAuthorizedException;
import cat.udl.eps.softarch.agridatahub.exception.NotFoundException;
import cat.udl.eps.softarch.agridatahub.repository.DatasetRepository;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@RepositoryRestController
public class DatasetController {

    private final DatasetRepository datasetRepository;

    public DatasetController(DatasetRepository datasetRepository) {
        this.datasetRepository = datasetRepository;
    }

    @RequestMapping(value = "/datasets/{id}", method = RequestMethod.GET)
    public @ResponseBody
    PersistentEntityResource getDataset(PersistentEntityResourceAssembler resourceAssembler,
                                        @PathVariable Long id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            throw new NotAuthorizedException();
        }

        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Dataset> dataset = datasetRepository.findById(id);

        if (dataset.isEmpty()) {
            throw new NotFoundException();
        }

        Dataset returnedDataset = dataset.get();

        if (returnedDataset.getProvidedBy().getId().equals(currentUser.getId())) {
            return resourceAssembler.toFullResource(returnedDataset);
        } else {
            throw new ForbiddenException();
        }

    }

}
