package br.com.itec.rifa.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "notifications")
public class Notification implements Serializable {

    /**
     * id	int(11)
     * title	varchar(255)
     * message	text
     * to_user	int(11)
     * item_id	int(11)
     * status_id	int(11)
     * type	varchar(50)
     * create_date	timestamp
     */

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user")
    private User toUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private Status status;

    private String type;

    @Column(name = "create_date", columnDefinition = "timestamp default current_timestamp")
    private Date createDate;


}
