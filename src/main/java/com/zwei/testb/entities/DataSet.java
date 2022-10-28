package com.zwei.testb.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "datasets")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataSet {

    public DataSet(Share share, Double price, Date time) {
        this.share = share;
        this.price = price;
        this.time = time;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "shares_tickers")
    private Share share;

    @Column(name = "price")
    private Double price;

    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
}
