package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.storage.serializers.StorageStrategy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {

    private File directory;
    private StorageStrategy storageStrategy;

    protected FileStorage(File directory, StorageStrategy storageStrategy) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory() || !directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory or is not readable/writable");
        }
        this.directory = directory;
        this.storageStrategy = storageStrategy;
    }

    @Override
    protected boolean elementIsExist(File file) {
        return file.exists();
    }

    @Override
    protected File getSearchKey(String key) {
        return new File(directory, key);
    }

    @Override
    protected void saveElement(Resume resume, File file) {
        try {
            file.createNewFile();
            storageStrategy.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void updateElement(Resume resume, File file) {
        try {
            storageStrategy.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File write error", file.getName(), e);
        }
    }

    @Override
    protected Resume getElement(File file) {
        try {
            return storageStrategy.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", file.getName(), e);
        }
    }

    @Override
    protected void deleteElement(File file) {
        if (!file.delete()) {
            throw new StorageException("File delete error", file.getName());
        }
    }

    @Override
    protected List<Resume> getList() {
        File[] files = directory.listFiles();
        List<Resume> list = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                list.add(getElement(file));
            }
        }
        return list;
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
    }

    @Override
    public int size() {
        File[] files = directory.listFiles();
        return files != null ? files.length : 0;
    }
}