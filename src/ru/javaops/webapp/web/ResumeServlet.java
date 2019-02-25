package ru.javaops.webapp.web;

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

    private Storage storage = new SqlStorage("jdbc:postgresql://localhost:5432/resumes", "postgres", "postgres");

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r = storage.get(uuid);
        r.setFullName(fullName);
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
        storage.update(r);
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
            case "view":
            case "edit":
                r = storage.get(uuid);
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