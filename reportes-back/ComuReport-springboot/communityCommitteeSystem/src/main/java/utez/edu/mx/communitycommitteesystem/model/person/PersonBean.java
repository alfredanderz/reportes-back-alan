package utez.edu.mx.communitycommitteesystem.model.person;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import utez.edu.mx.communitycommitteesystem.model.area.AreaBean;
import utez.edu.mx.communitycommitteesystem.model.colony.ColonyBean;
import utez.edu.mx.communitycommitteesystem.model.municipality.MunicipalityBean;
import utez.edu.mx.communitycommitteesystem.model.sms.SmsBean;
import utez.edu.mx.communitycommitteesystem.model.state.StateBean;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "person",indexes = {
        @Index(name = "idx_unique_email", columnList = "email", unique = true)
})
public class PersonBean {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;
    @Column(length = 100, nullable = false)
    private String lastname;
    @NotBlank(message = "Necesitas un correo")
    @Column(length = 50, nullable = false)
    private String email;
    @Column( nullable = false)
    @JsonIgnore
    private String password;
    @Column(length = 50, nullable = false)
    private String phone;
    @JsonIgnore
    @OneToOne(mappedBy = "personBean", cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
    private StateBean stateBean;
    @JsonIgnore
    @OneToOne(mappedBy = "personBean", cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true )
    private MunicipalityBean municipalityBean;
    @JsonIgnore
    @OneToOne(mappedBy = "personBean", cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
    private ColonyBean colonyBean;
    @JsonIgnore
    @OneToOne(mappedBy = "personBean", cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
    private AreaBean areaBean;
    @JsonIgnore
    @OneToMany(mappedBy = "personBean", cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
    private List<SmsBean> smsBeanList;
    @JsonIgnore
    @Column(columnDefinition = "BOOL DEFAULT true")
    private Boolean status = true;
    @JsonIgnore
    @Column(columnDefinition = "BOOL DEFAULT false")
    private Boolean blocked = false;
    @JsonIgnore
    private String token;


    @JsonIgnore
    public String getRole(){

        if(this.areaBean!=null){
            return "Area";
        }
        else if ( this.municipalityBean!=null){
            return "Municipality";
        }
        else if ( this.colonyBean!=null){
            return "Colony";
        }
        else if ( this.stateBean!=null){
            return "State";
        }
        return "";
    }
    @JsonIgnore
    public String getRoleUuid(){
        if(this.areaBean!=null){
            return areaBean.getUuid();
        }
        else if ( this.municipalityBean!=null){
            return municipalityBean.getUuid();
        }
        else if ( this.colonyBean!=null){
            return colonyBean.getUuid();
        }
        else if ( this.stateBean!=null){
            return stateBean.getUuid();
        }
        return "";
    }
}
