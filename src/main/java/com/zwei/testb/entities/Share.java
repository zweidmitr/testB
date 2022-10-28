package com.zwei.testb.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "shares")
@AllArgsConstructor
@RequiredArgsConstructor
public class Share {

    @Id
    @Column(name = "ticker")
    private String ticker;

    @Column(name = "companie")
    private String company;

    @Column(name = "sector")
    private String sector;

    @Column(name = "industry")
    private String industry;

    @Column(name = "last_price")
    private Double lastPrice;

    @JsonIgnore
    @OneToMany(mappedBy = "share", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DataSet> dataSets = new ArrayList<>();

}
