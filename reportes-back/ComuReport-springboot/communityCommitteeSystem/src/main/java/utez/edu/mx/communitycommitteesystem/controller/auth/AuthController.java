package utez.edu.mx.communitycommitteesystem.controller.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.communitycommitteesystem.controller.auth.dto.SignDto;
import utez.edu.mx.communitycommitteesystem.controller.auth.dto.TokenDto;
import utez.edu.mx.communitycommitteesystem.service.auth.AuthService;


@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService service;
    @PostMapping("/signin")
    public ResponseEntity<TokenDto> signIn(HttpServletRequest request, @Valid @RequestBody SignDto dto) {
        return service.signIn
                (dto.getUsername(), dto.getPassword(), request);
    }


}
