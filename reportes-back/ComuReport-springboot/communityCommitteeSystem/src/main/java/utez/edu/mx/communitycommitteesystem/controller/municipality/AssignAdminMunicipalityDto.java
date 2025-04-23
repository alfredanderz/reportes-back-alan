package utez.edu.mx.communitycommitteesystem.controller.municipality;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import utez.edu.mx.communitycommitteesystem.controller.person.PersonDto;
import utez.edu.mx.communitycommitteesystem.model.municipality.MunicipalityBean;
import utez.edu.mx.communitycommitteesystem.model.person.PersonBean;

@Getter
@Setter
public class AssignAdminMunicipalityDto extends PersonDto {

    @NotBlank
    private String nameMunicipality;
    private String uuid;

    public MunicipalityBean toEntity(){
        MunicipalityBean municipalityBean = new MunicipalityBean();
        PersonBean person = getPersonBean();
        municipalityBean.setPersonBean(person);
        municipalityBean.setNameMunicipality(nameMunicipality);
        return municipalityBean;

    }

    public MunicipalityBean toEntityUpdate(){
        MunicipalityBean municipalityBean = new MunicipalityBean();
        municipalityBean.setNameMunicipality(nameMunicipality);
        municipalityBean.setUuid(uuid);
        return municipalityBean;

    }
}


