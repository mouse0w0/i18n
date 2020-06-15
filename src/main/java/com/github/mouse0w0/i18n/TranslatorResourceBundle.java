package com.github.mouse0w0.i18n;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.ResourceBundle;

final class TranslatorResourceBundle extends ResourceBundle {
    private final Translator translator;

    public TranslatorResourceBundle(Translator translator) {
        this.translator = translator;
    }

    @Override
    protected Object handleGetObject(String key) {
        return translator.translate(key);
    }

    @Override
    public Enumeration<String> getKeys() {
        return new Enumeration<String>() {
            private final Iterator<String> iterator = translator.getKeys().iterator();

            @Override
            public boolean hasMoreElements() {
                return iterator.hasNext();
            }

            @Override
            public String nextElement() {
                return iterator.next();
            }
        };
    }
}
