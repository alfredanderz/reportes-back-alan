package utez.edu.mx.communitycommitteesystem.security.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import utez.edu.mx.communitycommitteesystem.exception.GlobalExceptionHandler;
import utez.edu.mx.communitycommitteesystem.model.person.PersonBean;

import java.util.*;

public class UserDetailsImpl implements UserDetails{
    private String username;
    private String password;
    private boolean blocked;
    private boolean enabled;
    private String uuid;
    private Collection<? extends GrantedAuthority> authorities;
    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    public UserDetailsImpl(String username, String password, boolean blocked, boolean enabled, String type , String uuid) {
        this.username = username;
        this.password = password;
        this.blocked = blocked;
        this.enabled = enabled;
        this.uuid = uuid;
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(type));
        this.authorities = authorities;

    }

    public static UserDetailsImpl build(PersonBean user){
       // logger.info("Nombre : " +user.getRole());
        return new UserDetailsImpl(
                user.getEmail(), user.getPassword(),
                user.getBlocked(), user.getStatus(),user.getRole() , user.getRoleUuid()
        );
    }

    public String getUuid() {
        return uuid;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !blocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
