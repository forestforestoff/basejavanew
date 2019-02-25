package ru.javaops.webapp.model;


import ru.javaops.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class ExperienceList implements Serializable {

    private static final long serialVersionUID = 1L;
    private Link link;
    private List<Experience> experienceList;

    public ExperienceList(String name, String url, Experience... experiences) {
        this(new Link(name, url), Arrays.asList(experiences));
    }

    public ExperienceList(Link link, List<Experience> experienceList) {
        Objects.requireNonNull(link, "link must not be null");
        Objects.requireNonNull(experienceList, "experienceList must not be null");
        this.link = link;
        this.experienceList = experienceList;
    }

    public ExperienceList() {
    }

    public Link getLink() {
        return link;
    }

    public List<Experience> getExperience() {
        return experienceList;
    }

    @Override
    public String toString() {
        return "ExperienceList{" +
                "link=" + link +
                ", experienceList=" + experienceList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExperienceList that = (ExperienceList) o;
        return link.equals(that.link) &&
                experienceList.equals(that.experienceList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link, experienceList);
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Experience implements Serializable {

        private static final long serialVersionUID = 1L;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate startDate;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate endDate;
        private String description;

        public Experience(LocalDate startDate, LocalDate endDate, String description) {
            Objects.requireNonNull(startDate, "startDate must not be null");
            Objects.requireNonNull(endDate, "endDate must not be null");
            this.startDate = startDate;
            this.endDate = endDate;
            this.description = description == null ? "" : description;
        }

        public Experience() {
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return "Experience{" +
                    "startDate=" + startDate +
                    ", endDate=" + endDate +
                    ", description='" + description + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Experience that = (Experience) o;
            return Objects.equals(startDate, that.startDate) &&
                    Objects.equals(endDate, that.endDate) &&
                    Objects.equals(description, that.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(startDate, endDate, description);
        }
    }
}