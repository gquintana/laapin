package com.github.mixteen.laapin;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class ResourcesTest {

    private static InputStream close(InputStream inputStream) throws IOException {
        if (inputStream != null) {
            inputStream.close();
        }
        return inputStream;
    }
    @Test
    public void testOpen_Classpath() throws Exception {
        assertThat(close(Resources.openStream("classpath:affichage/lapin-16.png")), is(notNullValue()));
        assertThat(close(Resources.openStream("classpath:/laapin1.properties")), is(notNullValue()));
    }

    @Test(expected = FileNotFoundException.class)
    public void testOpen_ClasspathNotFound() throws Exception {
        Resources.openStream("classpath:unknown.txt");
    }

    @Test
    public void testOpen_File() throws Exception {
        assertThat(close(Resources.openStream("file:src/main/resources/laapin1.properties")), is(notNullValue()));
        assertThat(close(Resources.openStream("file://"+new File("src/main/resources/laapin1.properties").getAbsolutePath())), is(notNullValue()));
        assertThat(close(Resources.openStream("src/main/resources/laapin1.properties")), is(notNullValue()));
        assertThat(close(Resources.openStream(new File("src/main/resources/laapin1.properties").getAbsolutePath())), is(notNullValue()));
    }

    @Test(expected = FileNotFoundException.class)
    public void testOpen_FileNotFound() throws Exception {
        Resources.openStream("file:unknown.txt");
    }
    @Test(expected = FileNotFoundException.class)
    public void testOpen_FileNotFound2() throws Exception {
        Resources.openStream("unknown.txt");
    }

}