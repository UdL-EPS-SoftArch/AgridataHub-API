package cat.udl.eps.softarch.agridatahub.domain;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@Data
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date creationDate;

    @Column(length = 500, unique = false)
    private String description;

    //S'ha d'enllaçar amb una instancia de Reuser i DatasetRequest
}
