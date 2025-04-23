package utez.edu.mx.communitycommitteesystem.service.colony;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import utez.edu.mx.communitycommitteesystem.controller.colony.ColonyWithLinkDto;
import utez.edu.mx.communitycommitteesystem.controller.person.PersonUpdateContact;
import utez.edu.mx.communitycommitteesystem.model.colony.ColonyBean;
import utez.edu.mx.communitycommitteesystem.model.colony.ColonyRepository;
import utez.edu.mx.communitycommitteesystem.model.municipality.MunicipalityBean;
import utez.edu.mx.communitycommitteesystem.model.person.PersonBean;
import utez.edu.mx.communitycommitteesystem.service.municipality.MunicipalityService;
import utez.edu.mx.communitycommitteesystem.service.person.PersonService;
import utez.edu.mx.communitycommitteesystem.service.report.ReportService;

import java.util.List;

@Service
@AllArgsConstructor
public class ColonyService {

    private final ColonyRepository colonyRepository;
    private final PersonService personService;
    private final MunicipalityService municipalityService;

    public ColonyBean findByUuid(MunicipalityBean municipalityBean, String uuid) {
        return colonyRepository.findByUuidAndMunicipalityBean(uuid, municipalityBean).orElseThrow(() -> new EntityNotFoundException("Colony not found"));
    }

    public ColonyBean findByUuid(String uuid) {
        return colonyRepository.findByUuid(uuid).orElseThrow(() -> new EntityNotFoundException("Colony not found"));
    }

    public String delete(String uuidMunicipality, ColonyWithLinkDto dto) {

        ColonyBean colonyBean = findByUuid(municipalityService.findByUuid(uuidMunicipality), dto.getUuid());
        try {
            personService.delete(findByUuid(municipalityService.findByUuid(uuidMunicipality), dto.getUuid()).getPersonBean());

        } catch (DataIntegrityViolationException e) {
            colonyBean.setStatus(false);
            colonyBean.getPersonBean().setStatus(false);
            personService.save(colonyBean.getPersonBean());
            colonyRepository.save(colonyBean);
            return "Colony disabled successfully";
        }

        return "Colony delete successfully";
    }

    public ColonyBean get(String uuid, String uuidMunicipality) {
        return findByUuid(municipalityService.findByUuid(uuidMunicipality), uuid);
    }


    public String registerColonyWithLink(ColonyBean colonyBean, String uuid) {
        PersonBean savedPerson = personService.saveColony(colonyBean.getPersonBean());
        colonyBean.setPersonBean(savedPerson);
        MunicipalityBean municipalityBean = municipalityService.findByUuid(uuid);
        colonyBean.setMunicipalityBean(municipalityBean);
        colonyRepository.save(colonyBean);

        return "Colony Success";
    }

    public List<ColonyBean> findAll(String uuidMunicipality) {
        return colonyRepository.findByMunicipalityBean(municipalityService.findByUuid(uuidMunicipality));
    }

    public String update(PersonUpdateContact dto, String uuid) {
        ColonyBean colonyBean = findByUuid(municipalityService.findByUuid(uuid), dto.getUuid());
        colonyBean.getPersonBean().setEmail(dto.getEmail());
        colonyBean.getPersonBean().setPhone(dto.getPhone());
        colonyRepository.save(colonyBean);
        return "Colony updated successfully";
    }

    public String transfer(ColonyBean colonyBean, String uuid) {
        ColonyBean colony = findByUuid(municipalityService.findByUuid(uuid), colonyBean.getUuid());
        colony.setPersonBean(colonyBean.getPersonBean());
        PersonBean personBean = personService.save(colony.getPersonBean());
        colonyBean.setPersonBean(personBean);
        colonyRepository.save(colony);
        return "Colony transfer successfully";
    }
}