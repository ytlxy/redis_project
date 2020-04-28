package com.cn.redis.service;

import java.util.Map;

public interface IRedEnvelopeserice {
    public static final String PREFIX="red-";
    public static final String SUFFIX="-result";
    public String add(String userid,int amount,double money);
    public Double grab(String ket,String userid);
    public Map<String,Double> result(String key);
}