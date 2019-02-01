package ru.javaops.webapp.model;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Comparable<Resume>, Serializable {

    private static final long serialVersionUID = 1L;
    private String uuid;
    private String fullName;
    private final Map<SectionType, AbstractSection> sectionMap = new EnumMap<>(SectionType.class);
    private final Map<ContactType, Contact> contactMap = new EnumMap<>(ContactType.class);

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public Resume() {
    }

    public Map<SectionType, AbstractSection> getSectionMap() {
        return sectionMap;
    }

    public Map<ContactType, Contact> getContactMap() {
        return contactMap;
    }


    public void addSection(SectionType type, AbstractSection section) {
        sectionMap.put(type, section);
    }

    public void addContact(ContactType type, Contact contact) {
        contactMap.put(type, contact);
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName) &&
                Objects.equals(sectionMap, resume.sectionMap) &&
                Objects.equals(contactMap, resume.contactMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, sectionMap, contactMap);
    }

    @Override
    public String toString() {
        return uuid + " " + fullName;
    }

    @Override
    public int compareTo(Resume o) {
        int c = uuid.compareTo(o.uuid);
        return c != 0 ? c : fullName.compareTo(o.fullName);
    }
}