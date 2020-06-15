package com.github.mouse0w0.i18n.source;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ClasspathFolderTranslationSource implements TranslationSource {

    private final ClassLoader classLoader;
    private final String path;
    private final String extensionName;
    private final Charset charset;

    public ClasspathFolderTranslationSource(String path) {
        this(Utils.getClassLoader(Utils.getCallerClass()), path);
    }

    public ClasspathFolderTranslationSource(ClassLoader classLoader, String path) {
        this(classLoader, path, ".lang", StandardCharsets.UTF_8);
    }

    public ClasspathFolderTranslationSource(String path, String extensionName) {
        this(Utils.getClassLoader(Utils.getCallerClass()), path, extensionName);
    }

    public ClasspathFolderTranslationSource(ClassLoader classLoader, String path, String extensionName) {
        this(classLoader, path, extensionName, StandardCharsets.UTF_8);
    }

    public ClasspathFolderTranslationSource(ClassLoader classLoader, String path, String extensionName, Charset charset) {
        this.classLoader = Objects.requireNonNull(classLoader, "classLoader");
        this.path = Objects.requireNonNull(path, "path");
        this.extensionName = Objects.requireNonNull(extensionName, "extensionName");
        this.charset = Objects.requireNonNull(charset, "charset");
    }

    @Override
    public void load(Locale locale, Map<String, String> translations) throws IOException {
        URL translationFolderURL = classLoader.getResource(path + "/" + Utils.toString(locale));
        if (translationFolderURL == null) return;

        Path translationFolder;
        try {
            translationFolder = Paths.get(translationFolderURL.toURI());
        } catch (URISyntaxException ignored) {
            return;
        }
        if (!Files.isDirectory(translationFolder)) return;

        Iterator<Path> iterator = Files.walk(translationFolder).iterator();
        while (iterator.hasNext()) {
            Path file = iterator.next();
            if (Files.isDirectory(file)) continue;
            if (!file.getFileName().toString().endsWith(extensionName)) continue;

            try (Reader reader = new InputStreamReader(Files.newInputStream(file), charset)) {
                Properties properties = new Properties();
                properties.load(reader);
                properties.forEach((key, value) -> translations.putIfAbsent(key.toString(), value.toString()));
            }
        }
    }
}
