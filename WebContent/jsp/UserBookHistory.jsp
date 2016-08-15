<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="mytagDate" prefix="ctd" %>
	<c:if test="${lang eq null}">
		<c:set var="lang" value="${'en_US'}"/>
	</c:if>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<fmt:setLocale value="${lang}" />
		<fmt:setBundle basename="properties/message" var="bundle" />
		<title><fmt:message key="history_page" bundle="${bundle}" /></title>
	</head>
	<body>
		<div>
			<table>
				<tr>
					<th><fmt:message key="book_name" bundle="${bundle}" /></th>
					<th><fmt:message key="taking_date" bundle="${bundle}" /></th>
					<th><fmt:message key="bring_back_date" bundle="${bundle}" /></th>
					<th><fmt:message key="actual_return" bundle="${bundle}" /></th>
				</tr>
				<c:forEach items="${readerMap}" var="entry">
						<tr>
							<td>${entry.value.name}</td>
							<td><ctd:CustomTagDate localization="${lang}" date="${entry.key.dateTaking }" /></td>
							<td><ctd:CustomTagDate localization="${lang}" date="${entry.key.dateBringBack }" /></td>
							<td>
								<c:if test="${entry.key.dateFactBringBack eq null}">
									<form method="post" action ="./Controller?command=BRING_BACK">
										<input type="hidden" name="copy_id" value="${entry.key.id}" />
										<input type="submit" value="<fmt:message key="bring_back" bundle="${bundle}" />"/> 
									</form>
								</c:if>
								<c:if test="${entry.key.dateFactBringBack ne null}">
									<ctd:CustomTagDate localization="${lang}" date="${entry.key.dateFactBringBack }" />
								</c:if>					
							</td> 
						</tr>
					</c:forEach>
				<c:forEach var="books" items="${readerList}">
					<c:out value="${books}"/><br/>
				</c:forEach>
			</table>
			<fmt:message key="date_format" bundle="${bundle}" /><br/><br/>
			<button onclick="location.href='./Controller?command=GO_BACK_USER'">
					<fmt:message key="back" bundle="${bundle}" />
			</button>
		</div>
	</body>
</html>