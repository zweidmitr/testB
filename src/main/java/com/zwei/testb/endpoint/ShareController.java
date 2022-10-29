package com.zwei.testb.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zwei.testb.dto.DataSetDto;
import com.zwei.testb.dto.ShareDto;
import com.zwei.testb.entities.DataSet;
import com.zwei.testb.entities.Share;
import com.zwei.testb.services.DataSetService;
import com.zwei.testb.services.ShareService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ShareController {

    private final ShareService shareService;

    @Autowired
    private ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(ShareController.class.getSimpleName());

    @PostConstruct
    public ResponseEntity initBase() throws IOException {
        try {
            shareService.initBase();
            return ResponseEntity.ok().body("shares download");
        } catch (IOException e) {
            throw new RuntimeException("shares error");
        }
    }

    @GetMapping("/companies")
    public List<ShareDto> giveMe(@RequestParam("page") String pageSt, @RequestParam("size") String sizeSt) {
        int page = Integer.parseInt(pageSt);
        int size = Integer.parseInt(sizeSt);
        Page<Share> pageList = shareService.getPages(page, size);

        return pageList.getContent().stream()
                .map(share -> modelMapper.map(share, ShareDto.class))
                .collect(Collectors.toList());
    }
}
