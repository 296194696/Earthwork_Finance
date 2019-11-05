package com.water.irrigation.utils;

import java.math.BigDecimal;

public class Maths {
    public static double multi(Double a,Double b){
        BigDecimal x=new BigDecimal(a.toString());
        BigDecimal y=new BigDecimal(b.toString());
        return x.multiply(y).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double divide(Double a,Double b){
        BigDecimal x=new BigDecimal(a.toString());
        BigDecimal y=new BigDecimal(b.toString());
        return x.divide(y).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double add(Double a,Double b){
        BigDecimal x=new BigDecimal(a.toString());
        BigDecimal y=new BigDecimal(b.toString());
        return x.add(y).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double subtract(Double a,Double b){
        BigDecimal x=new BigDecimal(a.toString());
        BigDecimal y=new BigDecimal(b.toString());
        return x.subtract(y).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
