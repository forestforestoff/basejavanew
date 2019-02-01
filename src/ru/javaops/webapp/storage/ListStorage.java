package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;


public class ListStorage extends AbstractStorage<Integer> {

    private List<Resume> resumeList = new ArrayList<>();

    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < resumeList.size(); i++) {
            if (resumeList.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void clear() {
        resumeList.clear();
    }

    @Override
    protected boolean elementIsExist(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    protected void saveElement(Resume resume, Integer searchKey) {
        resumeList.add(resume);
    }

    @Override
    protected void updateElement(Resume resume, Integer searchKey) {
        resumeList.set(searchKey, resume);
    }

    @Override
    protected Resume getElement(Integer searchKey) {
        return resumeList.get(searchKey);
    }

    @Override
    protected void deleteElement(Integer searchKey) {
        resumeList.remove(searchKey.intValue());
    }

    @Override
    protected List<Resume> getList() {
        return new ArrayList<>(resumeList);
    }

    @Override
    public int size() {
        return resumeList.size();
    }
}