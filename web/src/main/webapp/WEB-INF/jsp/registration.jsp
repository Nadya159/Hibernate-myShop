<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/registration" method="post">
    <label for="name">Name:
        <input type="text" name="name" id="name">
    </label>
    <br/><br/>
    <label for="name">Birthday:
        <input type="date" name="birthday" id="birthday">
    </label>
    <br/><br/>
    <label for="email">Phone:
        <input type="text" name="phone" id="phone">
    </label>
    <br/><br/>
    <label for="email">Email:
        <input type="text" name="email" id="email">
    </label>
    <br/><br/>
    <label for="pwd">Password:
        <input type="password" name="pwd" id="pwd">
    </label>
    <br/><br/>
    <label for="role">Role:</label><br>
    <select name="role" id="role">
        <c:forEach var="role" items="${requestScope.roles}">
            <option label="${role}">${role}</option>
            <br>
        </c:forEach>
    </select>
    <br/><br/>
    <label for="gender">Gender:</label><br>
    <select name="gender" id="gender">
        <c:forEach var="gender" items="${requestScope.genders}">
            <option label="${gender}">${gender}</option>
            <br>
        </c:forEach>
    </select>
    <br/><br/>
    <input type="submit" value="Send">
</form>
<c:if test="${not empty requestScope.errors}">
    <div style="color: red">
        <c:forEach var="error" items="${requestScope.errors}">
            <span>${error.message}</span>
        </c:forEach>
    </div>
</c:if>
</body>
</html>
