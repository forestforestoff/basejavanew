package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {

    //private static final Logger LOG = Logger.getLogger(ArrayStorage.class.getName());

    @Override
    public void update(Resume resume) {
        //LOG.info("Update " + resume);
        updateElement(resume, getExistedSearchKey(resume.getUuid()));
    }

    @Override
    public void save(Resume resume) {
        //LOG.info("Save " + resume);
        saveElement(resume, getNotExistedSearchKey(resume.getUuid()));
    }

    @Override
    public Resume get(String uuid) {
        //LOG.info("Get " + uuid);
        return getElement(getExistedSearchKey(uuid));
    }

    @Override
    public void delete(String uuid) {
        //LOG.info("Delete " + uuid);
        deleteElement(getExistedSearchKey(uuid));
    }

    public List<Resume> getAllSorted() {
        //LOG.info("GetAllSorted");
        List<Resume> list = getList();
        Collections.sort(list);
        return list;
    }

    private SK getExistedSearchKey(String key) {
        SK searchKey = getSearchKey(key);
        if (!elementIsExist(searchKey)) {
            //LOG.warning("Resume " + key + " not exist");
            throw new NotExistStorageException(key);
        }
        return searchKey;

    }

    private SK getNotExistedSearchKey(String key) {
        SK searchKey = getSearchKey(key);
        if (elementIsExist(searchKey)) {
            //LOG.warning("Resume " + key + " already exist");
            throw new ExistStorageException(key);
        }
        return searchKey;

    }

    protected abstract boolean elementIsExist(SK searchKey);

    protected abstract SK getSearchKey(String key);

    protected abstract void saveElement(Resume resume, SK searchKey);

    protected abstract void updateElement(Resume resume, SK searchKey);

    protected abstract Resume getElement(SK searchKey);

    protected abstract void deleteElement(SK searchKey);

    protected abstract List<Resume> getList();
}