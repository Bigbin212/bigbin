package com.bigbincome.bigbin;

import java.time.Month;
import java.util.EnumMap;

import static java.time.Month.*;

public class WorkingDays {
    //固定个数的map使用枚举map效率更高
    private static final EnumMap<Month,Integer> MAP = new EnumMap<Month, Integer>(Month.class);
    static {
        MAP.put(JANUARY,15);
        MAP.put(FEBRUARY,16);
        MAP.put(MARCH,17);
        MAP.put(APRIL,18);
        MAP.put(MAY,19);
        MAP.put(JUNE,20);
        MAP.put(JULY,21);
        MAP.put(AUGUST,22);
        MAP.put(SEPTEMBER,23);
        MAP.put(NOVEMBER,24);
        MAP.put(OCTOBER,25);
        MAP.put(DECEMBER,26);
    }

    public static Integer get(Month month){
        return MAP.get(month);
    }

    public static Integer get(Integer month){
        return MAP.get(Month.of(month));
    }
}
