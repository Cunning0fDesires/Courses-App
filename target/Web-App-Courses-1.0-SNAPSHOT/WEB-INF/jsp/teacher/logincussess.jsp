<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title>Teacher Journal</title>
</head>
<body>

<h1 align="center">Journal</h1>

<c:set var="logoutUrl" value="${pageContext.request.contextPath}/controller?command=logout"/>
<a href="${logoutUrl}"><button class="GFG" style="position: absolute; top:25px; right:20px; height:30px;width:70px">
    <fmt:message key="button.logout"/>
</button></a>

<div>
    <% request.getSession().getAttribute("languages"); %>
</div>

<form id="changeLanguage" action="controller" method="post">
    <input type="hidden" name="command" value="changeLanguage"/>
    <select name="lang">
        <c:forEach items="${sessionScope.languages}" var="language">
            <option value="${language.shortName}" ${lang == language.shortName ? 'selected' : ''}><c:out value="${language.fullName}"/></option>
        </c:forEach>
    </select>
    <input type="submit" value="Apply changes"/>
</form>

<form id="updateJournal" action="controller" method="post">
    <input type="submit" value="<fmt:message key="button.update.all"/>">

    <input type="hidden" name="command" value="updateJournal"/>
    <input type="hidden" name="tablesAmount" value="${fn:length(editableCoursesJournalRows)}"/>
    <br/>

    <c:forEach items="${editableCoursesJournalRows}" var="mapEntry" varStatus="mapEntryLoop">
        <%-- workaround for non submitted first hidden field --%>
        <div style="display: none;">
            <p><c:out value="${fn:length(mapEntry.value)}"/></p>
            <p><c:out value="${mapEntryLoop.index}"/></p>
        </div>
        <input type="hidden" name="tableRowsAmount_${mapEntryLoop.index}" value="${fn:length(mapEntry.value)}"/>
        <c:set var="course" value="${mapEntry.key}"/>
        <table border="1" style="margin-top:60px; margin-left:300px; width:900px;">
            <caption><c:out value="${course.name}. Duration ${course.startDate} - ${course.endDate}"/></caption>
            <tr>
                <th><fmt:message key="label.student.name"/></th>
                <th><fmt:message key="label.student.surname"/></th>
                <th><fmt:message key="label.grade"/></th>
            </tr>
            <c:forEach items="${mapEntry.value}" var="journal" varStatus="journalLoop">
                <tr>
                    <input type="hidden" name="id_record_${mapEntryLoop.index}_${journalLoop.index}"
                           value="${journal.id}"/>
                    <td><c:out value="${journal.studentName}"/></td>
                    <td><c:out value="${journal.studentSurname}"/></td>
                    <td><input type="number" name="grade_${mapEntryLoop.index}_${journalLoop.index}"
                               value="${journal.grade}"/></td>
                </tr>
            </c:forEach>
        </table>
        <br/>
    </c:forEach>
</form>

<h1 align="center">History</h1>
    <c:forEach items="${readonlyCoursesJournalRows}" var="mapEntry" varStatus="mapEntryLoop">
        <c:set var="course" value="${mapEntry.key}"/>
        <table border="1" style="margin-top:60px; margin-left:300px; width:900px;">
            <caption><c:out value="${course.name}. Duration ${course.startDate} - ${course.endDate}"/></caption>
            <tr>
                <th><fmt:message key="label.student.name"/></th>
                <th><fmt:message key="label.student.surname"/></th>
                <th><fmt:message key="label.grade"/></th>
            </tr>
            <c:forEach items="${mapEntry.value}" var="journal" varStatus="journalLoop">
                <tr>
                    <td><c:out value="${journal.studentName}"/></td>
                    <td><c:out value="${journal.studentSurname}"/></td>
                    <td><c:out value="${journal.grade}"/></td>
                </tr>
            </c:forEach>
        </table>
        <br/>
    </c:forEach>

</body>
</html>
