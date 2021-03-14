package it.zh.data;

import it.zh.bean.StockData;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.TreeMap;

/**
 * 股票/指数的数据源接口
 */
public interface StockDataSource {

    /**
     * 获取start-end之间范围的每天的股票数据
     * 返回的Map需要有序，所以用LinkedHashMap
     * @param code
     * @param start
     * @param end
     * @return
     */
    TreeMap<LocalDate, StockData> getDataMap(String code, LocalDate start, LocalDate end);

    /**
     * 获取所有天数的股票数据
     * 返回的Map需要有序，所以用LinkedHashMap
     * @param code
     * @return
     */
    TreeMap<LocalDate, StockData> getDataMap(String code);

}
