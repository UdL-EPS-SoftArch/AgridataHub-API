package cat.udl.eps.softarch.agridatahub.domain;

import org.springframework.data.rest.core.config.Projection;

import java.time.ZonedDateTime;

@Projection(
    name = "customDataset",
    types = {Dataset.class})
public interface CustomDataset {
    String getTitle();
    String getDescription();
    Provider getProvidedBy();
    ZonedDateTime getCreatedAt();
}
