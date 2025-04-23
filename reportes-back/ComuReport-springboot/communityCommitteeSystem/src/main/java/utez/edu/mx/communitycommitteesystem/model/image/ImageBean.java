package utez.edu.mx.communitycommitteesystem.model.image;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import utez.edu.mx.communitycommitteesystem.model.report.ReportBean;

@Entity
@Getter
@Setter
@Table(name = "image")
public class ImageBean {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(length = 100)
    private String image;

    @Column(length = 255)
    private String url;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "idReport")
    private ReportBean reportBean;


}
