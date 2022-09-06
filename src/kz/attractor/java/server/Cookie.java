package kz.attractor.java.server;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

public class Cookie<V> {
    private final String name;
    private final V value;
    private Integer maxAge;
    private boolean httpOnly;

    public Cookie(String name, V value) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(value);
        this.name = name.strip();
        this.value = value;
    }

    public static <V> Cookie make(String name, V value) {
        return new Cookie<>(name, value);
    }

    public void setMaxAge(Integer maxAgeInSeconds) {
        this.maxAge = maxAgeInSeconds;
    }

    public void setHttpOnly(boolean httpOnly) {
        this.httpOnly = httpOnly;
    }

    private V getValue() { return value; }
    private Integer getMaxAge() { return maxAge; }
    private String getName() { return name; }
    private boolean isHttpOnly() { return httpOnly; }
    public static Map<String, String> parse(String cookieString){
        return Utils.parseUrlEncoded(cookieString, "; ");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Charset utf8 = StandardCharsets.UTF_8;
        String encName = URLEncoder.encode(getName().strip(), utf8);
        String stringValue = getValue().toString();
        String encValue = URLEncoder.encode(stringValue, utf8);
        sb.append(String.format("%s=%s", encName, encValue));
        if (getMaxAge() != null) {
            sb.append(String.format("; Max-Age=%s", getMaxAge()));
        }
        if (isHttpOnly()) {
            sb.append("; HttpOnly");
        }
        return sb.toString();
    }
}