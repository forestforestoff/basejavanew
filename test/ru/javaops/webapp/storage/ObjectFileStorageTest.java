package ru.javaops.webapp.storage;

import ru.javaops.webapp.storage.serializers.ObjectSerializationStrategy;

public class ObjectFileStorageTest extends AbstractStorageTest {
    public ObjectFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectSerializationStrategy()));
    }
}