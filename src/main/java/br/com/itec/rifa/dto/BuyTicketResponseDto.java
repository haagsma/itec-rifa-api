package br.com.itec.rifa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BuyTicketResponseDto {

    private String message;
    private Boolean success;
    private Long saldo;

    public BuyTicketResponseDto(String message) {
        this.message = message;
    }
    public BuyTicketResponseDto(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }
}
