package utez.edu.mx.communitycommitteesystem.service.municipality;

import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.communitycommitteesystem.controller.municipality.AssignAdminMunicipalityDto;
import utez.edu.mx.communitycommitteesystem.controller.person.PersonUpdateContact;
import utez.edu.mx.communitycommitteesystem.model.municipality.MunicipalityBean;
import utez.edu.mx.communitycommitteesystem.model.municipality.MunicipalityRepository;
import utez.edu.mx.communitycommitteesystem.model.person.PersonBean;
import utez.edu.mx.communitycommitteesystem.model.state.StateBean;
import utez.edu.mx.communitycommitteesystem.service.person.PersonService;
import utez.edu.mx.communitycommitteesystem.service.report.ReportService;
import utez.edu.mx.communitycommitteesystem.service.state.StateService;

import java.util.List;

@Service
public class MunicipalityService {
    private final MunicipalityRepository municipalityRepository;
    private final StateService stateService;
    private final PersonService personService;
    private ReportService reportService;


    public MunicipalityService(MunicipalityRepository municipalityRepository, StateService stateService, PersonService personService, @Lazy ReportService reportService) {
        this.municipalityRepository = municipalityRepository;
        this.stateService = stateService;
        this.personService = personService;
        this.reportService = reportService;
    }

    public MunicipalityBean findByUuid(String uuid) {
        return municipalityRepository.findByUuid(uuid).orElseThrow(() -> new EntityNotFoundException("Municipality not found!."));
    }

    @Transactional
    public String registerMunicipalityWithAdmin(MunicipalityBean municipalityBean, String uuidState) {
        StateBean state = stateService.findByUuid(uuidState);
        PersonBean savedPerson = personService.save(municipalityBean.getPersonBean());
        municipalityBean.setPersonBean(savedPerson);
        municipalityBean.setStateBean(state);
        municipalityRepository.save(municipalityBean);

        return "Municipio y administrador registrados correctamente";
    }

    public List<MunicipalityBean> findAll(String stateUuid) {
        StateBean state = stateService.findByUuid(stateUuid);
        return state.getMunicipalityBeanList();
    }

    public MunicipalityBean findByUuid(String municipalityUuid, String uuidState) {
        StateBean state = stateService.findByUuid(uuidState);
        return municipalityRepository.findByUuidAndStateBean(municipalityUuid, state).orElseThrow(() -> new EntityNotFoundException("Municipality not found!."));
    }

    public MunicipalityBean update(PersonUpdateContact personUpdateContact, String uuidState) {
        MunicipalityBean municipalityUpdate = findByUuid(personUpdateContact.getUuid(), uuidState);
        municipalityUpdate.getPersonBean().setPhone(personUpdateContact.getPhone());
        municipalityUpdate.getPersonBean().setEmail(personUpdateContact.getEmail());
        return municipalityRepository.save(municipalityUpdate);
    }

    public String delete(MunicipalityBean municipalityBean, String uuidState) {
        MunicipalityBean municipality = findByUuid(municipalityBean.getUuid(), uuidState);
        try {
            personService.delete(municipality.getPersonBean());


        } catch (DataIntegrityViolationException e) {
            municipality.setStatus(false);
            municipality.getPersonBean().setStatus(false);
            personService.save(municipality.getPersonBean());
            municipalityRepository.save(municipality);
            return "Municipality disabled successfully";

        }

        return "Municipality delete successfully";


    }

    public String transfer(AssignAdminMunicipalityDto dto, String uuidState) {
        MunicipalityBean municipality = findByUuid(dto.getUuid(), uuidState);
        dto.toEntity();
        PersonBean personBean = personService.save(dto.getPersonBean());
        municipality.setPersonBean(personBean);
        municipalityRepository.save(municipality);
        return "Municipality transferred successfully";
    }


}
