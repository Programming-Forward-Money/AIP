package it.zh.bean;

import java.time.format.DateTimeFormatter;

public enum AIPCycle {

    MONTH(DateTimeFormatter.ofPattern("yyyy-MM")),
    WEEK(DateTimeFormatter.ofPattern("yyyy-MM-W"));

    private DateTimeFormatter formatter;

    AIPCycle(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    public DateTimeFormatter getFormatter() {
        return formatter;
    }
}
