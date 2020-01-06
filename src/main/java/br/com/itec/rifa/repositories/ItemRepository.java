package br.com.itec.rifa.repositories;

import br.com.itec.rifa.models.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends PagingAndSortingRepository<Item, Long> {

    Page<Item> findAllByStatusTag(String tag, Pageable pageable);

}
