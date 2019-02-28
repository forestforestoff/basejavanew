package ru.javaops.webapp.web;

import ru.javaops.webapp.Config;
import ru.javaops.webapp.model.*;
import ru.javaops.webapp.storage.SqlStorage;
import ru.javaops.webapp.storage.Storage;
import ru.javaops.webapp.util.ServletUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {

    private Storage storage = Config.get().getStorage();
            //new SqlStorage("jdbc:postgresql://localhost:5432/resumes", "postgres", "postgres");

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        final boolean isCreate = (uuid == null || uuid.length() == 0);
        Resume r;
        if (isCreate) {
            r = new Resume(fullName);
        } else {
            r = storage.get(uuid);
            r.setFullName(fullName);
        }
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (!ServletUtil.isEmpty(value)) {
                r.addContact(type, value);
            } else {
                r.getContactMap().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            String[] values = request.getParameterValues(type.name());
            if (ServletUtil.isEmpty(value)) {
                r.getSectionMap().remove(type);
            } else {
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        r.addSection(type, new TextSection(value));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        r.addSection(type, new ListSection(value.split("\\n")));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        List<ExperienceList> exps = new ArrayList<>();
                        for (int i = 0; i < values.length; i++) {
                            String name = values[i];
                            if (!ServletUtil.isEmpty(name)) {
                                List<ExperienceList.Experience> experiences = new ArrayList<>();
                                String[] startDates = request.getParameterValues(type.name() + i + "startDate");
                                String[] endDates = request.getParameterValues(type.name() + i +"endDate");
                                String[] descriptions = request.getParameterValues(type.name() + i + "description");
                                for (int j = 0; j < descriptions.length; j++) {
                                    if (!ServletUtil.isEmpty(descriptions[j])) {
                                        experiences.add(new ExperienceList.Experience(LocalDate.parse(startDates[j]), LocalDate.parse(endDates[j]), descriptions[j]));
                                    }
                                }
                                String[] urls = request.getParameterValues(type.name() + "url");
                                exps.add(new ExperienceList(new Link(name, urls[i]), experiences));
                            }
                        }
                        r.addSection(type, new ExperienceSection(exps));
                        break;
                }
            }
        }
        if (isCreate) {
            storage.save(r);
        } else {
            storage.update(r);
        }
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "add":
                r = Resume.EMPTY;
                break;
            case "view":
                r = storage.get(uuid);
                break;
            case "edit":
                r = storage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    AbstractSection section = r.getSection(type);
                    switch (type) {
                        case OBJECTIVE:
                        case PERSONAL:
                            if (section == null) {
                                section = TextSection.EMPTY;
                            }
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            if (section == null) {
                                section = ListSection.EMPTY;
                            }
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            ExperienceSection orgSection = (ExperienceSection) section;
                            List<ExperienceList> emptyFirstOrganizations = new ArrayList<>();
                            emptyFirstOrganizations.add(ExperienceList.EMPTY);
                            if (orgSection != null) {
                                for (ExperienceList org : orgSection.getExperienceList()) {
                                    List<ExperienceList.Experience> emptyFirstPositions = new ArrayList<>();
                                    emptyFirstPositions.add(ExperienceList.Experience.EMPTY);
                                    emptyFirstPositions.addAll(org.getExperience());
                                    emptyFirstOrganizations.add(new ExperienceList(org.getLink(), emptyFirstPositions));
                                }
                            }
                            section = new ExperienceSection(emptyFirstOrganizations);
                            break;
                    }
                    r.addSection(type, section);
                }
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }
}