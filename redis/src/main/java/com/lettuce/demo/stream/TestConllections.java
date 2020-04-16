package com.lettuce.demo.stream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TestConllections {
    public static void main(String[] args) {
        List list=new ArrayList();
        double array[]={122,12,88};
        for(int i=0;i<array.length;i++){
            list.add(new Double(array[i]));
        }
        Collections.sort(list);
        for (int i=0;i<array.length;i++){
            System.out.println(list.get(i));
        }
    }
}
