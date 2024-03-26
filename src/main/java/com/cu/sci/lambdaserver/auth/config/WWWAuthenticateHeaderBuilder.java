package com.cu.sci.lambdaserver.auth.config;

import java.util.Map;
import java.util.StringJoiner;

/**
 * WWWAuthenticateHeaderBuilder is a utility class that provides a method to compute the WWW-Authenticate header value.
 */
public final class WWWAuthenticateHeaderBuilder {

    /**
     * This method computes the WWW-Authenticate header value.
     * It takes a map of parameters and constructs a string in the format of "Bearer key1="value1", key2="value2", ...".
     *
     * @param parameters A map of parameters for the WWW-Authenticate header
     * @return A string representing the WWW-Authenticate header value
     */
    public static String computeWWWAuthenticateHeaderValue(Map<String, String> parameters) {
        StringJoiner wwwAuthenticate = new StringJoiner(", ", "Bearer ", "");

        if (!parameters.isEmpty()) {
            parameters.forEach((k, v) -> wwwAuthenticate.add(k + "=\"" + v + "\""));
        }

        return wwwAuthenticate.toString();
    }
}