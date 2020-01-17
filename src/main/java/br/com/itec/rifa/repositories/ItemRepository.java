package br.com.itec.rifa.repositories;

import br.com.itec.rifa.models.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends PagingAndSortingRepository<Item, Long> {

    Page<Item> findAllByStatusTag(String tag, Pageable pageable);

    @Query(value = "select i.* from item i\n" +
            "inner join status s on i.status_id = s.id\n" +
            "\twhere (select count(*) from ticket t where i.id = t.item_id)  < i.max_people and s.tag = :tag\n" +
            "and not exists(select ti.* from ticket ti where ti.user_id = :id and ti.item_id = i.id)", nativeQuery = true)
    Page<Item> findAllToViewApp(@Param("tag") String tag, @Param("id") Long id, Pageable pageable);

}
