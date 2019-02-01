package ru.javaops.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;
    private Link contactLink;
    private String contact;

    public Contact(Link contactLink, String contact) {
        Objects.requireNonNull(contact, "contact must not be null");
        Objects.requireNonNull(contactLink, "contact must not be null");
        this.contactLink = contactLink;
        this.contact = contact;
    }

    public Contact() {
    }

    public Link getContactLink() {
        return contactLink;
    }

    public String getContact() {
        return contact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact1 = (Contact) o;
        return contactLink.equals(contact1.contactLink) &&
                contact.equals(contact1.contact);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactLink, contact);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "contactLink=" + contactLink +
                ", contact='" + contact + '\'' +
                '}';
    }
}