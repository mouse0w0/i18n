package com.github.mouse0w0.i18n.source;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

public interface TranslationSource {
    TranslationSource[] EMPTY_ARRAY = new TranslationSource[0];

    void load(Locale locale, Map<String, String> translations) throws IOException;
}
