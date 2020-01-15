package br.com.itec.rifa.repositories;

import br.com.itec.rifa.models.Item;
import br.com.itec.rifa.models.Ticket;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {

    Integer countByItem(Item item);

    List<Ticket> findByUserId(Long id);

    List<Ticket> findByUserIdAndItemStatusTag(Long id, String tag);

    @Query(value = "SELECT MAX(t.num) FROM Ticket t WHERE t.item = :item")
    Integer findMaxByItem(@Param("item") Item item);

    @Query(value = "SELECT user_id FROM ticket where item_id = :id ORDER BY RAND() LIMIT 1",
            nativeQuery = true)
    Long sortTicket(@Param("id") Long id);
}
