package utez.edu.mx.communitycommitteesystem.model.person;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<PersonBean,Long> {
    PersonBean findByEmail(String email);

    Optional<PersonBean> findById(Long id);

}
