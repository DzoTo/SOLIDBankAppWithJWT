package kz.singularity.bankappdelivery.dto;

import kz.singularity.bankappdelivery.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class UserDTO {
    String username;

    public UserDTO(User user){
        this.username = user.getUsername();
    }
}
