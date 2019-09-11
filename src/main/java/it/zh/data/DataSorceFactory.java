package it.zh.data;

public class DataSorceFactory {

    /**
     * 根据不同情况，返回不同数据源
     * 目前用数据文件的形式实现，后边可能增加URL网络抓取数据源
     * @return
     */
    public static StockDataSource getDataSource(){
        return new FileStockDataSource();
    }

}
