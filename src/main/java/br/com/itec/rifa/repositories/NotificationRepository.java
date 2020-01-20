package br.com.itec.rifa.repositories;

import br.com.itec.rifa.models.Notification;
import br.com.itec.rifa.models.Status;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends PagingAndSortingRepository<Notification, Long> {

    List<Notification> findAllByToUserIdOrToUserIsNullOrderByIdDesc(Long id);

    @Query("SELECT count(n.id) FROM Notification n " +
            "WHERE (n.toUser is null or n.toUser.id = :id) " +
            "AND n.status.tag in (:tags)")
    Integer countByToUserIdOrToUserIsNullAndStatusTagIn(@Param("id") Long id, @Param("tags") List<String> tags);

    @Modifying
    @Query("UPDATE Notification n set n.status = :status WHERE n.toUser.id = :id")
    void setStatusByToUserId(@Param("status") Status status, @Param("id") Long id);

}
