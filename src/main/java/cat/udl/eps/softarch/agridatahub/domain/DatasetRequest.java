package cat.udl.eps.softarch.agridatahub.domain;


import lombok.NoArgsConstructor;
import lombok.Data;
import javax.validation.constraints.NotBlank;

import javax.persistence.*;


@Entity
@Data
@NoArgsConstructor
public class DatasetRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Column
    private boolean granted = false;

}
