package com.example.repositorypattern.checkstyle;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class UnusedImportBug {

    private static final Set<String> FOO;
    static {
        FOO = new HashSet<>();
        FOO.add(HashMap[].class.getName());
    }
}
