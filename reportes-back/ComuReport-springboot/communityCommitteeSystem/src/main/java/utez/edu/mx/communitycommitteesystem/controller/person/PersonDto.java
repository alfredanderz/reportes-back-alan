package utez.edu.mx.communitycommitteesystem.controller.person;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import utez.edu.mx.communitycommitteesystem.model.person.PersonBean;



@NoArgsConstructor
@Getter
@Setter
public class PersonDto {
    @NotBlank
    private String name;
    @NotBlank
    private String lastname;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String phone;

    public PersonBean getPersonBean(){
        PersonBean personBean = new PersonBean();
        personBean.setName(name);
        personBean.setLastname(lastname);
        personBean.setEmail(email);
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        String encryptedPsw = bcrypt.encode(password);
        personBean.setPassword(encryptedPsw);
        personBean.setPhone(phone);
        return personBean;
    }
}
