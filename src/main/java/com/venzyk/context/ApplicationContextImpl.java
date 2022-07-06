package com.venzyk.context;

import com.venzyk.annotation.Bean;
import com.venzyk.exception.NoSuchBeanException;
import com.venzyk.exception.NoUniqueBeanException;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ClassUtils;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ApplicationContextImpl implements ApplicationContext {

    private final Map<String, Object> storage = new HashMap<>();

    @SneakyThrows
    public ApplicationContextImpl(String packageName) {
        var reflections = new Reflections(packageName);
        var classes = reflections.getTypesAnnotatedWith(Bean.class);

        for (var targetClass : classes) {
            var beanObject = targetClass.getConstructor().newInstance();
            var nameFromAnnotation = targetClass.getAnnotation(Bean.class).name();
            var beanName = nameFromAnnotation.isBlank() ? targetClass.getSimpleName() : nameFromAnnotation;

            storage.put(beanName, beanObject);
        }
    }

    @Override
    public <T> T getBean(Class<T> beanType) throws NoSuchBeanException, NoUniqueBeanException {
        var foundBeans = storage.values().stream()
                .filter(bean -> ClassUtils.isAssignable(bean.getClass(), beanType))
                .toList();
        if (foundBeans.isEmpty()) {
            throw new NoSuchBeanException();
        }
        if (foundBeans.size() > 1) {
            throw new NoUniqueBeanException();
        }
        return beanType.cast(foundBeans.get(0));
    }

    @Override
    public <T> T getBean(String name, Class<T> beanType) throws NoSuchBeanException {
        return beanType.cast(storage.get(name));
    }

    @Override
    public <T> Map<String, ? extends T> getAllBeans(Class<T> beanType) {
        return storage.entrySet()
                .stream()
                .filter(entry -> ClassUtils.isAssignable(entry.getValue().getClass(), beanType))
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> beanType.cast(entry.getValue())));
    }
}
