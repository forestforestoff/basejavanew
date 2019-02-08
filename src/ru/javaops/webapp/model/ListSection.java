package ru.javaops.webapp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ListSection extends AbstractSection {

    private static final long serialVersionUID = 1L;
    private List<String> list;

    public ListSection(List<String> list) {
        Objects.requireNonNull(list, "list must not be null");
        this.list = list;
    }

    public ListSection(String... items) {
        this(Arrays.asList(items));
    }

    public ListSection() {
    }

    public List<String> getList() {
        return list;
    }

    @Override
    public String getSection() {
        return String.join("\n", getList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return list.equals(that.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list);
    }

    @Override
    public String toString() {
        return "ListSection{" +
                "list=" + list +
                '}';
    }
}