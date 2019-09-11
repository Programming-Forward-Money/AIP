package it.zh;

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


}
