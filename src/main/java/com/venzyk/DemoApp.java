package com.venzyk;

import com.venzyk.context.ApplicationContextImpl;
import com.venzyk.service.MainService;
import lombok.SneakyThrows;

public class DemoApp {
    @SneakyThrows
    public static void main(String[] args) {
        var context = new ApplicationContextImpl("com.venzyk");
        var service = context.getBean(MainService.class);
        service.doSmt();
    }
}
