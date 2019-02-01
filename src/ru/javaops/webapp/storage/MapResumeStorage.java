package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {

    private Map<String, Resume> resumeMap = new HashMap<>();

    @Override
    public void clear() {
        resumeMap.clear();
    }

    @Override
    protected boolean elementIsExist(Resume r) {
        return r != null;
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return resumeMap.get(uuid);
    }

    @Override
    protected void saveElement(Resume resume, Resume r) {
        resumeMap.put(resume.getUuid(), resume);
    }

    @Override
    protected void updateElement(Resume resume, Resume r) {
        resumeMap.replace(resume.getUuid(), resume);
    }

    @Override
    protected Resume getElement(Resume r) {
        return r;
    }

    @Override
    protected void deleteElement(Resume r) {
        resumeMap.remove(r.getUuid());
    }

    @Override
    protected List<Resume> getList() {
        return new ArrayList<>(resumeMap.values());
    }

    @Override
    public int size() {
        return resumeMap.size();
    }
}
