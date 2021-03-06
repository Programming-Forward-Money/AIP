package it.zh.data;

import it.zh.bean.StockData;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.TreeMap;

/**
 * 通过文件的形式，获取到股票的价格数据
 */
public class FileStockDataSource implements StockDataSource {

    private static final String suffix = ".txt";
    private static final DateTimeFormatter isoLocalDate = DateTimeFormatter.ofPattern("yyyy年M月d日");

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
                String[] dataList = line.split("\t+");
                LocalDate date = LocalDate.parse(dataList[0], isoLocalDate);
                // 在指定时间范围内
                if(isAfterStart(start, date) && isBeforeEnd(end, date)){
                    BigDecimal openPrice = new BigDecimal(dataList[1].replaceAll(",",""));
                    BigDecimal closePrice = new BigDecimal(dataList[2].replaceAll(",",""));
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
        return getDataMap(code, null, null);
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


}
