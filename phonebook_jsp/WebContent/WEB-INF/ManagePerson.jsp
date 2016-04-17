<?xml version="1.0" encoding="UTF-8" ?>
<%@ page import="app.Person"%>
<%@ page import="app.Phone"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:h="http://java.sun.com/jsf/html">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Управление данными о человеке</title>
</head>
<body>

	<%
		HashMap<String, String> jsp_parameters = new HashMap<String, String>();
		Person person = new Person();
		String error_message = "";
		String inside_message = "";

		if (request.getAttribute("jsp_parameters") != null) {
			jsp_parameters = (HashMap<String, String>) request.getAttribute("jsp_parameters");
		}

		if (request.getAttribute("person") != null) {
			person = (Person) request.getAttribute("person");
		}
		error_message = (String) jsp_parameters.get("current_action_result_label");
		inside_message = (String) request.getSession().getAttribute("current_action_result_label");
		request.getSession().removeAttribute("current_action_result_label");
	%>

	<form action="<%=request.getContextPath()%>/" method="post">
		<input type="hidden" name="id" value="<%=person.getId()%>" />
		<table align="center" border="1" width="70%">
			<%
				if ((error_message != null) && (!error_message.equals(""))) {
			%>
			<tr>
				<td colspan="2" align="center"><span style="color: red"><%=error_message%></span></td>
			</tr>
			<%
				}
			%>

			<%
				if ((inside_message != null) && (!inside_message.equals(""))) {
			%>
			<tr>
				<td colspan="2" align="center"><span style="color: red"><%=inside_message%></span></td>
			</tr>
			<%
				}
			%>
			<tr>
				<td colspan="2" align="center">Информация о человеке</td>
			</tr>
			<tr>
				<td>Фамилия:</td>
				<td><input type="text" name="surname"
					value="<%=person.getSurname()%>" /></td>
			</tr>
			<tr>
				<td>Имя:</td>
				<td><input type="text" name="name"
					value="<%=person.getName()%>" /></td>
			</tr>
			<tr>
				<td>Отчество:</td>
				<td><input type="text" name="middlename"
					value="<%=person.getMiddlename()%>" /></td>
			</tr>
			<%
				if (jsp_parameters.get("current_action").equals("edit")) {
			%>
			<tr>
				<td>Телефоны:</td>
				<td>
					<table width="100%">
						<%
							for (Phone phone : person.getPhones().values()) {
						%>
						<tr>
							<td><%=phone.getNumber()%></td>
							<td><a
								href="<%=request.getContextPath()%>/?action=editPhone&id=<%=person.getId()%>&phoneId=<%=phone.getId()%>">Редактировать</a></td>
							<td><a
								href="<%=request.getContextPath()%>/?action=deletePhone&id=<%=person.getId()%>&phoneId=<%=phone.getId()%>">Удалить</a></td>
						</tr>

						<%
							}
						%>
					</table>
					<table width="100%">
						<td><a
							href="<%=request.getContextPath()%>/?action=addPhone&id=<%=person.getId()%>">Добавить</a></td>
					</table> <%
 	}
 %>
				</td>
			</tr>

			<tr>
				<th colspan="2">
					<table align="center">
						<tr>
							<td><input type="submit"
								name="<%=jsp_parameters.get("next_action")%>"
								value="<%=jsp_parameters.get("next_action_label")%>" /></td>
						</tr>
						<tr>
							<td><a href="/">Вернуться к списку</a></td>
						</tr>
					</table>
				</th>
			</tr>
		</table>
	</form>
</body>
</html>