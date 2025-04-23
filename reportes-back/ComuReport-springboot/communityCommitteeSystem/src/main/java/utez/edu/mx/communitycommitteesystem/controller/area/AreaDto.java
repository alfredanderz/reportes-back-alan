package utez.edu.mx.communitycommitteesystem.controller.area;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import utez.edu.mx.communitycommitteesystem.controller.person.PersonDto;
import utez.edu.mx.communitycommitteesystem.model.area.AreaBean;
import utez.edu.mx.communitycommitteesystem.model.person.PersonBean;

@Getter
@Setter
public class AreaDto extends PersonDto {
    @NotBlank
    private String nameArea;
    private String uuid;

    public AreaBean toEntity() {
        PersonBean personBean = getPersonBean();
        AreaBean areaBean = new AreaBean();
        areaBean.setNameArea(nameArea);
        areaBean.setPersonBean(personBean);
        return areaBean;
    }
    public AreaBean toEntityUpdate() {
        AreaBean areaBean = new AreaBean();
        PersonBean personBean = getPersonBean();
        areaBean.setPersonBean(personBean);
        areaBean.setNameArea(nameArea);
        areaBean.setUuid(uuid);
        return areaBean;
    }

}
