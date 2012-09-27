<%@page import="com.cacoo.apisample.cacoo.CacooUtils"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/myapp.css">
		<title>Diagrams - Cacoo API Sample</title>
	</head>
	<body>
		<div>
			<h1>Diagrams</h1>
			<div>
				<img src="<%=request.getAttribute("imageUrl") %>">
				<%=request.getAttribute("userName") %>
			</div>
			<div>
				<button onclick="location.href='create'"  style="font-size:150%;">Create Diagram</button>
			</div>
			<div>
				<%
				List<Map> diagrams = (List<Map>)request.getAttribute("diagrams");
				if(diagrams.isEmpty()){
				%>
					There is no diagrams.
				<% }else{ %>
					<table class="bordered">
						<tr>
							<th style="width:200px;">Title</th>
							<th>Action</th>
						</tr>
					<% for(Map d : diagrams){ %>
						<tr>
							<td><a href="detail?diagramId=<%=d.get("diagramId")%>"><%=d.get("title")%></a></td>
							<td>
								<span class="action"><a href="editor?diagramId=<%=d.get("diagramId")%>">EDIT</a></span>
								<span class="action"><a href="copyInit?diagramId=<%=d.get("diagramId")%>">COPY</a></span>
								<span class="action"><a href="delete?diagramId=<%=d.get("diagramId")%>">DELETE</a></span>
							</td>
						</tr>		
					<% } %>
				</table>
				<% } %>
			</div>
			<div>
				<a href="<%=request.getContextPath()%>">&lt;&lt; Back to Top.</a>
			</div>
		</div>
	</body>
</html>