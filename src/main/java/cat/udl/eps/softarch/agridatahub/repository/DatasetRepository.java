package cat.udl.eps.softarch.agridatahub.repository;

import cat.udl.eps.softarch.agridatahub.domain.CustomDataset;
import cat.udl.eps.softarch.agridatahub.domain.Dataset;
import cat.udl.eps.softarch.agridatahub.domain.Provider;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PostAuthorize;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(excerptProjection = CustomDataset.class)
public interface DatasetRepository extends PagingAndSortingRepository<Dataset, Long> {
    Dataset findDatasetByTitleAndDescription(@Param("title") String title, @Param("description") String description);
    List<Dataset> findByTitle(@Param("title") String title);
    List<Dataset> findByDescription(@Param("description") String description);
    List<Dataset> findByTitleContainingOrDescriptionContaining(@Param("title") String title,
                                                               @Param("description") String description);
    List<Dataset> findByProvidedBy(@Param("provider") Provider provider);

    @PostAuthorize("!returnObject.isPresent() || returnObject.get().providedBy.username == authentication.name")
    Optional<Dataset> findById(@Param("id") Long id);
}
