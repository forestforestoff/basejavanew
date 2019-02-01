package ru.javaops.webapp.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArrayStorageTest.class,
        ListStorageTest.class,
        MapUuidStorageTest.class,
        SortedArrayStorageTest.class,
        MapResumeStorageTest.class,
        ObjectFileStorageTest.class,
        ObjectPathStorageTest.class,
        JsonPathStorageTest.class,
        XmlPathStorageTest.class,
        DataPathStorageTest.class,
        SqlStorageTest.class
})
public class AllStorageTest {
}