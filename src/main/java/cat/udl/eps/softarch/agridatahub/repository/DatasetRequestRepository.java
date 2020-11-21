package cat.udl.eps.softarch.agridatahub.repository;

import cat.udl.eps.softarch.agridatahub.domain.Dataset;
import cat.udl.eps.softarch.agridatahub.domain.DatasetRequest;
import cat.udl.eps.softarch.agridatahub.domain.Request;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface DatasetRequestRepository extends PagingAndSortingRepository<DatasetRequest, Long>{
    /* Interface provides automatically, as defined in https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/PagingAndSortingRepository.html
     * count, delete, deleteAll, deleteById, existsById, findAll, findAllById, findById, save, saveAll
     *
     * Additional methods following the syntax defined in
     * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
     */
    DatasetRequest findByGrantedAndRequestOf(@Param("granted") Boolean granted, @Param("requestOf") Dataset requestOf);
    DatasetRequest findByGrantedAndRequestedInEquals(@Param("granted") Boolean granted, @Param("requestedIn") Request requestedIn);
    List<DatasetRequest> findByGranted(@Param("granted") Boolean granted);
    Optional<DatasetRequest> findById(@Param("id") Long id);
}
