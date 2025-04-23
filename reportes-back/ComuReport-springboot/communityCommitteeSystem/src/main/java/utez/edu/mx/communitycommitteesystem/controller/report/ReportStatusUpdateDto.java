package utez.edu.mx.communitycommitteesystem.controller.report;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ReportStatusUpdateDto {
    private String statusDescription;
    private String uuid;
    private String uuidArea;

}
