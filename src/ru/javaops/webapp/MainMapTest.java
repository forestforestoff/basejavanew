package ru.javaops.webapp;

import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.storage.MapResumeStorage;

public class MainMapTest {

    private static final MapResumeStorage MAP_STORAGE = new MapResumeStorage();

    public static void main(String[] args) {
        Resume resume1 = new Resume("111","riki");
        Resume resume2 = new Resume("222","tiki");
        Resume resume3 = new Resume("333", "tavi");
        MAP_STORAGE.save(resume1);
        MAP_STORAGE.save(resume2);
        MAP_STORAGE.save(resume3);

        System.out.println("Get r1: " + MAP_STORAGE.get(resume1.getUuid()));
        System.out.println("Size: " + MAP_STORAGE.size());
        System.out.println("\n");

        printAll();
        System.out.println("\n");

        MAP_STORAGE.delete(resume1.getUuid());
        printAll();
        System.out.println("\n");
        MAP_STORAGE.clear();
        printAll();
        System.out.println("\n");
        System.out.println("Size: " + MAP_STORAGE.size());
    }

    private static void printAll() {
        for (Resume resume : MAP_STORAGE.getAllSorted()) {
            System.out.println(resume);
        }
    }
}