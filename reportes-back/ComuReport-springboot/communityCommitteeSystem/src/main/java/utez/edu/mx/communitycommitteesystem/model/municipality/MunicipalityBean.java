package utez.edu.mx.communitycommitteesystem.model.municipality;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import utez.edu.mx.communitycommitteesystem.model.area.AreaBean;
import utez.edu.mx.communitycommitteesystem.model.colony.ColonyBean;
import utez.edu.mx.communitycommitteesystem.model.person.PersonBean;
import utez.edu.mx.communitycommitteesystem.model.report.ReportBean;
import utez.edu.mx.communitycommitteesystem.model.state.StateBean;

import java.util.List;
import java.util.UUID;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
@Entity
@Getter
@Setter
@Data
@Table(name = "municipality")
public class MunicipalityBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String nameMunicipality;

    @Column(length = 36, nullable = false, unique = true, updatable = false)
    private String uuid;

    @OneToOne
    @JsonManagedReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "idPerson")
    private PersonBean personBean;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idState")
    private StateBean stateBean;

    @JsonIgnore
    @OneToMany( mappedBy = "municipalityBean",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ColonyBean> colonyBeanList;
    @JsonIgnore
    @OneToMany( mappedBy = "municipalityBean",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AreaBean> areaBeanList;
    @JsonIgnore
    @OneToMany(mappedBy = "municipalityBean", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ReportBean> reportBeanList;

    @Column(name = "status", nullable = false)
    private boolean status = true;

    public MunicipalityBean() {
        this.uuid = UUID.randomUUID().toString();
    }



}
