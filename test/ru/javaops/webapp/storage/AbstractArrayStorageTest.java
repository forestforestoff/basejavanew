package ru.javaops.webapp.storage;

import org.junit.Assert;
import org.junit.Test;
import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        try {
            for (int i = 4; i <= AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume("" + i));
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            Assert.fail("ArrayIndexOutOfBoundsException");
        }
        storage.save(new Resume("overflow"));
    }
}