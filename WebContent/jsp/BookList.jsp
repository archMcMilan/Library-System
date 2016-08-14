<?xml version="1.0" encoding="ISO-8859-1" ?>
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
		<title><fmt:message key="book_list_page" bundle="${bundle}" /></title>
	</head>
	<body>
		<table>
		<tr><th><fmt:message key="book_name" bundle="${bundle}" /></th>
			<th><fmt:message key="publication_year" bundle="${bundle}" /></th></tr>
			<c:forEach var="book" items="${list}">
			<tr><td>${book.name}</td><td>${book.publicationYear}</td></tr>
			</c:forEach>
		</table>
		<input type="button" onclick="history.back();" value="<fmt:message key="back" bundle="${bundle}" />"/>
	</body>
</html>