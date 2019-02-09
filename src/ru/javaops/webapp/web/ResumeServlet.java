package ru.javaops.webapp.web;

import ru.javaops.webapp.model.ContactType;
import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.model.SectionType;
import ru.javaops.webapp.storage.SqlStorage;
import ru.javaops.webapp.storage.Storage;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class ResumeServlet extends HttpServlet {

    private Storage storage = new SqlStorage("jdbc:postgresql://localhost:5432/resumes", "postgres", "postgres");

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String uuid = request.getParameter("uuid");
        Writer writer = response.getWriter();

        if (uuid == null) {

            writer.write(
                    "<html>\n" +
                            "<head>\n" +
                            "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                            "    <link rel=\"stylesheet\" href=\"css/style.css\">\n" +
                            "    <title>Резюме</title>\n" +
                            "</head>\n" +
                            "<body>\n" +
                            "<section>\n" +
                            "<table border=\"1\" cellpadding=\"8\" cellspacing=\"0\">\n" +
                            "    <tr>\n" +
                            "        <th>Имя</th>\n" +
                            "        <th>Email</th>\n" +
                            "        <th>Personal</th>\n" +
                            "    </tr>\n");
            for (Resume resume : storage.getAllSorted()) {
                writer.write(
                        "<tr>\n" +
                                "     <td><a href=\"resume?uuid=" + resume.getUuid() + "\">" + resume.getFullName() + "</a></td>\n" +
                                "     <td>" + resume.getContactMap().get(ContactType.E_MAIL) + "</td>\n" + "     <td>" + resume.getSectionMap().get(SectionType.PERSONAL) + "</td>\n" +
                                "</tr>\n");
            }
            writer.write("</table>\n" +
                    "</section>\n" +
                    "</body>\n" +
                    "</html>\n");
        } else {
            response.getWriter().write(storage.get(uuid).toString());
        }

    }
}