package com.zwei.testb.endpoint;

import com.zwei.testb.dto.DataSetDto;
import com.zwei.testb.entities.DataSet;
import com.zwei.testb.services.DataSetService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DataSetController {

    private final DataSetService dataSetService;

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    public ResponseEntity loadDataSets() {
        try {
            dataSetService.loadDataSets();
            return ResponseEntity.ok().body("dataSet load");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/companies/{ticker}/prices")
    public List<DataSetDto> getOne(@PathVariable("ticker") String ticker) {
        List<DataSet> dataSets = dataSetService.getDataSets(ticker);
        return dataSets.stream()
                .map(dataSet -> modelMapper.map(dataSet, DataSetDto.class))
                .collect(Collectors.toList());
    }
}
