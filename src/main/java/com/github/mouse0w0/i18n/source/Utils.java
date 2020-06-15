package com.github.mouse0w0.i18n.source;

class Utils {

    public static ClassLoader getClassLoader(Class<?> clazz) {
        return clazz != null ? clazz.getClassLoader() : Thread.currentThread().getContextClassLoader();
    }

    public static Class<?> getCallerClass() {
        try {
            return Class.forName(Thread.currentThread().getStackTrace()[3].getClassName());
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
