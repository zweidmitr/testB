package com.zwei.testb.test;

import java.nio.file.Files;
import java.nio.file.Paths;

public class ResourceReader {
    public static void main(String[] args) throws Exception {
        var example = new ResourceReader();
        var content = example.readTextResource("test.json");
        String expr = "";
        System.out.println(content);
    }

    public String readTextResource(String filename) throws Exception {
        var uri = getClass().getResource(String.format("/%s", filename)).toURI();
        return Files.readString(Paths.get(uri));
    }
}
