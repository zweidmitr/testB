package com.zwei.testb.endpoint;

import com.zwei.testb.services.DataSetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class DataSetController {

    private final DataSetService dataSetService;

    @GetMapping("/api")
    public ResponseEntity loadDataSets() {
        try {
            dataSetService.loadDataSets();
            return ResponseEntity.ok().body("dataSet works");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("DataSets error");
        }
    }
}
