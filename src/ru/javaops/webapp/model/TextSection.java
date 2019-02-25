package ru.javaops.webapp.model;

import java.util.Objects;

public class TextSection extends AbstractSection {

    public static final TextSection EMPTY = new TextSection("");
    private static final long serialVersionUID = 1L;
    private String text;

    public TextSection(String text) {
        Objects.requireNonNull(text, "text must not be null");
        this.text = text;
    }

    public TextSection() {
    }

    public String getText() {
        return text;
    }

    @Override
    public String getSection() {
        return getText();
    }

    @Override
    public String toString() {
        return "TextSection{" +
                "text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSection that = (TextSection) o;
        return text.equals(that.text);
    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }
}