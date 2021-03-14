package it.zh.core;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import it.zh.bean.AIPData;
import it.zh.bean.ExecuteResult;

/**
 * 目前只是按照定投策略简单模拟定投并计算
 * 后面要按照周期，算出每周期内所有组合的定投结果
 * @author zhanghao12 <zhanghao12@kuaishou.com>
 * Created on 2021/3/14
 */
public class PlanExecutor {

    /**
     * 定投的收益的计算方式：
     * Σ(最终卖出价 - 每个定投点买入的价)*每个定投点买入的数量
     */
    public static ExecuteResult doPlainAndSell(TreeMap<LocalDate, AIPData> aipDataMap){
        BigDecimal totalEarn = BigDecimal.ZERO;
        BigDecimal totalCost = BigDecimal.ZERO;
        BigDecimal finalPrice = aipDataMap.lastEntry().getValue().getBuyInPrice();
        Iterator<Entry<LocalDate, AIPData>> iterator = aipDataMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<LocalDate, AIPData> next = iterator.next();
            AIPData aipData = next.getValue();
            BigDecimal buyInPrice = aipData.getBuyInPrice();
            BigDecimal buyInCount = aipData.getBuyInCount();

            totalCost = totalCost.add(buyInPrice.multiply(buyInCount));
            totalEarn = totalEarn.add(finalPrice.subtract(buyInPrice).multiply(buyInCount));
        }
        return ExecuteResult.of(totalEarn, totalCost);
    }


}
