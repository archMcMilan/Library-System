<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<c:if test="${lang eq null}">
		<c:set var="lang" value="${'en_US'}"/>
	</c:if>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<fmt:setLocale value="${lang}" />
		<fmt:setBundle basename="properties/message" var="bundle" />
		<title><fmt:message key="start_page" bundle="${bundle}" /></title>
	</head>
	<body>
		<form method="post" action="./Controller?command=LOCALIZATION">
			<select name="locale">
				<option selected value="ENG">ENG</option>
				<option value="UKR">UKR</option>
			</select>
			<input type="submit" value="Choose language/Оберіть мову"/>
		</form>
		<br/>
		<br/>
		<form method="post" action ="./Controller?command=AUTHENTICATION" >
			<fmt:message key="login" bundle="${bundle}" />
			<input type="text" name="login" />
			<br/>
			<fmt:message key="password" bundle="${bundle}" />
			<input type="password" name="password" />
			<input type="submit" value="<fmt:message key="button" bundle="${bundle}" />"/>
			
			<br/>
			<br/>
			<c:if test="${errorLogin eq true}">
				<fmt:message key="message_login_fail" bundle="${bundle}"/><br/>
			</c:if>
			
		</form>
		<p><a href="./Controller?command=AUTHENTICATION"><fmt:message key="guest_enter" bundle="${bundle}" /></a></p>
	</body>
</html>