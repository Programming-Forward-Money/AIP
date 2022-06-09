package it.zh;

import it.zh.bean.AIPCycle;
import it.zh.bean.AIPData;
import it.zh.bean.ExecuteResult;
import it.zh.bean.StockData;
import it.zh.core.AIPPlanner;
import it.zh.core.AIPPrinter;
import it.zh.core.PlanExecutor;
import it.zh.data.DataSorceFactory;
import it.zh.data.StockDataSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.TreeMap;

public class App {

    public static void main( String[] args ){
        String code = "上证50";
        LocalDate start = null;
        LocalDate end = null;
//        start = LocalDate.of(2015, 1, 1);
//        end = LocalDate.of(2021, 3, 1);

        // 获取股票历史数据
        StockDataSource dataSource = DataSorceFactory.getDataSource();
        TreeMap<LocalDate, StockData> dataMap = dataSource.getDataMap(code, start, end);

        // 获取定投策略
        BigDecimal budgetMoney = BigDecimal.valueOf(100000000);
        AIPPlanner planner = AIPPlanner.getInstance(AIPCycle.MONTH, budgetMoney);
        TreeMap<LocalDate, AIPData> planDataMap = planner.plan(dataMap);

        // 根据定投策略开始模拟定投并计算出结果
        ExecuteResult executeResult = PlanExecutor.doPlainAndSell(planDataMap);

        // 打印结果
        AIPPrinter.print(planDataMap, executeResult);
    }
}
