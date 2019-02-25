package ru.javaops.webapp;

import ru.javaops.webapp.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static ru.javaops.webapp.model.ContactType.E_MAIL;
import static ru.javaops.webapp.model.ContactType.SKYPE;
import static ru.javaops.webapp.model.SectionType.*;

public class ResumeTestData {

    public static void main(String[] args) {
        Resume testResume = getInstance("01", "Григорий Кислин");
        for (AbstractSection section : testResume.getSectionMap().values()) {
            System.out.println(section + "\n");
        }

    }

    public static Resume getInstance(String uuid, String fullName) {

        Resume resume = new Resume(uuid, fullName);

        ExperienceList.Experience experience_1 = new ExperienceList.Experience(LocalDate.of(2012, 4, 1), LocalDate.of(2014, 10, 1), "Java архитектор. " +
                "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO.");
        ExperienceList experienceList_1 = new ExperienceList("RIT Center", null, experience_1);
        ExperienceList.Experience experience_2_1 = new ExperienceList.Experience(LocalDate.of(1987, 9, 1), LocalDate.of(1993, 7, 1), "Инженер (программист Fortran, C)");
        ExperienceList.Experience experience_2_2 = new ExperienceList.Experience(LocalDate.of(1993, 7, 1), LocalDate.of(1996, 7, 1), "Аспирантура (программист С, С++)");
        ExperienceList experienceList_2 = new ExperienceList("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "http://www.ifmo.ru/ru/", experience_2_1, experience_2_2);
        List<ExperienceList> experienceLists = new ArrayList<>();
        experienceLists.add(experienceList_1);
        experienceLists.add(experienceList_2);
        ExperienceSection experienceSection = new ExperienceSection(experienceLists);
        TextSection textSection_1 = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        TextSection textSection_2 = new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        ArrayList<String> list = new ArrayList<>();
        list.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP.");
        list.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike.");
        ListSection listSection = new ListSection(list);

        resume.addSection(EXPERIENCE, experienceSection);
        resume.addSection(ACHIEVEMENT, listSection);
        resume.addSection(OBJECTIVE, textSection_1);
        resume.addSection(PERSONAL, textSection_2);

        resume.addContact(SKYPE, "skype");
        resume.addContact(E_MAIL, "e_mail");

        return resume;
    }
}