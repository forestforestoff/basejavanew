package ru.javaops.webapp.storage.serializers;

import ru.javaops.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataStreamSerializer implements StorageStrategy {
    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            writeCollection(dos, r.getContactMap().entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            writeCollection(dos, r.getSectionMap().entrySet(), entry -> {
                SectionType sectionType = entry.getKey();
                AbstractSection abstractSection = entry.getValue();
                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((TextSection) abstractSection).getText());
                        break;
                    case ACHIEVEMENT:
                    case EDUCATION:
                        writeCollection(dos, ((ListSection) abstractSection).getList(), dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case QUALIFICATIONS:
                        writeCollection(dos, ((ExperienceSection) abstractSection).getExperienceList(), elem -> {
                            dos.writeUTF(elem.getLink().getTitle());
                            dos.writeUTF(elem.getLink().getUrl());
                            writeCollection(dos, elem.getExperienceList(), exp -> {
                                writeLocalDate(dos, exp.getStartDate());
                                writeLocalDate(dos, exp.getEndDate());
                                dos.writeUTF(exp.getDescription());
                            });
                        });
                        break;
                }
            });


        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readElement(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readElement(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(sectionType, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        resume.addSection(sectionType, new ListSection(readCollection(dis, dis::readUTF)));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        resume.addSection(sectionType, new ExperienceSection(
                                readCollection(dis, () -> new ExperienceList(
                                        new Link(dis.readUTF(), dis.readUTF()),
                                        readCollection(dis, () -> new ExperienceList.Experience(
                                                readLocalDate(dis), readLocalDate(dis), dis.readUTF()))
                                ))
                        ));
                        break;
                }
            });
            return resume;
        }
    }

    private void writeLocalDate(DataOutputStream dos, LocalDate localDate) throws IOException {
        dos.writeInt(localDate.getYear());
        dos.writeInt(localDate.getMonth().getValue());
    }

    private LocalDate readLocalDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), 1);
    }

    private interface Writer<T> {
        void write(T t) throws IOException;

    }

    private interface Reader<T> {
        T read() throws IOException;
    }

    private interface ElementReader {
        void elread() throws IOException;
    }

    private <T> void writeCollection(DataOutputStream dos, Collection<T> collection, Writer<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T element : collection) {
            writer.write(element);
        }
    }

    private <T> List<T> readCollection(DataInputStream dis, Reader<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }
        return list;
    }

    private void readElement(DataInputStream dis, ElementReader elementReader) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            elementReader.elread();
        }
    }
}