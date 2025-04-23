package utez.edu.mx.communitycommitteesystem.controller.state;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import utez.edu.mx.communitycommitteesystem.controller.person.PersonDto;
import utez.edu.mx.communitycommitteesystem.model.person.PersonBean;
import utez.edu.mx.communitycommitteesystem.model.state.StateBean;

@Getter
@Setter
public class StateWithAdminDto extends PersonDto {
    @NotBlank
    private String stateName;


    public StateBean toEntity(){
        StateBean stateBean = new StateBean();
        PersonBean person = getPersonBean();
        stateBean.setPersonBean(person);
        stateBean.setNameState(stateName);
        return stateBean;

    }






}
