<%@ page import="ru.javaops.webapp.model.TextSection" %>
<%@ page import="ru.javaops.webapp.model.ListSection" %>
<%@ page import="ru.javaops.webapp.model.ExperienceSection" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
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
    <h2>${resume.fullName}<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contactMap}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.javaops.webapp.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
        <c:forEach var="sectionMap" items="${resume.sectionMap}">
            <jsp:useBean id="sectionMap"
                         type="java.util.Map.Entry<ru.javaops.webapp.model.SectionType, ru.javaops.webapp.model.AbstractSection>"/>
            <c:set var="type" value="${sectionMap.key}"/>
            <jsp:useBean id="type" type="ru.javaops.webapp.model.SectionType"/>
            <c:set var="section" value="${sectionMap.value}"/>
            <jsp:useBean id="section" type="ru.javaops.webapp.model.AbstractSection"/>
    <h3>${type.title}</h3>
    <c:choose>
    <c:when test="${type=='PERSONAL' || type=='OBJECTIVE'}">
        <%=((TextSection) section).getText()%>
    </c:when>
    <c:when test="${type=='QUALIFICATIONS' || type=='ACHIEVEMENT'}">
        <ul>
            <c:forEach var="list" items="<%=((ListSection) section).getList()%>">
                <li>${list}</li>
            </c:forEach>
        </ul>
    </c:when>
    <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
    <c:forEach var="exp" items="<%=((ExperienceSection) section).getExperienceList()%>">
    <c:choose>
        <c:when test="${empty exp.link.url}">
            <h4>${exp.link.title}</h4>
        </c:when>
        <c:otherwise>
            <h4><a href="${exp.link.url}">${exp.link.title}</a></h4>
        </c:otherwise>
    </c:choose>
    <c:forEach var="experience" items="${exp.experience}">
    <jsp:useBean id="experience" type="ru.javaops.webapp.model.ExperienceList.Experience"/>
            <b><u><%=experience.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM")) +
                    " - " + experience.getEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM"))%></u></b>
        <br>
            <%=experience.getDescription()%>
        <br>
        <br>
        </c:forEach>
        </c:forEach>
        </c:when>
        </c:choose>
        </c:forEach>
        <p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>