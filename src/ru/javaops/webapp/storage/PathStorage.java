package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.storage.serializers.StorageStrategy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {

    private Path directory;
    private StorageStrategy storageStrategy;

    protected PathStorage(String dir, StorageStrategy storageStrategy) {
        Objects.requireNonNull(dir, "directory must not be null");
        directory = Paths.get(dir);
        if (!Files.isDirectory(directory) || !Files.isWritable(directory) || !Files.isReadable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable/readable");
        }
        this.storageStrategy = storageStrategy;
    }

    @Override
    protected boolean elementIsExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected Path getSearchKey(String key) {
        return directory.resolve(key);
    }

    @Override
    protected void saveElement(Resume resume, Path path) {
        try {
            Files.createFile(path);
            storageStrategy.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("IO error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void updateElement(Resume resume, Path path) {
        try {
            storageStrategy.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path write error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected Resume getElement(Path path) {
        try {
            return storageStrategy.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path read error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void deleteElement(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Path delete error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected List<Resume> getList() {
        return getFilesStream().map(this::getElement).collect(Collectors.toList());
    }

    @Override
    public void clear() {
        getFilesStream().forEach(this::deleteElement);
    }

    @Override
    public int size() {
        return (int) getFilesStream().count();
    }

    private Stream<Path> getFilesStream() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Directory read error", null, e);
        }
    }
}