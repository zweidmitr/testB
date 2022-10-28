package com.zwei.testb.services;

import com.zwei.testb.entities.Share;
import com.zwei.testb.repository.ShareRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;

@Service
@RequiredArgsConstructor
public class ShareService {
    private final ShareRepository shareRepo;

    public void initBase() throws IOException {
        Document page = getPage();
        Element table = page.select("table[class=table-light]").first();
        Elements value = table.select("td[class=screener-body-table-nw]");

        Share[] shares = new Share[20];
        for (int i = 0; i < shares.length; i++) {
            shares[i] = new Share();
        }
        int temp = 0;
        int index = 0;
        for (Element name : value) {
            String elem = name.childNode(0).childNode(0).toString();

            temp++;

            if (temp % 12 == 0) {
                temp = 1;
                index++;
            }
            if (temp == 2) {
                shares[index].setTicker(elem);
            }
            if (temp == 3) {
                shares[index].setCompany(elem);
            }
            if (temp == 4) {
                shares[index].setSector(elem);
            }
            if (temp == 5) {
                shares[index].setIndustry(elem);
            }
        }

        for (Share share : shares) {
            shareRepo.save(share);
        }
    }

    public Page<Share> getPages(int page, int size) {
        Pageable giveMePage = PageRequest.of(page,size);
        Page allShares =  shareRepo.findAll(giveMePage);
        return allShares;
    }

    private Document getPage() throws IOException {
        String url = "https://finviz.com/screener.ashx?v=111&f=idx_sp500&o=-marketcap";
        Document page = Jsoup.parse(new URL(url), 3000);
        return page;
    }
}
