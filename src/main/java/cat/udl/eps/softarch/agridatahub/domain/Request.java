package cat.udl.eps.softarch.agridatahub.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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
    private Date creationDate;

    @Column(length = 500, unique = false)
    private String description;
    /*
    @ManyToOne(optional = false)
    private Reuser requestedBy;
    */
}
