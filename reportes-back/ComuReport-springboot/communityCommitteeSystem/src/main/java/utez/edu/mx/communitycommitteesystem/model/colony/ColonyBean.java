package utez.edu.mx.communitycommitteesystem.model.colony;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import utez.edu.mx.communitycommitteesystem.model.municipality.MunicipalityBean;
import utez.edu.mx.communitycommitteesystem.model.person.PersonBean;
import utez.edu.mx.communitycommitteesystem.model.report.ReportBean;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "colony")

public class ColonyBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false)
    private String nameColony;

    @Column(length = 36, nullable = false, unique = true, updatable = false)
    private String uuid;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "idPerson")
    private PersonBean personBean;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMunicipality")
    private MunicipalityBean municipalityBean;

    @Column(name = "status", nullable = false)
    private boolean status = true;

    @OneToMany(mappedBy = "colonyBean", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ReportBean> reportBeanList;



    public ColonyBean() {
        this.uuid = UUID.randomUUID().toString();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ColonyBean(Long id) {
        this.id = id;
    }

    public ColonyBean(Long id, String nameColony) {
        this.id = id;
        this.nameColony = nameColony;
    }


}
