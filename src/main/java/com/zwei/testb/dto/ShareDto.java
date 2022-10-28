package com.zwei.testb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShareDto {

    private String ticker;

    private String company;

    private String sector;

    private String industry;

    private Double lastPrice;
}
