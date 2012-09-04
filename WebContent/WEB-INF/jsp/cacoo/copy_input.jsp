<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/myapp.css">
		<title>Copy diagram - Cacoo API Sample</title>
	</head>
	<body>
		<div>
			<h1>Copy diagram</h1>
			<div>
				<form action="copyInput" method="POST">
					<input type="hidden" name="diagramId" value="<%=request.getAttribute("diagramId")%>">
					<table>
						<tr>
							<td>Title</td><td><input type="text" name="title" value="diagram title"></td>
						</tr>
						<tr>
							<td>Description</td><td><input type="text" name="description"></td>
						</tr>
					</table>
					<input type="submit" value="SUBMIT">
				</form>
			</div>			
			<div>
				<a href="<%=request.getContextPath()%>/cacoo/">&lt;&lt; Back to Diagrams.</a>
			</div>
		</div>
	</body>
</html>