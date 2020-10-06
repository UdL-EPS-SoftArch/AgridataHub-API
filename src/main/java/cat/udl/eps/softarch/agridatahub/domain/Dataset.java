package cat.udl.eps.softarch.agridatahub.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.time.ZonedDateTime;


@Entity
@Data
public class Dataset extends UriEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private ZonedDateTime createdAt;
}
