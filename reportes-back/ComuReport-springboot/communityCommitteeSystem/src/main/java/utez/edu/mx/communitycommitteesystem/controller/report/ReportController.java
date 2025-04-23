package utez.edu.mx.communitycommitteesystem.controller.report;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.communitycommitteesystem.security.jwt.JwtProvider;
import utez.edu.mx.communitycommitteesystem.service.report.ReportService;

import java.util.List;

@RestController
@RequestMapping("/api/report")
@AllArgsConstructor
public class ReportController {

    private final ReportService reportService;
    private final JwtProvider jwtProvider;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> registerReport(
            @Valid @ModelAttribute("dto") ReportDto dto, // Aquí mapeas los datos del DTO
            HttpServletRequest req) {
        reportService.registerReport(dto, jwtProvider.resolveClaimsJUuid(req)  );
        return ResponseEntity.ok("Report registered successfully");

    }

    @GetMapping("")
    public ResponseEntity<List<ReportSummaryDto>> getReportsByColony(HttpServletRequest req) {
        List<ReportSummaryDto> reports = reportService.getReportsByColonyUuid(jwtProvider.resolveClaimsJUuid(req) , jwtProvider.resolveClaimsJRole(req));
        return ResponseEntity.ok(reports);
    }

    @PutMapping("")
    public ResponseEntity<String> updateReportStatus(HttpServletRequest req, @RequestBody ReportStatusUpdateDto request) {
            reportService.updateReportStatus( request,jwtProvider.resolveClaimsJUuid(req) , jwtProvider.resolveClaimsJRole(req));
            return ResponseEntity.ok("Reporte actualizado");

    }
    @PutMapping("/cancel")
    public ResponseEntity<String> cancelReportStatus(HttpServletRequest req, @RequestBody ReportStatusUpdateDto request) {
        reportService.cancelReport( request,jwtProvider.resolveClaimsJUuid(req) , jwtProvider.resolveClaimsJRole(req));
        return ResponseEntity.ok("Reporte actualizado");

    }

    @GetMapping("/history")
    public ResponseEntity<List<ReportSummaryDto>> history(HttpServletRequest req) {
        List<ReportSummaryDto> reports = reportService.findAllHistory(jwtProvider.resolveClaimsJUuid(req) , jwtProvider.resolveClaimsJRole(req));
        return ResponseEntity.ok(reports);
    }
}
