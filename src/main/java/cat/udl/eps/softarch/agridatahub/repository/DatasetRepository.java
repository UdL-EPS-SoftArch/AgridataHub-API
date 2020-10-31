package cat.udl.eps.softarch.agridatahub.repository;

import cat.udl.eps.softarch.agridatahub.domain.Dataset;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
@RepositoryRestResource
public interface DatasetRepository extends PagingAndSortingRepository<Dataset, Long> {
    Dataset findDatasetByTitleAndDescription(@Param("title") String title, @Param("description") String description);
    List<Dataset> findByTitle(@Param("title") String title);
    List<Dataset> findByDescription(@Param("description") String description);
    List<Dataset> findByTitleContainingOrDescriptionContaining(@Param("title") String title,
                                                               @Param("description") String description);
}
