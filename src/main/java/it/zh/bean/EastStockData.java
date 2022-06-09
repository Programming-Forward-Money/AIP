package it.zh.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangzhidong <zhangzhidong@kuaishou.com>
 * Created on 2022-06-08
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EastStockData {
    private String date;
    private double open;
    private double close;
    private double high;
    private double low;
    private double volume;
    private double amount;
    private double amplitude;
    private double changeAmplitude;
    private double changeAmount;
    private double exchangeRatio;
}
