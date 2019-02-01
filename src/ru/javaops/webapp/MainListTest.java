package ru.javaops.webapp;

import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.storage.ListStorage;

public class MainListTest {

    private static final ListStorage LIST_STORAGE = new ListStorage();

    public static void main(String[] args) {
        Resume resume1 = new Resume("uuid1");
        Resume resume2 = new Resume("uuid2");
        Resume resume3 = new Resume("uuid3");
        LIST_STORAGE.save(resume1);
        LIST_STORAGE.save(resume2);
        LIST_STORAGE.save(resume3);

        System.out.println("Get r1: " + LIST_STORAGE.get(resume1.getUuid()));
        System.out.println("Size: " + LIST_STORAGE.size());
        System.out.println("\n");

        printAll();
        System.out.println("\n");

        LIST_STORAGE.delete(resume1.getUuid());
        printAll();
        System.out.println("\n");
        LIST_STORAGE.clear();
        printAll();
        System.out.println("\n");
        System.out.println("Size: " + LIST_STORAGE.size());
    }

    private static void printAll() {
        for (Resume resume : LIST_STORAGE.getAllSorted()) {
            System.out.println(resume);
        }
    }
}