package com.github.mouse0w0.i18n.source;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

public interface TranslationSource {
    void load(Locale locale, Map<String, String> translations) throws IOException;
}
