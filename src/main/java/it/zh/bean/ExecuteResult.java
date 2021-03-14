package it.zh.bean;

import java.math.BigDecimal;

/**
 * @author zhanghao12 <zhanghao12@kuaishou.com>
 * Created on 2021/3/14
 */
public class ExecuteResult {

    private BigDecimal totalEarn = BigDecimal.ZERO;
    private BigDecimal totalCost = BigDecimal.ZERO;

    public static ExecuteResult of(BigDecimal totalEarn, BigDecimal totalCost){
        ExecuteResult executeResult = new ExecuteResult();
        executeResult.setTotalCost(totalCost);
        executeResult.setTotalEarn(totalEarn);
        return executeResult;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public BigDecimal getTotalEarn() {
        return totalEarn;
    }

    public void setTotalEarn(BigDecimal totalEarn) {
        this.totalEarn = totalEarn;
    }
}
