package com.github.mouse0w0.i18n;

import java.util.ResourceBundle;

public final class I18n {

    private static Translator translator;

    public static Translator getTranslator() {
        return translator;
    }

    public static void setTranslator(Translator translator) {
        I18n.translator = translator;
    }

    public static ResourceBundle getResourceBundle() {
        return translator.getResourceBundle();
    }

    public static String translate(String key) {
        return translator.translate(key);
    }

    public static String translate(String key, String defaultValue) {
        return translator.translate(key, defaultValue);
    }

    public static String format(String key, Object... args) {
        return String.format(translate(key), args);
    }

    private I18n() {
        throw new AssertionError();
    }
}
