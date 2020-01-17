package br.com.itec.rifa.services;

import br.com.itec.rifa.dto.BuyTicketResponseDto;
import br.com.itec.rifa.models.Item;
import br.com.itec.rifa.models.Ticket;
import br.com.itec.rifa.models.User;
import br.com.itec.rifa.repositories.ItemRepository;
import br.com.itec.rifa.repositories.TicketRepository;
import br.com.itec.rifa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CreditsService creditsService;

    @Autowired
    private ItemRepository itemRepository;

    public BuyTicketResponseDto buyTicket(User user, Item item) {

        user = userRepository.findById(user.getId()).get();
        item = itemRepository.findById(item.getId()).get();

        if (!verifyCanBuyTicket(item)) return new BuyTicketResponseDto("Limite de participantes atingido!", false);

        if (!creditsService.debitCredits(user, item.getPrice())) return new BuyTicketResponseDto("Saldo insuficiente!", false);

        Integer max = ticketRepository.findMaxByItem(item);
        user = userRepository.findById(user.getId()).get();
        System.out.println(max);
        Ticket ticket = new Ticket();
        ticket.setItem(item);
        ticket.setUser(user);
        ticket.setNum(max == null ? 1 : (max + 1));
        ticket.setCreateDate(new Date());

        ticketRepository.save(ticket);

        return new BuyTicketResponseDto("Desejamos boa sorte, o numero do seu ticket é " + ticket.getNum() + " !", true, user.getCredits());
    }

    public Boolean verifyCanBuyTicket(Item item) {
        return ticketRepository.countByItem(item) < item.getMaxPeople();
    }

    public List<Ticket> getTicketsByUser(Long id, String active) {
        if (active.equals("ativos")) {
            return ticketRepository.findByUserIdAndItemStatusTag(id, "EM_SORTEIO");
        } else {
            return ticketRepository.findByUserId(id);
        }
    }
}
