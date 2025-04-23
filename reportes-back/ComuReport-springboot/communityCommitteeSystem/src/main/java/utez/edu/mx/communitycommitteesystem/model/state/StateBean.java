package utez.edu.mx.communitycommitteesystem.model.state;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import utez.edu.mx.communitycommitteesystem.model.municipality.MunicipalityBean;
import utez.edu.mx.communitycommitteesystem.model.person.PersonBean;
import utez.edu.mx.communitycommitteesystem.model.report.ReportBean;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "state")
public class StateBean {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false)
    private String nameState;

    @OneToOne
    @JoinColumn(name = "idPerson")
    @JsonManagedReference
    private PersonBean personBean;

    @Column(length = 36, nullable = false, unique = true, updatable = false)
    private String uuid;


    @OneToMany( mappedBy = "stateBean",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<MunicipalityBean> municipalityBeanList;

    @OneToMany( mappedBy = "stateBean",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<ReportBean> reportBeanList;

    @Column(name = "status", nullable = false)
    private boolean status = true;

    public StateBean() {
        this.uuid = UUID.randomUUID().toString(); // Generar UUID al crear la entidad
    }





}
