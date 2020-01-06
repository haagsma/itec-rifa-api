package br.com.itec.rifa.dto;

import lombok.Data;

@Data
public class UserRequestLoginDto {

    private String email;
    private String password;
    private String name;
}
