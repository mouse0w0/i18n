package com.github.mouse0w0.i18n.source;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class FolderTranslationSource implements TranslationSource {

    private final Path path;
    private final String extensionName;
    private final Charset charset;

    public FolderTranslationSource(Path path) {
        this(path, ".lang", StandardCharsets.UTF_8);
    }

    public FolderTranslationSource(Path path, String extensionName) {
        this(path, extensionName, StandardCharsets.UTF_8);
    }

    public FolderTranslationSource(Path path, String extensionName, Charset charset) {
        this.path = Objects.requireNonNull(path, "path");
        if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException("The path must be directory. Current path: " + path.toAbsolutePath());
        }
        this.extensionName = Objects.requireNonNull(extensionName, "extensionName");
        this.charset = Objects.requireNonNull(charset, "charset");
    }

    @Override
    public void load(Locale locale, Map<String, String> translations) throws IOException {
        Path translationFolder = path.resolve(locale.toLanguageTag());
        if (!Files.isDirectory(translationFolder)) {
            return;
        }

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
