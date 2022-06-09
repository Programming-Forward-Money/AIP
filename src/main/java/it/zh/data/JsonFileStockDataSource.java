package it.zh.data;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.TreeMap;

import com.google.gson.Gson;

import it.zh.bean.EastStockData;
import it.zh.bean.StockData;

/**
 * @author zhangzhidong <zhangzhidong@kuaishou.com>
 * Created on 2022-06-08
 */
public class JsonFileStockDataSource implements StockDataSource{
    private static final String suffix = ".txt";
    Gson GSON = new Gson();
    @Override
    public TreeMap<LocalDate, StockData> getDataMap(String code, LocalDate start, LocalDate end) {
        TreeMap<LocalDate, StockData> dataMap = new TreeMap<>();
        String fileName = code + suffix;
        InputStream asStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
        if (asStream == null){
            throw new RuntimeException(String.format("%s文件不存在，无法获取历史数据", fileName));
        }

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(asStream))) {
            String line = reader.readLine();
            while (line != null){
                EastStockData eastStockData = GSON.fromJson(line, EastStockData.class);
                LocalDate date = LocalDate.parse(eastStockData.getDate());
                // 在指定时间范围内
                if(isAfterStart(start, date) && isBeforeEnd(end, date)){
                    BigDecimal openPrice = new BigDecimal(eastStockData.getOpen());
                    BigDecimal closePrice = new BigDecimal(eastStockData.getClose());
                    dataMap.put(date, StockData.of(date, openPrice, closePrice));
                }
                line = reader.readLine();
            }
        }catch (Exception e){
            throw new RuntimeException(String.format("%s文件读取失败", code), e);
        }

        return dataMap;
    }

    @Override
    public TreeMap<LocalDate, StockData> getDataMap(String code) {
        return null;
    }

    private boolean isBeforeEnd(LocalDate end, LocalDate date) {
        if(end == null){
            // 时间控制传null，视为没有控制
            return true;
        }

        return end.isAfter(date);
    }

    private boolean isAfterStart(LocalDate start, LocalDate date) {
        if(start == null){
            // 时间控制传null，视为没有控制
            return true;
        }

        return start.isBefore(date);
    }

    public static void main(String[] args) {

    }
}
