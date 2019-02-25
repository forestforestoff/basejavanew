<%@ page import="ru.javaops.webapp.model.ContactType" %>
<%@ page import="ru.javaops.webapp.model.TextSection" %>
<%@ page import="ru.javaops.webapp.model.ExperienceSection" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javaops.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <h3>Имя:</h3>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <hr>
        <c:forEach var="sectionMap" items="${resume.sectionMap}">
            <jsp:useBean id="sectionMap"
                         type="java.util.Map.Entry<ru.javaops.webapp.model.SectionType, ru.javaops.webapp.model.AbstractSection>"/>
            <c:set var="type" value="${sectionMap.key}"/>
            <jsp:useBean id="type" type="ru.javaops.webapp.model.SectionType"/>
            <c:set var="section" value="${sectionMap.value}"/>
            <jsp:useBean id="section" type="ru.javaops.webapp.model.AbstractSection"/>
            <h2><a>${type.title}</a></h2>
            <c:choose>
                <c:when test="${type=='PERSONAL' || type=='OBJECTIVE'}">
                    <textarea name='${type}' cols=60 rows=3><%=((TextSection) section).getText()%></textarea>
                </c:when>
                <c:when test="${type=='QUALIFICATIONS' || type=='ACHIEVEMENT'}">
                    <textarea name='${type}' cols=60 rows=6><%=section.getSection()%></textarea>
                </c:when>
                <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                    <c:forEach var="expli" items="<%=((ExperienceSection) section).getExperienceList()%>">
                        <p><p style="font-size:20px"><b>Название учереждения:</b><br>
                            <input type="text" name='${type}place' size=73 value="${expli.link.title}">
                        </p>
                        <p><b>Сайт учереждения:</b><br>
                            <input type="text" name='${type}url' size=73 value="${expli.link.url}">
                        </p>
                        <c:forEach var="exp" items="${expli.experience}">
                            <jsp:useBean id="exp" type="ru.javaops.webapp.model.ExperienceList.Experience"/>
                            <p><b>Начальная дата:</b>
                                <input type="text" name="${type}startDate" size=6
                                       value="<%=exp.getStartDate()%>">&emsp;
                                <b>Конечная дата:</b>
                                <input type="text" name="${type}endDate" size=6
                                       value="<%=exp.getEndDate()%>">
                            </p>
                            <p><b>Описание:</b><br>
                                <textarea name="${type}description" rows=5
                                          cols=75>${exp.description}</textarea>
                            </p>
                        </c:forEach>
                        <hr width="550" align="left">
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
        <br>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
    <p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>