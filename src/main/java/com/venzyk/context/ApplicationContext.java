package com.venzyk.context;

import com.venzyk.exception.NoSuchBeanException;
import com.venzyk.exception.NoUniqueBeanException;

import java.util.Map;

public interface ApplicationContext {
    <T> T getBean(Class<T> beanType) throws NoSuchBeanException, NoUniqueBeanException;
    <T> T getBean(String name, Class<T> beanType) throws NoSuchBeanException;
    <T> Map<String, ? extends T> getAllBeans(Class<T> beanType);
}
