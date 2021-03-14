package it.zh.core;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

import it.zh.bean.AIPData;
import it.zh.bean.ExecuteResult;

/**
 * 目前是打印在控制台中，后面有复杂的定投结果之后打印成表格
 * @author zhanghao12 <zhanghao12@kuaishou.com>
 * Created on 2021/3/14
 */
public class AIPPrinter {

    public static void print(TreeMap<LocalDate, AIPData> planDataMap, ExecuteResult executeResult) {
        // 打印出每一次定投
        Iterator<Entry<LocalDate, AIPData>> iterator = planDataMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<LocalDate, AIPData> next = iterator.next();
            System.out.println(next.getKey() + "->" + next.getValue());
        }


        /**
         * 打印最终结果
         */
        String template = "定投起始:%s至%s，定投覆盖了%s天(%s月，%s年)\r\n总成本：%s，总收益：%s，总收益率：%s";
        LocalDate firstDay = planDataMap.firstKey();
        LocalDate lastDay = planDataMap.lastKey();
        long durationDays = lastDay.toEpochDay() - firstDay.toEpochDay();
        Object[] variables = {
                firstDay,
                lastDay,
                durationDays,
                durationDays / 30.0,
                durationDays / 365.0,
                executeResult.getTotalCost(),
                executeResult.getTotalEarn(),
                executeResult.getTotalEarn()
                        .divide(executeResult.getTotalCost() , 10, BigDecimal.ROUND_HALF_DOWN)
                        .multiply(BigDecimal.valueOf(100))+"%"
        };
        System.out.println(String.format(template, variables));
    }

}
