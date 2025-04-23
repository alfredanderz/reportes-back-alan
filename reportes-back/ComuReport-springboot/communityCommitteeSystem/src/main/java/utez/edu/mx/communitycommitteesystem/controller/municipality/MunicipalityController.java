package utez.edu.mx.communitycommitteesystem.controller.municipality;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.communitycommitteesystem.controller.person.PersonUpdateContact;
import utez.edu.mx.communitycommitteesystem.model.municipality.MunicipalityBean;
import utez.edu.mx.communitycommitteesystem.security.jwt.JwtProvider;
import utez.edu.mx.communitycommitteesystem.service.municipality.MunicipalityService;

import java.util.List;

@RestController
@RequestMapping("/api/municipality")
@AllArgsConstructor
public class MunicipalityController {


    private final MunicipalityService municipalityService;
    private final JwtProvider jwtProvider;


    @PostMapping()
    public ResponseEntity<String> registerMunicipalityWithAdmin(@Valid @RequestBody AssignAdminMunicipalityDto dto, HttpServletRequest req) {

        String uuid = jwtProvider.resolveClaimsJUuid(req);
        String response = municipalityService.registerMunicipalityWithAdmin(dto.toEntity(), uuid);
        return ResponseEntity.ok(response);
    }

    @GetMapping("")
    public ResponseEntity<List<MunicipalityBean>> getMunicipalitiesByStateUuid(HttpServletRequest req) {
        String uuid = jwtProvider.resolveClaimsJUuid(req);
        List<MunicipalityBean> municipalities = municipalityService.findAll(uuid);
        return ResponseEntity.ok(municipalities);

    }

    @GetMapping("/{municipalityUuid}")
    public ResponseEntity<MunicipalityBean> getMunicipalityAdminByUuid(@PathVariable String municipalityUuid , HttpServletRequest req) {

            String uuid = jwtProvider.resolveClaimsJUuid(req);
            MunicipalityBean municipalityBean = municipalityService.findByUuid(municipalityUuid,uuid );
            return ResponseEntity.ok(municipalityBean);

    }
    @PutMapping()
    public ResponseEntity<MunicipalityBean> update ( HttpServletRequest req , @Valid @RequestBody PersonUpdateContact dto){
        String uuid = jwtProvider.resolveClaimsJUuid(req);
       MunicipalityBean municipalityBean=  municipalityService.update( dto , uuid);
        return ResponseEntity.ok(municipalityBean);
    }
    @DeleteMapping()
    public ResponseEntity<String> delete(HttpServletRequest req ,  @RequestBody AssignAdminMunicipalityDto dto) {
        String uuid = jwtProvider.resolveClaimsJUuid(req);

        return ResponseEntity.ok(municipalityService.delete( dto.toEntityUpdate() , uuid ));
    }

    @PutMapping("/transfer")
    public ResponseEntity<String> transfer(@Valid @RequestBody AssignAdminMunicipalityDto dto, HttpServletRequest req) {
        String uuid = jwtProvider.resolveClaimsJUuid(req);
        String response = municipalityService.transfer(dto, uuid);
        return ResponseEntity.ok(response);
    }

}
