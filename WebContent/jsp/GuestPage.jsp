<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="mytag" prefix="ct" %>
<html xmlns="http://www.w3.org/1999/xhtml">
	<c:if test="${lang eq null}">
		<c:set var="lang" value="${'en_US'}"/>
	</c:if>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<fmt:setLocale value="${lang}" />
		<fmt:setBundle basename="properties/message" var="bundle" />
		<title><fmt:message key="guest_page" bundle="${bundle}" /></title>
	</head>
	<body>
		<h2><ct:CustomTag user="${name }" localization="${lang}"/></h2>
		<p><a href="./Controller?command=LOGOUT">
				<fmt:message key="logout" bundle="${bundle}" /></a>
		</p>
	 	<p><a href="./Controller?command=FIND_ALL_BOOKS">
	 		<fmt:message key="show_all_books_in_library" bundle="${bundle}" /></a>
	 	</p>
	 	<form method="post" action ="./Controller?command=FIND_BOOK_BY_CATALOG" >
	 		<fmt:message key="find_book_by_catalog" bundle="${bundle}" />
				<select name="catalog">
			    	<option value="1"><fmt:message key="fantasy" bundle="${bundle}" /></option>
			    	<option value="2"><fmt:message key="novel" bundle="${bundle}" /></option>
			    	<option value="3"><fmt:message key="verse" bundle="${bundle}" /></option>
			    	<option value="4"><fmt:message key="prose" bundle="${bundle}" /></option>
		    	</select>
		    	<input type="submit" value="<fmt:message key="find" bundle="${bundle}" />"/> 
		</form>
	 	<form method="post" action ="./Controller?command=FIND_BOOK_BY_NAME" >
			<fmt:message key="find_book_by_name" bundle="${bundle}" />
			<input type="text" name="book_name"/>
		 	<input type="submit" value="<fmt:message key="find" bundle="${bundle}" />"/> 
		</form> 
		<form method="post" action ="./Controller?command=FIND_BOOK_BY_AUTHOR" >
			<fmt:message key="find_book_by_author" bundle="${bundle}" />
			<input type="text" name="author"/>
		 	<input type="submit" value="<fmt:message key="find" bundle="${bundle}" />"/>
	 		<fmt:message key="enter_surname" bundle="${bundle}" />
		</form> 
		<table>
			<c:if test="${map.size() gt 0}">
				<tr>
					<th><fmt:message key="book_name" bundle="${bundle}" /></th>
					<th><fmt:message key="publication_year" bundle="${bundle}" /></th>
					<th><fmt:message key="pages_amount" bundle="${bundle}" /></th>
					<th><fmt:message key="catalog" bundle="${bundle}" /></th>
					<th><fmt:message key="author" bundle="${bundle}" /></th>
				</tr>
			</c:if>
			<c:if test="${findAmount eq 0 }">
				<fmt:message key="find_nothing" bundle="${bundle}" />
			</c:if>
			<c:forEach items="${map}" var="entry">
				<tr>
					<td>${entry.key.name}</td>
					<td>${entry.key.publicationYear}</td>
					<td>${entry.key.pagesAmount}</td>
					<td>${entry.key.catalog.name}</td>
					<td>
						<c:forEach var="author" items="${entry.key.authors}">
							${author.surname}
						</c:forEach>
					</td>
				</tr>
			</c:forEach>
		</table>
	</body>
</html>