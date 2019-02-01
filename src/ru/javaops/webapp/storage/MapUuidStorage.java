package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage<String> {

    private Map<String, Resume> resumeMap = new HashMap<>();

    @Override
    public void clear() {
        resumeMap.clear();
    }

    @Override
    protected boolean elementIsExist(String uuid) {
        return resumeMap.containsKey(uuid);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected void saveElement(Resume resume, String uuid) {
        resumeMap.put(uuid, resume);
    }

    @Override
    protected void updateElement(Resume resume, String uuid) {
        resumeMap.replace(uuid, resume);
    }

    @Override
    protected Resume getElement(String uuid) {
        return resumeMap.get(uuid);
    }

    @Override
    protected void deleteElement(String uuid) {
        resumeMap.remove(uuid);
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