package cat.udl.eps.softarch.agridatahub.repository;

import cat.udl.eps.softarch.agridatahub.domain.Reuser;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ReuserRepository extends PagingAndSortingRepository<Reuser, String> {
    List<Reuser> findByUsernameContaining(@Param("text") String text);
}
