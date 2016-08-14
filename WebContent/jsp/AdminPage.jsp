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
		<title><fmt:message key="admin_page" bundle="${bundle}" /></title>
	</head>
	<body>
		<div>
			<h2><ct:CustonTag user="${name }" localization="${lang}"/></h2>
			<c:if test="${opSuccess eq true}">
				<fmt:message key="successfully_operation" bundle="${bundle}"/><br/>
			</c:if>
			<p><a href="./Controller?command=LOGOUT">
				<fmt:message key="logout" bundle="${bundle}" /></a>
			</p>
		</div>
		<div>
			<form method="post" action ="./Controller?command=FIND_OVERDUE_COPIES" >
			<fmt:message key="show_overdue_books" bundle="${bundle}" />
				<input type="submit" value="<fmt:message key="find" bundle="${bundle}" />"/> 
			</form>
			<table>
				<c:if test="${mapOverdue.size() gt 0}">
					<tr>
						<th><fmt:message key="reader_name" bundle="${bundle}" /></th>
						<th><fmt:message key="book_name" bundle="${bundle}" /></th>
						<th><fmt:message key="taking_date" bundle="${bundle}" /></th>
						<th><fmt:message key="bring_back_date" bundle="${bundle}" /></th>
						<th><fmt:message key="reader_phone" bundle="${bundle}" /></th>
					</tr>
				</c:if>
				
				<c:forEach items="${mapOverdue}" var="entry">
					<tr>
						<td>${entry.value.name}</td>
						<td>${entry.key.book.name}</td>
						<td>${entry.key.dateTaking}</td>
						<td>${entry.key.dateBringBack}</td>
						<td>${entry.value.phone}</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div>
			<form method="post" action ="./Controller?command=SHOW_ORDER_LIST" >
				<fmt:message key="show_ordered_books" bundle="${bundle}" />
				<input type="submit" value="<fmt:message key="find" bundle="${bundle}" />"/>
			</form>
			<table>
				<c:if test="${mapOrder.size() gt 0}">
					<tr>
						<th><fmt:message key="book_name" bundle="${bundle}" /> </th>
						<th><fmt:message key="author" bundle="${bundle}" /></th>
						<th><fmt:message key="publication_year" bundle="${bundle}" /></th>
						<th><fmt:message key="reader_name" bundle="${bundle}" /></th>
						<th><fmt:message key="reader_phone" bundle="${bundle}" /></th>
					</tr>
				</c:if>
				<c:forEach items="${mapOrder}" var="entry">
						<c:forEach items="${entry.value}" var="entryBook">
							<tr>
								<td>${entryBook.key.name}</td>
								<td>
									<c:forEach var="author" items="${entryBook.key.authors}">
										${author.surname}
									</c:forEach>
								</td>
								<td>${entryBook.key.publicationYear}</td>
								<td>${entry.key.name}</td> 
								<td>${entry.key.phone}</td> 
								<td>
									<c:if test="${entryBook.value eq true}">
										<form method="post" action ="./Controller?command=GIVE_BOOK_IN_ARMS" >
											<input type="submit" value="<fmt:message key="give_it_in_arms" bundle="${bundle}" />"/>
											<input type="hidden" name="book_id" value="${entryBook.key.id}" />
											<input type="hidden" name="reader_id" value="${entry.key.id}" />
										</form>  
										<form method="post" action ="./Controller?command=GIVE_BOOK_IN_HALL" >
											<input type="submit" value="<fmt:message key="give_it_in_hall" bundle="${bundle}" />"/>
											<input type="hidden" name="book_id" value="${entryBook.key.id}" />
											<input type="hidden" name="reader_id" value="${entry.key.id}" />
										</form>  
									</c:if>
								</td>
							</tr>
						</c:forEach>
				</c:forEach>
			</table>
		</div>
	</body>
</html>
