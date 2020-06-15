package com.github.mouse0w0.i18n;

import com.github.mouse0w0.i18n.source.ClasspathFileTranslationSource;
import com.github.mouse0w0.i18n.source.ClasspathFolderTranslationSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;

class I18nTest {

    @Test
    void testI18n() {
        Translator translator = Translator.builder().locale(Locale.US)
                .source(new ClasspathFileTranslationSource("lang"))
                .source(new ClasspathFolderTranslationSource("lang_folder"))
                .build();
        Assertions.assertEquals("Hello", translator.translate("file.hello"));
        Assertions.assertEquals("Hello", translator.translate("folder.hello"));
    }
}
