package cat.udl.eps.softarch.agridatahub.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.time.ZonedDateTime;

@Projection(
    name = "customDataset",
    types = {Dataset.class})
public interface CustomDataset {
    Long getId();
    String getTitle();
    String getDescription();
    @Value("#{target.providedBy.username}")
    String getProvidedBy();
    ZonedDateTime getCreatedAt();
    String getUri();
}
