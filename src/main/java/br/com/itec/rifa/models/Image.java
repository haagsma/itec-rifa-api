package br.com.itec.rifa.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "image")
public class Image implements Serializable {

    /**
     * id	int(11)
     * item_id	int(11)
     * link	text
     * order	int(11)
     */

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private String link;

    @JsonIgnore
    private String path;

    @Column(name = "`order`")
    private Integer order;

}
