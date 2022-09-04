package com.command;

import com.annotation.Autowired;
import com.annotation.Singleton;
import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Annotation implements Command {
    final Map<Class<?>, Object> CACHE = new HashMap<>();

    @SneakyThrows
    private Object getNewInstanceWithEmptyConstructor(Class<?> clazz) {
        Constructor constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }

    @SneakyThrows
    private Object getNewInstanceWithOneArgument(Class<?> clazz, Object argument) {
        Constructor constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance(argument);
    }

    @Override
    public void execute() {
        Reflections reflections = new Reflections("com");

        final Set<Class<?>> singletonClasses = reflections.getTypesAnnotatedWith(Singleton.class);
        singletonClasses.forEach(singletonClass -> {
            System.out.println("Class " + singletonClass.getName());
            Constructor<?>[] constructors = singletonClass.getDeclaredConstructors();
            Arrays.stream(constructors).forEach(c -> {
                        c.setAccessible(true);
                        if (c.isAnnotationPresent(Autowired.class) && c.getParameterCount() == 1) {
                            Class<?>[] parameterTypes = c.getParameterTypes();
                            Arrays.stream(parameterTypes).forEach(parameter -> {
                                try {
                                    if (CACHE.containsKey(parameter)) {

                                        Object newInstance = c.newInstance(CACHE.get(parameter));
                                        CACHE.put(newInstance.getClass(), newInstance);
                                    } else {
                                        CACHE.put(parameter, getNewInstanceWithEmptyConstructor(parameter));
                                        Object newInstance = c.newInstance(CACHE.get(parameter));
//                                        Object newInstance = getNewInstanceWithOneArgument(c.getDeclaringClass(), CACHE.get(parameter));
                                        CACHE.put(newInstance.getClass(), newInstance);
                                    }
                                } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            });
                        } else {
                            try {
                                c.setAccessible(true);
                                Object newInstance = c.newInstance();
                                CACHE.put(newInstance.getClass(), newInstance);
                            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            );
        });
        System.out.println(CACHE);
//        autowiredConstructors.forEach(c -> System.out.println(c.getName()));
//        System.out.println(autowiredConstructors);


//            final Field[] fields = aClass.getDeclaredFields();
//            System.out.println("Has fields " + Arrays.toString(fields));
//            Arrays.stream(fields).forEach(f -> {
//                f.setAccessible(true);
//            });
//                try {
//
//                    Object o = aClass.getConstructor();
////                    System.out.println("Get type: "+f.getType());
////                    System.out.println("Get generic type: "+f.getGenericType());
////                    System.out.println("Get declared class: "+f.getDeclaringClass());
////                    System.out.println(f.getName() + ": " + f.get(f.getType()));
//                } catch (NoSuchMethodException e) {
//                    e.printStackTrace();
//                }

//            System.out.println(fields[0].getChar());


//        CACHE.stream().map(Object::getClass)
//                                    .forEach(cached -> {
//                                        for (Class clazz : parameterTypes) {
//                                            try {
//                                                if (clazz == cached) {
//                                                    CACHE.add(c.newInstance(cached));
//                                                }
//                                            } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
//                                                e.printStackTrace();
//                                            }
//                                        }
//                                    });
//                            System.out.println(CACHE);
//                        } else {
//                            try {
//                                CACHE.add(c.newInstance());
//                            } catch (InstantiationException e) {
//                                e.printStackTrace();
//                            } catch (IllegalAccessException e) {
//                                e.printStackTrace();
//                            } catch (InvocationTargetException e) {
//                                e.printStackTrace();
//                            }
//                        }

    }
}
