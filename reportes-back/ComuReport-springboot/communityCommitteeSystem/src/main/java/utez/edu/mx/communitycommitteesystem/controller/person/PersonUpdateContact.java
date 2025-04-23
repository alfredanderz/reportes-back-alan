package utez.edu.mx.communitycommitteesystem.controller.person;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonUpdateContact {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String phone;
    private String uuid;
}
