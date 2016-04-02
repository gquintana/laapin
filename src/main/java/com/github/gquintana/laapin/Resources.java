package com.github.gquintana.laapin;

import java.io.*;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Resources {

    private static Pattern URI_PATTERN = Pattern.compile("^([a-z]+:)(.*)$");

    /**
     * Charge un fichier depuis le classpath ou le système de fichier
     */
    public static InputStream openStream(String name) throws IOException {
        InputStream inputStream;
        Matcher matcher = URI_PATTERN.matcher(name);
        if (matcher.matches()) {
            String scheme = matcher.group(1);
            String path = matcher.group(2);
            if (scheme.equals("classpath:")) {
                inputStream = Resources.class.getResourceAsStream(path);
                if (inputStream == null) {
                    throw new FileNotFoundException("Resource " + path + " not found in classpath");
                }
            } else {
                inputStream = new URL(name).openStream();
            }
        } else {
            return new FileInputStream(name);
        }
        return inputStream;
    }

    /**
     * Charge un fichier depuis le classpath ou le système de fichier
     */
    public static Reader openReader(String name) throws IOException {
        return new InputStreamReader(openStream(name), "UTF-8");
    }

}
