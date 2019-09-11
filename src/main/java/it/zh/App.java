package it.zh;

import it.zh.bean.AIPCycle;
import it.zh.bean.AIPData;
import it.zh.bean.StockData;
import it.zh.core.AIPMachine;
import it.zh.data.DataSorceFactory;
import it.zh.data.StockDataSource;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App {

    public static void main( String[] args ){
        String code = "yili";
        LocalDate start = null;
        LocalDate end = null;
//        LocalDate start = LocalDate.of(2018, 1, 1);
//        LocalDate end = LocalDate.of(2019, 9, 1);
        BigDecimal budgetMoney = BigDecimal.valueOf(2000);

        StockDataSource dataSource = DataSorceFactory.getDataSource();
        LinkedHashMap<LocalDate, StockData> dataMap = dataSource.getDataMap(code, start, end);
        AIPMachine instance = AIPMachine.getInstance(AIPCycle.MONTH, budgetMoney);
        LinkedHashMap<LocalDate, AIPData> aipDataMap = instance.doAip(dataMap);

        // 展示所有的定投计划，并取出第一个和最后一个定投点的数据
        AIPData firstData = null;
        AIPData lastData = null;
        Iterator<Map.Entry<LocalDate, AIPData>> iterator = aipDataMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<LocalDate, AIPData> next = iterator.next();
            firstData = firstData == null ? next.getValue() : firstData;
            lastData = next.getValue();
            System.out.println(next.getKey() + "->" + next.getValue());
        }

        /**
         * 定投的收益的计算方式：
         * Σ(最终卖出价 - 每个定投点买入的价)*每个定投点买入的数量
         */
        BigDecimal finalPrice = lastData.getBuyInPrice();
        BigDecimal totalEarn = BigDecimal.ZERO;
        BigDecimal totalCost = BigDecimal.ZERO;
        iterator = aipDataMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<LocalDate, AIPData> next = iterator.next();
            AIPData aipData = next.getValue();
            BigDecimal buyInPrice = aipData.getBuyInPrice();
            BigDecimal buyInCount = aipData.getBuyInCount();

            totalCost = totalCost.add(buyInPrice.multiply(buyInCount));
            totalEarn = totalEarn.add(finalPrice.subtract(buyInPrice).multiply(buyInCount));
        }

        System.out.println(String.format("定投覆盖了%s天，总成本：%s，总收益：%s，总收益率：%s",
                lastData.getDate().toEpochDay() - firstData.getDate().toEpochDay(),
                totalCost, totalEarn, totalEarn.divide(totalCost, 10, BigDecimal.ROUND_HALF_DOWN)));
    }

}
