package utez.edu.mx.communitycommitteesystem.model.report;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import utez.edu.mx.communitycommitteesystem.model.area.AreaBean;
import utez.edu.mx.communitycommitteesystem.model.colony.ColonyBean;
import utez.edu.mx.communitycommitteesystem.model.image.ImageBean;
import utez.edu.mx.communitycommitteesystem.model.municipality.MunicipalityBean;
import utez.edu.mx.communitycommitteesystem.model.sms.SmsBean;
import utez.edu.mx.communitycommitteesystem.model.state.StateBean;
import utez.edu.mx.communitycommitteesystem.model.status.StatusBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "report")

public class ReportBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Necesitas colocarle un titulo al reporte")
    @Column(length = 100, nullable = false)
    private String title;
    @Column(length = 250, nullable = false)
    private String description;
    @Temporal(TemporalType.TIMESTAMP)
    private Date reportDate;
    @Column(length = 100)
    private String statusDescription;
    @Column(length = 36, nullable = false, unique = true, updatable = false)
    private String uuid;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idColony")
    private ColonyBean colonyBean;

    @OneToMany( mappedBy = "reportBean",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SmsBean> smsBeanList;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idArea")
    private AreaBean areaBean;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idStatus")
    private StatusBean statusBean;

    @OneToMany( mappedBy = "reportBean",cascade = CascadeType.ALL)
    private List<ImageBean> ImageBeanList;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idMunicipality")
    private MunicipalityBean municipalityBean;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idState")
    private StateBean stateBean;

    public ReportBean() {
        this.uuid = java.util.UUID.randomUUID().toString();
        this.ImageBeanList = new ArrayList<>();

    }


}
