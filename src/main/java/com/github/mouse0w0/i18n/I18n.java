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

    public static ResourceBundle toResourceBundle() {
        return translator.toResourceBundle();
    }

    public static String translate(String translationKey) {
        return translator.translate(translationKey);
    }

    private I18n() {
        throw new AssertionError();
    }
}
