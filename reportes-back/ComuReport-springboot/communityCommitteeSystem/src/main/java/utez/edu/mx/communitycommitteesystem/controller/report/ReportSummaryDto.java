package utez.edu.mx.communitycommitteesystem.controller.report;

import lombok.Data;
import utez.edu.mx.communitycommitteesystem.model.image.ImageBean;

import java.util.Date;
import java.util.List;

@Data
public class ReportSummaryDto {
    private String title;
    private List<ImageBean> image;
    private Date date;
    private String uuid;
    private String status;
    private String colonyName;
    private String municipalityName;



    public ReportSummaryDto(String title, List<ImageBean> image, Date date, String name, String lastname, String status,String uuid) {
        this.title = title;
        this.image = image;
        this.date = date;
        this.colonyName = name;
        this.municipalityName = lastname;
        this.status = status;
        this.uuid = uuid;
    }



}
