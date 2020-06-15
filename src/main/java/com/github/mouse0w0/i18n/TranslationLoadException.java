package com.github.mouse0w0.i18n;

public final class TranslationLoadException extends RuntimeException {
    public TranslationLoadException(String message) {
        super(message);
    }

    public TranslationLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
