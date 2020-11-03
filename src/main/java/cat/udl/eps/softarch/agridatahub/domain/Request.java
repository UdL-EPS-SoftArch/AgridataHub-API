package cat.udl.eps.softarch.agridatahub.domain;


import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table
@Data
public class Request extends UriEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.DATE)
    @CreatedDate
    private Date creationDate;

    @Column(length = 500, unique = false)
    private String description;

    @ManyToOne(optional = false)
    @JsonIdentityReference(alwaysAsId = true)
    private Reuser requestedBy;

}
