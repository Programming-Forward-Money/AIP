package it.zh.bean;

import java.math.BigDecimal;

public class AIPData {

    private BigDecimal buyInPrice;
    private BigDecimal buyInCount;

    public AIPData(BigDecimal buyInPrice, BigDecimal buyInCount) {
        this.buyInPrice = buyInPrice;
        this.buyInCount = buyInCount;
    }

    public static AIPData of(BigDecimal buyInPrice, BigDecimal buyInCount){
        return new AIPData(buyInPrice, buyInCount);
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
        return "AIPData{" +
                "buyInPrice=" + buyInPrice +
                ", buyInCount=" + buyInCount +
                '}';
    }
}
