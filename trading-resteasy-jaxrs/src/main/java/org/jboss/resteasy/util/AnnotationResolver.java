package org.jboss.resteasy.util;

import java.lang.annotation.Annotation;

public class AnnotationResolver {

    public static Class getClassWithAnnotation(Class<?> clazz, Class<? extends Annotation> annotation) {

        Class cls = getInterfaceWithAnnotation(clazz, annotation);

        if (cls != null) {
            return cls;
        }

        Class superClass = clazz.getSuperclass();

        if (superClass != Object.class && superClass != null) {
            return getClassWithAnnotation(superClass, annotation);
        }

        return null;
    }

    private static Class getInterfaceWithAnnotation(Class clazz, Class<? extends Annotation> annotation) {

        if (clazz.isAnnotationPresent(annotation)) {
            return clazz;
        }

        for (Class intf : clazz.getInterfaces()) {

            Class inhIntf = getInterfaceWithAnnotation(intf, annotation);

            if (inhIntf != null) {
                return inhIntf;
            }

        }

        return null;
    }
}
