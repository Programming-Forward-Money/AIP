package it.zh;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;

/**
 * 通过文件的形式，获取到股票的价格数据
 */
public class FileStockDataSource implements StockDataSource {

    private static final String suffix = ".txt";

    @Override
    public LinkedHashMap<LocalDate, StockData> getDataMap(String code, LocalDate start, LocalDate end) {
        LinkedHashMap<LocalDate, StockData> dataMap = new LinkedHashMap<>();

        InputStream asStream = this.getClass().getClassLoader().getResourceAsStream(code + suffix);
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(asStream))) {
            String line = reader.readLine();
            while (line != null){
                String[] datas = line.split("\t+");
                LocalDate date = LocalDate.parse(datas[0], DateTimeFormatter.ISO_LOCAL_DATE);
                // 在指定时间范围内
                if(isAfterStart(start, date) && isBeforeEnd(end, date)){
                    BigDecimal openPrice = new BigDecimal(datas[1].replaceAll(",",""));
                    BigDecimal closePrice = new BigDecimal(datas[2].replaceAll(",",""));
                    dataMap.put(date, StockData.of(date, openPrice, closePrice));
                }
                line = reader.readLine();
            }
        }catch (FileNotFoundException e){
            throw new RuntimeException(String.format("%s文件不存在，无法获取历史数据", code));
        }catch (Throwable e){
            throw new RuntimeException(String.format("%s文件读取失败", code), e);
        }

        return dataMap;
    }

    @Override
    public LinkedHashMap<LocalDate, StockData> getDataMap(String code) {
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
