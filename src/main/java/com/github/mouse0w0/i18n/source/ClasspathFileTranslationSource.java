package com.github.mouse0w0.i18n.source;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public class ClasspathFileTranslationSource implements TranslationSource {

    private final ClassLoader classLoader;
    private final String path;
    private final String prefix;
    private final String suffix;
    private final Charset charset;

    public ClasspathFileTranslationSource(String path) {
        this(Utils.getClassLoader(Utils.getCallerClass()), path);
    }

    public ClasspathFileTranslationSource(ClassLoader classLoader, String path) {
        this(classLoader, path, "", ".lang", StandardCharsets.UTF_8);
    }

    public ClasspathFileTranslationSource(String path, String prefix, String suffix) {
        this(Utils.getClassLoader(Utils.getCallerClass()), path, prefix, suffix);
    }

    public ClasspathFileTranslationSource(ClassLoader classLoader, String path, String prefix, String suffix) {
        this(classLoader, path, prefix, suffix, StandardCharsets.UTF_8);
    }

    public ClasspathFileTranslationSource(ClassLoader classLoader, String path, String prefix, String suffix, Charset charset) {
        this.classLoader = Objects.requireNonNull(classLoader, "classLoader");
        this.path = Objects.requireNonNull(path, "path");
        this.prefix = Objects.requireNonNull(prefix, "prefix");
        this.suffix = Objects.requireNonNull(suffix, "suffix");
        this.charset = Objects.requireNonNull(charset, "charset");
    }

    @Override
    public void load(Locale locale, Map<String, String> translations) throws IOException {
        URL resource = classLoader.getResource(path + "/" + prefix + locale.toLanguageTag() + suffix);
        if (resource == null) return;
        try (Reader reader = new InputStreamReader(resource.openStream(), charset)) {
            Properties properties = new Properties();
            properties.load(reader);
            properties.forEach((key, value) -> translations.putIfAbsent(key.toString(), value.toString()));
        }
    }
}
