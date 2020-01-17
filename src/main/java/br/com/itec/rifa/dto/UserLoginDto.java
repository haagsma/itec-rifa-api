package br.com.itec.rifa.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserLoginDto {

    private Long id;
    private String name;
    private String email;
    private StatusDto status;
    private Date createDate;
    private Date updateDate;
    private String token;
    private Long credits;
}
