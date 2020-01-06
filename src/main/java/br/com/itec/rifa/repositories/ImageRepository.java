package br.com.itec.rifa.repositories;

import br.com.itec.rifa.models.Image;
import br.com.itec.rifa.models.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends PagingAndSortingRepository<Image, Long> {

    Integer countByItem(Item item);

    @Query(value = "SELECT MAX(i.order) FROM Image i WHERE item = :item")
    Integer findMaxOrderByItem(@Param("item") Item item);

}
