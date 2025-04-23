package utez.edu.mx.communitycommitteesystem.model.sms;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import utez.edu.mx.communitycommitteesystem.model.person.PersonBean;
import utez.edu.mx.communitycommitteesystem.model.report.ReportBean;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "sms")
public class SmsBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 500, nullable = false)
    private String message;
    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveryDate;
    @Column(length = 36, nullable = false, unique = true, updatable = false)
    private String uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPerson")
    private PersonBean personBean;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idReport")
    private ReportBean reportBean;

    public SmsBean() {
        this.uuid = java.util.UUID.randomUUID().toString();

    }


}
