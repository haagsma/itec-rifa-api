package br.com.itec.rifa.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "item")
public class Item implements Serializable {

    /**
     * id	int(11)
     * title	varchar(150)
     * description	text
     * owner	int(11)
     * winner	int(11)
     * price	int(11)
     * max_people	int(11)
     * status_id	int(11)
     * create_date	timestamp
     */

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner")
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner")
    private User winner;
    private Long price;

    @Column(name = "max_people")
    private Integer maxPeople;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private Status status;

    @Column(name = "create_date", columnDefinition = "timestamp default current_timestamp")
    private Date createDate;

    @Column(name = "update_date")
    private Date updateDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item")
    private List<Image> images;
 }
