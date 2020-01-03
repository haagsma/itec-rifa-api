package br.com.itec.rifa.dto;

import br.com.itec.rifa.models.Status;
import br.com.itec.rifa.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String name;
    private String email;
    private StatusDto status;

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
