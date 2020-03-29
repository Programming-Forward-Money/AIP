package it.zh.bean;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AIPData {

    private LocalDate date;
    private BigDecimal buyInPrice;
    private BigDecimal buyInCount;

    public AIPData(LocalDate date, BigDecimal buyInPrice, BigDecimal buyInCount) {
        this.date = date;
        this.buyInPrice = buyInPrice;
        this.buyInCount = buyInCount;
    }

    public static AIPData of(LocalDate date, BigDecimal buyInPrice, BigDecimal buyInCount){
        return new AIPData(date, buyInPrice, buyInCount);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getBuyInPrice() {
        return buyInPrice;
    }

    public void setBuyInPrice(BigDecimal buyInPrice) {
        this.buyInPrice = buyInPrice;
    }

    public BigDecimal getBuyInCount() {
        return buyInCount;
    }

    public void setBuyInCount(BigDecimal buyInCount) {
        this.buyInCount = buyInCount;
    }

    @Override
    public String toString() {
        return "{" +
                "每股买入价=" + buyInPrice +
                ", 买入股数=" + buyInCount +
                '}';
    }
}
