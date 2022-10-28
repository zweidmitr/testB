package com.zwei.testb.endpoint;

import com.zwei.testb.entities.DataSet;
import com.zwei.testb.entities.Share;
import com.zwei.testb.services.DataSetService;
import com.zwei.testb.services.ShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ShareController {

    private final ShareService shareService;
    private final DataSetService dataSetService;

    @GetMapping("/")
    public ResponseEntity test() throws IOException {
        try {
            shareService.initBase();
            return ResponseEntity.ok("it works");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("init error");
        }
    }

    @GetMapping("/init")
    public ResponseEntity initBase() throws IOException {
        try {
            shareService.initBase();
            return ResponseEntity.ok("shares download");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error");
        }
    }


    @GetMapping("/companies/{ticker}/prices")
    public List<DataSet> getOne(@PathVariable("ticker") String ticker) {
        return dataSetService.getDataSets(ticker);
    }

    @GetMapping("/companies")
    public List<Share> giveMe(HttpServletRequest request) {
        int page = Integer.parseInt(request.getParameter("page"));
        int size = Integer.parseInt(request.getParameter("size"));
        Page<Share> pageList = shareService.getPages(page, size);

        return pageList.getContent();
    }


}
