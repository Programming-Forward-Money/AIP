package it.zh;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App {

    public static void main( String[] args ){
        LinkedHashMap<LocalDate, StockData> dataMap = DataSorceFactory.getDataSource().getDataMap("yili", LocalDate.of(2018, 1, 1), LocalDate.of(2019, 4, 1));
        AIPMachine instance = AIPMachine.getInstance(AIPCycle.MONTH, BigDecimal.valueOf(2000));
        LinkedHashMap<LocalDate, AIPData> aipDataMap = instance.doAip(dataMap);

        Iterator<Map.Entry<LocalDate, AIPData>> iterator = aipDataMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<LocalDate, AIPData> next = iterator.next();
            System.out.println(next.getKey() + "->" + next.getValue());
        }
    }

}
