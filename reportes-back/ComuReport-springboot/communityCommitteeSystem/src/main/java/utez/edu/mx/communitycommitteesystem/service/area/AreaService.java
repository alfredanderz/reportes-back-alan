package utez.edu.mx.communitycommitteesystem.service.area;

import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.communitycommitteesystem.controller.person.PersonUpdateContact;
import utez.edu.mx.communitycommitteesystem.model.area.AreaBean;
import utez.edu.mx.communitycommitteesystem.model.area.AreaRepository;
import utez.edu.mx.communitycommitteesystem.model.municipality.MunicipalityBean;
import utez.edu.mx.communitycommitteesystem.model.person.PersonBean;
import utez.edu.mx.communitycommitteesystem.service.municipality.MunicipalityService;
import utez.edu.mx.communitycommitteesystem.service.person.PersonService;
import utez.edu.mx.communitycommitteesystem.service.report.ReportService;

import java.util.List;

@Service
public class AreaService {


    private final AreaRepository areaRepository;

    private final PersonService personService;
    private final MunicipalityService municipalityService;
    private ReportService reportService;

    private static final Logger logger = LogManager.getLogger(AreaService.class);

    public AreaService(AreaRepository areaRepository, PersonService personService, MunicipalityService municipalityService,@Lazy ReportService reportService) {
        this.areaRepository = areaRepository;
        this.personService = personService;
        this.municipalityService = municipalityService;
        this.reportService = reportService;
    }

    public AreaBean getArea(String areaUuid, String municipalityUuid) {
        MunicipalityBean municipalityBean = municipalityService.findByUuid(municipalityUuid);

        AreaBean areaBaen = areaRepository.findByUuidAndMunicipalityBean(areaUuid, municipalityBean)
                .orElseThrow(() -> new EntityNotFoundException("Area not found"));
        return  areaBaen;
    }
    public AreaBean getArea(String areaUuid) {
        return  areaRepository.findByUuid(areaUuid).orElseThrow(() -> new EntityNotFoundException("Area not found"));
    }

    public List<AreaBean> findByMunicipality(String municipalityUuid) {

        MunicipalityBean municipalityBean = municipalityService.findByUuid(municipalityUuid);

        List<AreaBean> areas = areaRepository.findByMunicipalityBean(municipalityBean);

        return areas;
    }

    public String save(AreaBean areaBean, String municipalityUuid) {

        MunicipalityBean municipalityBean = municipalityService.findByUuid(municipalityUuid);

        areaBean.setMunicipalityBean(municipalityBean);

        PersonBean personBean = personService.saveMun(areaBean.getPersonBean());
        areaBean.setPersonBean(personBean);
        areaRepository.save(areaBean);
        logger.info("Creo area salida com sucesso" + areaBean.getUuid());
        return "Area registered successfully";
    }

    public String update(PersonUpdateContact dto, String municipalityUuid) {
       AreaBean areaUpdate =  getArea(dto.getUuid(), municipalityUuid);
       areaUpdate.getPersonBean().setEmail(dto.getEmail());
       areaUpdate.getPersonBean().setPhone(dto.getPhone());
        areaRepository.save(areaUpdate);
        return  "Updated successfully";
    }

    public String delete(String uuidArea, String municipalityUuid) {
        AreaBean area =  getArea(uuidArea, municipalityUuid);

        try{
            logger.info(area.getNameArea());
            personService.delete(getArea(uuidArea,municipalityUuid).getPersonBean());
        }catch (DataIntegrityViolationException e){
            System.out.println(e);
            area.setStatus(false);
            area.getPersonBean().setStatus(false);
            personService.save(area.getPersonBean());
            areaRepository.save(area);
            return "Area disabled successfully";
        }

        return "Area delete successfully";
    }
    public String transfer(AreaBean areaBean, String uuid){
        AreaBean area =  getArea(areaBean.getUuid(), uuid);
        area.setPersonBean(areaBean.getPersonBean());
        PersonBean personBean =  personService.save(area.getPersonBean());
        area.setPersonBean(personBean);
        areaRepository.save(area);
        return "Area transfer successfully";
    }
}


