package utez.edu.mx.communitycommitteesystem.controller.report;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ReportDto {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    private MultipartFile[] file;

}
