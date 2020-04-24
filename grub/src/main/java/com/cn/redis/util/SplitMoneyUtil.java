package com.cn.redis.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class SplitMoneyUtil {
    private Double money;
    private int amount;
    private int currentAmount;
    private double surplusMoney;
    private double currentMoney;
    private Random rand=new Random();
    private List<Double> allPackages=new ArrayList<Double>();

    public SplitMoneyUtil(int amount ,double money){
        this.amount=amount;
        this.money=money-(amount/100.00);
        this.currentAmount=amount;
        this.surplusMoney=money * 100;
        if(this.currentAmount==1){
            this.allPackages.add(money);
        }else {
            this.handle();
        }
    }
    private void handle(){
        int count=(int)this.surplusMoney / this.amount;
        int key=count * 2;
        int rand=this.rand.nextInt(key);
        this.surplusMoney -=rand;
        this.allPackages.add(rand/100.00);
        this.currentMoney +=rand;
        if(--this.currentAmount>1){
            this.handle();
        }else{
            if(this.currentAmount==1){
                this.allPackages.add(((this.money*100)-this.currentMoney)/100.00);
                return;
            }
        }
    }
    public List<Double> getAllPackages(){
        List<Double> all=new ArrayList<Double>();
        Iterator<Double> it=this.allPackages.iterator();
        while (it.hasNext()){
            double s=it.next();
            all.add(MyMath.round(s+0.01,2));
        }
        return all;
    }
}