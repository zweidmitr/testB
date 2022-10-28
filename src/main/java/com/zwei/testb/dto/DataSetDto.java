package com.zwei.testb.dto;

import com.zwei.testb.entities.Share;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataSetDto {

    private Date time;

    private Double price;
}
