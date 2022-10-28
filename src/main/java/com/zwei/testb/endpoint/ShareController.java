package com.zwei.testb.endpoint;

import com.zwei.testb.dto.DataSetDto;
import com.zwei.testb.dto.ShareDto;
import com.zwei.testb.entities.DataSet;
import com.zwei.testb.entities.Share;
import com.zwei.testb.services.DataSetService;
import com.zwei.testb.services.ShareService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ShareController {

    private final ShareService shareService;
    private final DataSetService dataSetService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/")
    public ResponseEntity test() throws IOException {
        try {
            if(6>5) {
                throw new IllegalArgumentException("Invalid username or password");
            }
            return ResponseEntity.ok("it works");
        } catch (Exception e) {
            throw new RuntimeException("application error");
        }
    }

    @GetMapping("/init")
    public ResponseEntity initBase() throws IOException {
        try {
            shareService.initBase();
            return ResponseEntity.ok().body("shares download");
        } catch (IOException e) {
            throw new RuntimeException("error");
        }
    }


    @GetMapping("/companies")
    public List<ShareDto> giveMe(HttpServletRequest request) {
        int page = Integer.parseInt(request.getParameter("page"));
        int size = Integer.parseInt(request.getParameter("size"));
        Page<Share> pageList = shareService.getPages(page, size);

        return pageList.getContent().stream()
                .map(share -> modelMapper.map(share, ShareDto.class))
                .collect(Collectors.toList());
    }


}
