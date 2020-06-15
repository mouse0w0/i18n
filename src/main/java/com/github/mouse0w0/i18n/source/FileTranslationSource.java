package com.github.mouse0w0.i18n.source;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public class FileTranslationSource implements TranslationSource {

    private final Path path;
    private final String prefix;
    private final String suffix;
    private final Charset charset;

    public FileTranslationSource(Path path) {
        this(path, "", ".lang");
    }

    public FileTranslationSource(Path path, String prefix, String suffix) {
        this(path, prefix, suffix, StandardCharsets.UTF_8);
    }

    public FileTranslationSource(Path path, String prefix, String suffix, Charset charset) {
        this.path = Objects.requireNonNull(path, "path");
        if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException("The path must be directory. Current path: " + path.toAbsolutePath());
        }
        this.prefix = Objects.requireNonNull(prefix, "prefix");
        this.suffix = Objects.requireNonNull(suffix, "suffix");
        this.charset = Objects.requireNonNull(charset, "charset");
    }

    @Override
    public void load(Locale locale, Map<String, String> translations) throws IOException {
        Path translationFile = path.resolve(prefix + locale.toLanguageTag() + suffix);
        if (!Files.exists(translationFile)) {
            return;
        }

        try (Reader reader = new InputStreamReader(Files.newInputStream(translationFile), charset)) {
            Properties properties = new Properties();
            properties.load(reader);
            properties.forEach((key, value) -> translations.putIfAbsent(key.toString(), value.toString()));
        }
    }
}
