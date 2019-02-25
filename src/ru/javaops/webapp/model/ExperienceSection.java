package ru.javaops.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class ExperienceSection extends AbstractSection {

    private static final long serialVersionUID = 1L;
    private List<ExperienceList> experienceList;

    public ExperienceSection(ExperienceList... experienceLists) {
        this(Arrays.asList(experienceLists));
    }

    public ExperienceSection(List<ExperienceList> experienceList) {
        Objects.requireNonNull(experienceList, "experienceList must not be null");
        this.experienceList = experienceList;
    }

    public ExperienceSection() {
    }

    public List<ExperienceList> getExperienceList() {
        return experienceList;
    }

    @Override
    public String getSection() {
        return getExperienceList().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExperienceSection that = (ExperienceSection) o;

        return experienceList.equals(that.experienceList);
    }

    @Override
    public int hashCode() {
        return experienceList.hashCode();
    }

    @Override
    public String toString() {
        return "ExperienceSection{" +
                "experienceList=" + experienceList +
                '}';
    }
}