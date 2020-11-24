package cat.udl.eps.softarch.agridatahub.repository;

import cat.udl.eps.softarch.agridatahub.domain.Provider;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProviderRepository extends PagingAndSortingRepository<Provider, String> {

    List<Provider> findByUsernameContaining(@Param("text") String text);
    Provider findByUsernameAndPassword(@Param("username") String username,@Param("password") String password);
}