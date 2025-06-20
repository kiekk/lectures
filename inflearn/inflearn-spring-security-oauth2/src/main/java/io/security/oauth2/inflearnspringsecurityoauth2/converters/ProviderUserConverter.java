package io.security.oauth2.inflearnspringsecurityoauth2.converters;

public interface ProviderUserConverter<T, R> {
    R converter(T t);
}
