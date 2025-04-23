package utez.edu.mx.communitycommitteesystem.service.auth;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.communitycommitteesystem.controller.auth.dto.TokenDto;
import utez.edu.mx.communitycommitteesystem.model.person.PersonBean;
import utez.edu.mx.communitycommitteesystem.security.jwt.JwtProvider;
import utez.edu.mx.communitycommitteesystem.service.person.PersonService;

import java.util.Optional;


@Service
@Transactional
public class AuthService {
    private final PersonService userService;


    private final JwtProvider provider;
    private final AuthenticationManager manager;

    private final JdbcTemplate jdbcTemplate;


    public AuthService(PersonService userService, JwtProvider provider, AuthenticationManager manager, JdbcTemplate jdbcTemplate) {
        this.userService = userService;
        this.provider = provider;
        this.manager = manager;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public ResponseEntity<TokenDto> signIn(String username, String password, HttpServletRequest request) {
        TokenDto tokenDto = new TokenDto();


        try {

            BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
            Optional<PersonBean> foundUser = Optional.ofNullable(userService.getPersonRepository().findByEmail(username));
            if (foundUser.isEmpty()) {
                tokenDto.setMessage("User not found");
                return ResponseEntity.ok(tokenDto);
            }
            if (!bcrypt.matches(password, foundUser.get().getPassword())) {
                tokenDto.setMessage("Wrong password");
                return ResponseEntity.ok(tokenDto);
            }

            PersonBean user = foundUser.get();

            if (user.getStatus().equals("Baja")) {
                tokenDto.setMessage("User is Baja");
                return ResponseEntity.ok(tokenDto);
            }
            if (user.getStatus().equals("Bloqueado")) {
                tokenDto.setMessage("User is Bloqueado");
                return ResponseEntity.ok(tokenDto);
            }
            System.out.println(new UsernamePasswordAuthenticationToken(username, password));
            Authentication auth = manager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
            String token = provider.generateToken(auth);
            // Payload - DTO (token, attrs)

            tokenDto.setToken(token);
            tokenDto.setRole(auth.getAuthorities().iterator().next().toString());

            String ip = request.getRemoteAddr();
            String userAgent = request.getHeader("User-Agent");
            String role = tokenDto.getRole();

            jdbcTemplate.update(
                    "INSERT INTO user_login_logs (user_uuid, user_role, ip_address, user_agent) VALUES (?, ?, ?, ?)",
                    user.getRoleUuid(), role, ip, userAgent
            );

            return ResponseEntity.ok(tokenDto);
        } catch (Exception e) {
            System.out.println(e);
            String message = "CredentialsMismatch";
            if (e instanceof DisabledException)
                message = "UserDisabled";
            if (e instanceof AccountExpiredException)
                message = "Expiro";
            tokenDto.setMessage(message);
            return ResponseEntity.ok(tokenDto);
        }
    }


}
