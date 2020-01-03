package br.com.itec.rifa.repositories;

import br.com.itec.rifa.dto.UserDto;
import br.com.itec.rifa.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query(value = "SELECT u FROM User u WHERE u.name = 'Jhonatan'")
    List<User> findForName();

    User findByEmail(String email);
}
