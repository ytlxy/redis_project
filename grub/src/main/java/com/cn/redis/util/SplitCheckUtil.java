package com.cn.redis.util;

import java.util.Iterator;
import java.util.List;

public class SplitCheckUtil {
    private SplitCheckUtil(){}

    public static boolean check(List<Double> pasks,double money){
        double sum=0.0;
        Iterator<Double> iter=pasks.iterator();
        while (iter.hasNext()){
            sum+=iter.next();
        }
        return MyMath.round(sum,2)==money;
    }
}
