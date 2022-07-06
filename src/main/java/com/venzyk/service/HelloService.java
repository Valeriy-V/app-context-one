package com.venzyk.service;

import com.venzyk.annotation.Bean;

@Bean(name ="Hello")
public class HelloService implements MainService{
    @Override
    public void doSmt() {
        System.out.println("Hello");
    }
}

