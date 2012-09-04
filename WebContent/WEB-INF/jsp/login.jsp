<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/myapp.css">
		<title>LOGIN - Cacoo API Sample</title>
	</head>
	<body>
		<div>
			<form action="j_security_check" method="post">
				<table style="width:300px; margin:100px auto;">
					<tr>
						<td>ID:</td><td><input type="text" name="j_username"></td>
					</tr>
					<tr>
						<td>Password:</td><td><input type="password" name="j_password"></td>
					</tr>
					<tr>
						<td colspan="2" style="text-align:center;"><input type="submit" value="LOGIN"></td>
					</tr>
				</table>
			</form>
		</div>
	</body>
</html>
