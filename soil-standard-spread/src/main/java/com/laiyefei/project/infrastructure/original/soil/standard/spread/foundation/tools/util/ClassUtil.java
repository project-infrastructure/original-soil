package com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.util;

import com.alibaba.spring.util.AnnotationUtils;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.co.IModule;
import org.reflections.Reflections;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-03-15 15:55
 * @memo : this is util about class
 * @Version : v1.0.0.20200315
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public class ClassUtil {

    public static <T> Set<Class<? extends T>> GainClassBy(Class<T> clazz) {
        return GainClassBy(IModule.GROUP_ID, clazz);
    }

    public static <T> Set<Class<? extends T>> GainClassBy(String packageName, Class<T> clazz) {
        final Reflections reflections = new Reflections(packageName);
        return reflections.getSubTypesOf(clazz);
    }

    public static final <T> List<T> DigEnumBy(Class<T> clazz) {
        return DigEnumBy(IModule.GROUP_ID, clazz);
    }

    public static final <T> List<T> DigEnumBy(String packageName, Class<T> clazz) {
        final Set<Class<? extends T>> classes = ClassUtil.GainClassBy(packageName, clazz);
        for (final Class<? extends T> item : classes) {
            if (!item.isEnum()) {
                System.out.println("warning: sorry, the class named [" + item.getSimpleName() + "] is not enum class.");
                continue;
            }
            final T[] lists = item.getEnumConstants();
            if (JudgeUtil.IsNull(lists)) {
                System.out.println("warning: sorry, the class named [" + item.getSimpleName() + "] is not have constant.");
                continue;
            }
            return Arrays.asList(lists.clone());
        }
        return new ArrayList<>();
    }

    public static final <T> List<T> GetBeansOrderedBy(ApplicationContext context, Class<T> clazz) {
        return Optional.of(context.getBeansOfType(clazz)).orElse(new HashMap<>())
                .entrySet().stream().map(e -> e.getValue())
                .sorted((first, second) -> {
                    if (!first.getClass().isAnnotationPresent(Order.class)) {
                        return 1;
                    }
                    if (!second.getClass().isAnnotationPresent(Order.class)) {
                        return -1;
                    }
                    return first.getClass().getAnnotation(Order.class).value() -
                            second.getClass().getAnnotation(Order.class).value();
                })
                .collect(Collectors.toList());
    }

    public static Annotation gainMergedAnnotation(AnnotatedElement annotatedElement,
                                                  Class<? extends Annotation> annotationType) {
        return AnnotationUtils.tryGetMergedAnnotation(annotatedElement, annotationType);
    }

    public static boolean isAnnotationPresent(AnnotatedElement annotatedElement,
                                              Class<? extends Annotation> annotationType) {
        return JudgeUtil.IsNotNULL(gainMergedAnnotation(annotatedElement, annotationType));
    }
}
