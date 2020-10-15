package cat.udl.eps.softarch.agridatahub.repository;

import cat.udl.eps.softarch.agridatahub.domain.Request;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface RequestRepository extends PagingAndSortingRepository<Request, Long> {

    Optional<Request> findById(Long id);
    List<Request> findRequestByCreationDate(@Param("creationDate") Date date);
    List<Request> findRequestByDescription(@Param("description") String description);
}
