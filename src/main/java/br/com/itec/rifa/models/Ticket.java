package br.com.itec.rifa.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "ticket")
public class Ticket implements Serializable {
    /**
     * id	int(11)
     * item_id	int(11)
     * user_id	int(11)
     * create_date	timestamp
     */

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "create_date", columnDefinition = "timestamp default current_timestamp")
    private Date createDate;

    private Integer num;
}
