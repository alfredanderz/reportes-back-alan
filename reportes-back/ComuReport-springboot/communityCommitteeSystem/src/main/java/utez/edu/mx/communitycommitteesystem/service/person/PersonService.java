package utez.edu.mx.communitycommitteesystem.service.person;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.communitycommitteesystem.model.person.PersonBean;
import utez.edu.mx.communitycommitteesystem.model.person.PersonRepository;

@Service
@Transactional
@Getter

public class PersonService {

    private final PersonRepository personRepository;



    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public PersonRepository getPersonRepository() {
        return personRepository;
    }

    // Buscar persona por correo

    // Guardar persona
    public PersonBean save(PersonBean person) {
        return personRepository.save(person);

    }

    public PersonBean saveMun(PersonBean person) {
        person.setStatus(true);
        person.setBlocked(false);
        return personRepository.save(person);
    }



    public PersonBean saveColony(PersonBean person) {
        person.setStatus(true);
        person.setBlocked(false);
        return personRepository.save(person);
    }

    public void delete(PersonBean person) {
        personRepository.delete(person);
    }
    public void delete(Long id) {
        personRepository.deleteById(id);
    }

    public PersonBean getPerson(Long id) {
        return personRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Person not found!"));
    }
    public PersonBean update(PersonBean person) {
        return personRepository.save(person);
    }
}

