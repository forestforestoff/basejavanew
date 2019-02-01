package ru.javaops.webapp.storage;

import ru.javaops.webapp.storage.serializers.ObjectSerializationStrategy;

public class ObjectPathStorageTest extends AbstractStorageTest {
    public ObjectPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectSerializationStrategy()));
    }
}