package utez.edu.mx.communitycommitteesystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import utez.edu.mx.communitycommitteesystem.security.jwt.JwtAuthenticationFilter;
import utez.edu.mx.communitycommitteesystem.security.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity

public class MainSecurity {
    private String authMunicipality = "Municipality";
    private String authColony = "Colony";
    private String authArea = "Area";
    private final String[] WHITE_LIST = {
            "/api/auth/signin",
            "/api/state",
    };
    private final String[] State_LIST = {
            "/api/municipality",
            "/api/state/transfer"
    };
    private final String[] Municipality_LIST = {
            "/api/colony",
            "/api/report/updateStatus/",
            "/api/area/all",
            "/api/area/"
    };
    private final String[] Colony_LIST = {
            "/api/committee"
    };
    private final UserDetailsServiceImpl service;

    public MainSecurity(UserDetailsServiceImpl service) {
        this.service = service;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
        dao.setUserDetailsService(service);
        dao.setPasswordEncoder(passwordEncoder());
        return dao;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter filter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(req -> {
                    // Iterar sobre el arreglo WHITE_LIST y aplicar cada ruta
                    for (String path : WHITE_LIST) {
                        req.requestMatchers(new AntPathRequestMatcher(path)).permitAll();
                    }
                    for (String path : State_LIST) {
                        req.requestMatchers(new AntPathRequestMatcher(path)).hasAnyAuthority("State");
                    }
                    for (String path : Municipality_LIST) {
                        req.requestMatchers(new AntPathRequestMatcher(path)).hasAnyAuthority(authMunicipality);
                    }
                    for (String path : Colony_LIST) {
                        req.requestMatchers(new AntPathRequestMatcher(path)).hasAnyAuthority(authColony);
                    }
                    req.requestMatchers(new AntPathRequestMatcher("/api/report")).hasAnyAuthority(authColony,authMunicipality,authArea);
                    req.requestMatchers(new AntPathRequestMatcher("/api/report/history")).hasAnyAuthority(authColony,authMunicipality,authArea);

                    req.anyRequest().authenticated();
                })
                .httpBasic(Customizer.withDefaults())
                .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(filter(), UsernamePasswordAuthenticationFilter.class)
                .logout(out -> out.logoutUrl("/api/auth/logout").clearAuthentication(true));
        return http.build();
    }

}
