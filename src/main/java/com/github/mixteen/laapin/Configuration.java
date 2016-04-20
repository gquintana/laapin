package com.github.mixteen.laapin;

import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Configuration {
    private final Properties properties = new Properties();

    public void load(InputStream inputStream) throws IOException {
        properties.load(inputStream);
    }

    public void load() throws IOException {
        try (InputStream inputStream = getClass().getResourceAsStream("/laapin.properties")) {
            if (inputStream != null) {
                load(inputStream);
            }
        }
        File file = new File("laapin.properties");
        if (file.exists() && file.isFile()) {
            try (FileInputStream inputStream = new FileInputStream(file)) {
                load(inputStream);
            }
        }
    }

    public int getInt(String name, int defaultValue) {
        String stringValue = properties.getProperty(name);
        return stringValue == null ? defaultValue : Integer.parseInt(stringValue);
    }

    public Class getClass(String name) {
        String stringValue = properties.getProperty(name);
        if (stringValue == null) {
            return null;
        }
        try {
            return Class.forName(stringValue);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Invalid configuration " + name, e);
        }
    }

    public String getString(String name) {
        String stringValue = properties.getProperty(name);
        if (stringValue == null) {
            return null;
        }
        return stringValue.trim();
    }

    private static Pattern COLOR_PATTERN = Pattern.compile("#?([A-F0-9]{6})");

    public Color getColor(String name) {
        String stringValue = properties.getProperty(name);
        if (stringValue == null) {
            return null;
        }
        stringValue = stringValue.trim().toUpperCase();
        Matcher colorMatcher = COLOR_PATTERN.matcher(stringValue);
        if (colorMatcher.matches()) {
            return Color.web(stringValue, 1.0);
        }
        try {
            Class<Color> colorClass = Color.class;
            return colorClass.cast(colorClass.getDeclaredField(stringValue).get(colorClass));
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException("Couleur invalide " + stringValue);
        }
    }

    public boolean getBoolean(String name, boolean defaultValue) {
        String stringValue = properties.getProperty(name);
        return stringValue == null ? defaultValue : Boolean.parseBoolean(stringValue);
    }
}
