package br.com.itec.rifa.dto;

import lombok.Data;

@Data
public class UserRequestLoginDto {

    private Long id;
    private String email;
    private String password;
    private String name;
}
