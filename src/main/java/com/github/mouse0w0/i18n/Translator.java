package com.github.mouse0w0.i18n;

import com.github.mouse0w0.i18n.source.TranslationSource;

import java.io.IOException;
import java.util.*;

public final class Translator implements TranslationSource {

    private final Locale locale;

    private final Map<String, String> translations = new HashMap<>();

    private final ResourceBundle resourceBundle;

    public static Builder builder() {
        return new Builder();
    }

    private Translator(Locale locale, List<TranslationSource> sources) {
        if (locale == null) throw new NullPointerException("locale");
        this.locale = locale;

        sources.forEach(source -> {
            try {
                source.load(locale, translations);
            } catch (IOException e) {
                throw new TranslationLoadException("Failed to load translation", e);
            }
        });

        this.resourceBundle = new TranslatorResourceBundle(this);
    }

    public Locale getLocale() {
        return locale;
    }

    public Set<String> getKeys() {
        return translations.keySet();
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public String translate(String key) {
        return translations.getOrDefault(key, key);
    }

    public String translate(String key, String defaultValue) {
        return translations.getOrDefault(key, defaultValue);
    }

    @Override
    public void load(Locale locale, Map<String, String> translations) throws IOException {
        if (getLocale().equals(locale)) {
            translations.forEach(translations::putIfAbsent);
        }
    }

    public static final class Builder {
        private Locale locale;

        private List<TranslationSource> sources = new ArrayList<>();

        public Builder locale(Locale locale) {
            this.locale = locale;
            return this;
        }

        public Builder source(TranslationSource source) {
            this.sources.add(source);
            return this;
        }

        public Translator build() {
            return new Translator(locale, sources);
        }
    }
}
