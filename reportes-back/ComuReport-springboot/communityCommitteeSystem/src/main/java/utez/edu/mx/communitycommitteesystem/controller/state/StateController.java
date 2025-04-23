package utez.edu.mx.communitycommitteesystem.controller.state;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.communitycommitteesystem.controller.person.PersonUpdateContact;
import utez.edu.mx.communitycommitteesystem.model.state.StateBean;
import utez.edu.mx.communitycommitteesystem.security.jwt.JwtProvider;
import utez.edu.mx.communitycommitteesystem.service.state.StateService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/state")
public class StateController {


    private final StateService stateService;
    private final JwtProvider jwtProvider;


    @PostMapping()
    public ResponseEntity<String> registerStateWithAdmin(@Valid @RequestBody StateWithAdminDto dto) {
        String result = stateService.registerStateWithAdmin(dto.toEntity());
        return ResponseEntity.ok(result);

    }

    @GetMapping("")
    public ResponseEntity<StateBean> getStateAdminsByStateUuid(HttpServletRequest req) {
        return ResponseEntity.ok(stateService.findByUuid(jwtProvider.resolveClaimsJUuid(req)));
    }

    @PutMapping("")
    public ResponseEntity<StateBean> update(HttpServletRequest req, @Valid @RequestBody PersonUpdateContact personUpdateContact) {
        return ResponseEntity.ok(stateService.update(jwtProvider.resolveClaimsJUuid(req), personUpdateContact));
    }

    @DeleteMapping("")
    public ResponseEntity<String> delete(HttpServletRequest req) {
        return ResponseEntity.ok(stateService.delete(jwtProvider.resolveClaimsJUuid(req)));
    }

    @PutMapping("/transfer")
    public ResponseEntity<String> transfer(@Valid @RequestBody StateWithAdminDto dto , HttpServletRequest req) {
        return ResponseEntity.ok(stateService.transfer(jwtProvider.resolveClaimsJUuid(req),dto.toEntity()));

    }


}
