package br.com.itec.rifa.dto;

import br.com.itec.rifa.models.Item;
import br.com.itec.rifa.models.User;
import lombok.Data;

@Data
public class BuyTicketRequestDto {

    private User user;
    private Item item;
}
