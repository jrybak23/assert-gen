package com.github.jrybak23.assertgen;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public final class ReflectionUtils {

    // copied from https://stackoverflow.com/a/9797689
    public static List<Class<?>> getCommonSuperClasses(List<? extends Class<?>> classes) {
        // start off with set from first hierarchy
        Set<Class<?>> rollingIntersect = new LinkedHashSet<>(
                getClassesBfs(classes.get(0)));
        // intersect with next
        for (int i = 1; i < classes.size(); i++) {
            rollingIntersect.retainAll(getClassesBfs(classes.get(i)));
        }
        return new LinkedList<>(rollingIntersect);
    }

    private static Set<Class<?>> getClassesBfs(Class<?> clazz) {
        Set<Class<?>> classes = new LinkedHashSet<>();
        Set<Class<?>> nextLevel = new LinkedHashSet<>();
        nextLevel.add(clazz);
        do {
            classes.addAll(nextLevel);
            Set<Class<?>> thisLevel = new LinkedHashSet<>(nextLevel);
            nextLevel.clear();
            for (Class<?> each : thisLevel) {
                Class<?> superClass = each.getSuperclass();
                if (superClass != null && superClass != Object.class) {
                    nextLevel.add(superClass);
                }
                for (Class<?> eachInt : each.getInterfaces()) {
                    nextLevel.add(eachInt);
                }
            }
        } while (!nextLevel.isEmpty());
        return classes;
    }
}
