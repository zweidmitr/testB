package com.zwei.testb.services;

import com.zwei.testb.entities.DataSet;
import com.zwei.testb.entities.Share;
import com.zwei.testb.repository.DataSetRepository;
import com.zwei.testb.repository.ShareRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataSetService {
    private final DataSetRepository dataSetRepo;
    private final ShareRepository shareRepo;

    public void loadDataSets() {
        int time = 1000 * 20; // 1000*60*10;
        Thread run = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        List<String> tickers = getTickers();
                        for (String tik : tickers) {
                            parseJson(tik);
                        }

                        Thread.sleep(time);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        run.start();
    }

    public List<DataSet> getDataSets(String ticker) {
        return dataSetRepo.findByTicker(ticker);
    }

    public List<String> getTickers() {
        return shareRepo.findAllShare().stream().map(Share::getTicker).toList();
    }
    public void parseJson(String ticker) throws MalformedURLException, ParseException {
        String urlString = String.format("https://query1.finance.yahoo.com/v8/finance/chart/%s", ticker);
        Object obj = new JSONParser().parse(stream(urlString));
        JSONObject jo = (JSONObject) obj;
        JSONObject chart = (JSONObject) jo.get("chart");
        JSONArray result = (JSONArray) chart.get("result");
        JSONObject nolik = (JSONObject) result.get(0);
        JSONObject meta = (JSONObject) nolik.get("meta");
        Double price = (Double) meta.get("regularMarketPrice");
        Long time = (Long) meta.get("regularMarketTime");
        String symbol = (String) meta.get("symbol");

        Share sh = shareRepo.findById(symbol).get();
        sh.setLastPrice(price);

        DataSet dataSet = new DataSet(sh, price, new Timestamp(time * 1000));
        shareRepo.save(sh);
        dataSetRepo.save(dataSet);
    }


    public static String stream(String urlS) throws MalformedURLException {
        URL url = new URL(urlS);
        try (InputStream input = url.openStream()) {
            InputStreamReader isr = new InputStreamReader(input);
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder json = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                json.append((char) c);
            }
            return json.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
