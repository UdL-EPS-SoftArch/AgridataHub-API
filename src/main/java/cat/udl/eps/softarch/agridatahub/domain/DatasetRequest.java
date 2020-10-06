package cat.udl.eps.softarch.agridatahub.domain;


import lombok.NoArgsConstructor;
import lombok.Data;


import javax.persistence.*;


@Entity
@Data
@NoArgsConstructor
public class DatasetRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private boolean granted = false;

}
