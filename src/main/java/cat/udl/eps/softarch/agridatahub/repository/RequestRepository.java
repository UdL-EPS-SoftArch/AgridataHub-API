package cat.udl.eps.softarch.agridatahub.repository;

import cat.udl.eps.softarch.agridatahub.domain.Request;
import com.sun.xml.bind.v2.runtime.property.StructureLoaderBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface RequestRepository extends PagingAndSortingRepository<Request, Long> {

    Request findByCreationDateAndAndDescription(@Param("creationDate") Date date, @Param("description") String description);
    Optional<Request> findById(Long id);

    @PreAuthorize("#requestedBy == authentication.name")
    Page<Request> findAll(Pageable pageable);

    List<Request> findRequestByCreationDate(@Param("creationDate") Date date);

    @PreAuthorize("#requestedBy == authentication.name")
    List<Request> findRequestByDescription(@Param("description") String description);

}
