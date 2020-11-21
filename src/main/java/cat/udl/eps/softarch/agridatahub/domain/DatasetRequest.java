package cat.udl.eps.softarch.agridatahub.domain;


import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Data;

import javax.persistence.*;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class DatasetRequest extends UriEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private boolean granted = false;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private Request requestedIn;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private Dataset requestOf;

}
