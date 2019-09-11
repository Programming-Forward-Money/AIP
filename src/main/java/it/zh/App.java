package it.zh;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;

/**
 * Hello world!
 *
 */
public class App {

    public static void main( String[] args ){
        LinkedHashMap<LocalDate, StockData> dataMap = DataSorceFactory.getDataSource().getDataMap("sz50");
        AIPMachine instance = AIPMachine.getInstance(AIPCycle.MONTH, BigDecimal.valueOf(2000));
        LinkedHashMap<LocalDate, AIPData> aipDataMap = instance.doAip(dataMap);


    }

}
