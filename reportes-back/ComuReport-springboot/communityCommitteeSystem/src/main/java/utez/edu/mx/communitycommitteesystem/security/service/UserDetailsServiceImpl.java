package utez.edu.mx.communitycommitteesystem.security.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utez.edu.mx.communitycommitteesystem.exception.GlobalExceptionHandler;
import utez.edu.mx.communitycommitteesystem.model.person.PersonBean;
import utez.edu.mx.communitycommitteesystem.security.model.UserDetailsImpl;
import utez.edu.mx.communitycommitteesystem.service.person.PersonService;

import java.util.Optional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    private final PersonService service;


    public UserDetailsServiceImpl(PersonService service) {
        this.service = service;
    }
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<PersonBean> foundUser = Optional.ofNullable(service.getPersonRepository().findByEmail(username));

        if (foundUser.isPresent()) {

            return UserDetailsImpl.build(foundUser.get());
        }


        throw new UsernameNotFoundException("UserNotFound");
    }
}
