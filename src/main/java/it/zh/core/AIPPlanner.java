package it.zh.core;


import it.zh.bean.AIPCycle;
import it.zh.bean.AIPData;
import it.zh.bean.StockData;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

/**
 * 定投器
 * 决定周期(月、周、天)、每个周期的第几天、定投多少钱....
 */
public class AIPPlanner {

    /** 定投周期 */
    private final AIPCycle cycle;
    /** 每次定投多少钱 */
    private final BigDecimal budgetMoney;

    private AIPPlanner(AIPCycle cycle, BigDecimal budgetMoney){
        this.cycle = cycle;
        this.budgetMoney = budgetMoney;
    }

    public static AIPPlanner getInstance(AIPCycle cycle, BigDecimal budgetMoney){
        return new AIPPlanner(cycle, budgetMoney);
    }

    /**
     * 返回值是所有定投的时间点，和对应的买入价、买入数量
     * @param dataMap   股票的历史数据
     * @return
     */
    public TreeMap<LocalDate, AIPData> plan(TreeMap<LocalDate, StockData> dataMap){
        TreeMap<LocalDate, AIPData> result = new TreeMap<>();

        Iterator<Map.Entry<LocalDate, StockData>> iterator = dataMap.entrySet().iterator();
        Set<String> cycleCache = new HashSet<>();
        while (iterator.hasNext()){
            Map.Entry<LocalDate, StockData> data = iterator.next();
            if(shouldBuy(data, cycleCache)){
                BigDecimal openPrice = data.getValue().getOpenPrice();
                // 用预算额度算出这次，最多能买多少份
                BigDecimal count = budgetMoney.divide(openPrice, 0, BigDecimal.ROUND_DOWN);
                result.put(data.getKey(), AIPData.of(data.getKey(), openPrice, count));
            }
        }

        return result;
    }

    /**
     * 今天是否应该定投，每个周期应该只定投一次
     * @param data
     * @param cache
     * @return
     */
    private boolean shouldBuy(Map.Entry<LocalDate, StockData> data, Set<String> cache) {
        LocalDate date = data.getKey();
        String cacheKey = date.format(cycle.getFormatter());
        if(!cache.contains(cacheKey)){
            cache.add(cacheKey);
            return true;
        }
        return false;
    }

}
