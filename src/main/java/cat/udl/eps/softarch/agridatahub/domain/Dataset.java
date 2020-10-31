package cat.udl.eps.softarch.agridatahub.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.time.ZonedDateTime;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Dataset extends UriEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Length(min = 1, max = 80)
    private String title;

    @Length(max = 256)
    private String description;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private Provider providedBy;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime createdAt;
}
