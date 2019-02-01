package ru.javaops.webapp;

import java.io.File;

public class MainFile {

    public static void main(String[] args) {
        checkFiles(new File("C:\\Users\\Марк\\IdeaProjects\\basejava"), "");
    }

    private static void checkFiles(File directory, String indent) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    System.out.println(indent + file.getName() + ".file");
                } else if (file.isDirectory()) {
                    System.out.println(indent + file.getName() + ".directory");
                    checkFiles(file, indent + "  ");
                }
            }
        }
    }
}