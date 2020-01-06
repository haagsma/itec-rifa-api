package br.com.itec.rifa.controllers;

import br.com.itec.rifa.dto.BuyTicketRequestDto;
import br.com.itec.rifa.services.CreditsService;
import br.com.itec.rifa.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/ticket")
@RestController
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/buy")
    @Transactional
    public ResponseEntity<?> buyTicket(@RequestBody BuyTicketRequestDto buyTicketRequestDto) {
        String res = ticketService.buyTicket(buyTicketRequestDto.getUser(), buyTicketRequestDto.getItem());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getTicketsByUser(@PathVariable Long id) {
        return new ResponseEntity<>(ticketService.getTicketsByUser(id), HttpStatus.OK);
    }
}
