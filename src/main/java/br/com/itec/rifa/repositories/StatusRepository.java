package br.com.itec.rifa.repositories;

import br.com.itec.rifa.models.Status;
import br.com.itec.rifa.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusRepository extends CrudRepository<Status, Long> {

    Status findByTag(String tag);
}
