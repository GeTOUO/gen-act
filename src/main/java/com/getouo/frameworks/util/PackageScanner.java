package com.getouo.frameworks.util;

import com.google.protobuf.Descriptors;
import com.google.protobuf.Message;
import org.reflections.Reflections;
//import org.reflections.scanners.Scanner;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class PackageScanner {

    public static <T> Set<Class<? extends T>> loadClass(final String pkg, final Class<T> clz) {
//        Reflections f = new Reflections(pkg, new Scanner[0]);
        Reflections f = new Reflections(pkg);
        Set<Class<? extends T>> subTypesOf = f.getSubTypesOf(clz);
        return subTypesOf.stream().filter(t -> t.getName().startsWith(pkg)).collect(Collectors.toSet());
    }

    public static Set<Descriptors.Descriptor> loadProtoMessageDescriptors(Set<Class<? extends Message>> msgCls) {
        Set<Descriptors.Descriptor> descriptors = new HashSet<>();
        msgCls.forEach(t -> {
            try {
                Constructor<? extends Message> constructor = t.getDeclaredConstructor();
                constructor.setAccessible(true);
                Descriptors.Descriptor descriptor = constructor.newInstance().getDescriptorForType();
                descriptors.add(descriptor);
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException exp) {
                exp.printStackTrace();
            }
        });
        return descriptors;
    }
}
