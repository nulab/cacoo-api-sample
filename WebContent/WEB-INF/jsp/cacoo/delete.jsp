<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/myapp.css">
		<title>Delete diagram - Cacoo API Sample</title>
	</head>
	<body>
		<div>
			<h1>Delete diagram</h1>
			
			<div>
				<%=request.getAttribute("message") %>
			</div>
			
			<div>
				<a href="<%=request.getContextPath()%>/cacoo/">&lt;&lt; Back to Diagrams.</a>
			</div>
		</div>
	</body>
</html>