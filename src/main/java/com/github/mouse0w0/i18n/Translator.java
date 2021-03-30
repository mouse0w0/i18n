package com.github.mouse0w0.i18n;

import com.github.mouse0w0.i18n.source.TranslationSource;

import java.io.IOException;
import java.util.*;

public final class Translator implements TranslationSource {

    private Locale locale;

    private final TranslationSource[] sources;
    private final Map<String, String> translations = new HashMap<>();
    private final ResourceBundle resourceBundle;

    public static Builder builder() {
        return new Builder();
    }

    private Translator(Locale locale, TranslationSource[] sources) throws TranslationLoadException {
        if (sources == null) throw new NullPointerException("sources");
        this.sources = sources;
        this.resourceBundle = new TranslatorResourceBundle(this);

        setLocale(locale);
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) throws TranslationLoadException {
        if (locale == null) throw new NullPointerException("locale");
        this.locale = locale;

        for (TranslationSource source : sources) {
            try {
                source.load(locale, translations);
            } catch (IOException e) {
                throw new TranslationLoadException("Failed to load translation. Source: " + source, e);
            }
        }
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
        if (this.locale.equals(locale)) {
            translations.putAll(this.translations);
        } else {
            for (TranslationSource source : sources) {
                source.load(locale, translations);
            }
        }
    }

    public static final class Builder {
        private Locale locale = Locale.getDefault();

        private final List<TranslationSource> sources = new ArrayList<>();

        public Builder locale(Locale locale) {
            this.locale = locale;
            return this;
        }

        public Builder source(TranslationSource source) {
            this.sources.add(source);
            return this;
        }

        public Translator build() throws TranslationLoadException {
            return new Translator(locale, sources.toArray(EMPTY_ARRAY));
        }
    }
}
