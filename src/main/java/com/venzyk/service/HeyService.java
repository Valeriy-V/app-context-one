package com.venzyk.service;

import com.venzyk.annotation.Bean;

@Bean(name ="Hey")
public class HeyService implements MainService{
    @Override
    public void doSmt() {
        System.out.println("Hey");
    }
}
