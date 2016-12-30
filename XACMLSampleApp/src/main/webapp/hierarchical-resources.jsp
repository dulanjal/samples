<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Hierarchical Resources</title>
</head>

<body bgcolor="#F0F0F0">
	<h1 align="center">Close Account</h1>
	<hr />
	<form name="loanform" method="post" action="controller">
		<br />
		<%
		    if (request.getAttribute("uc6-authorized") != null
		            && (Boolean) request.getAttribute("uc6-authorized")) {
		%>
		<table width="330px" align="center">
			<tr bgcolor="green">
				<td align="center"><label><font color="white"><b>Successfully
								Authorized</b></font></label></td>
			</tr>
		</table>
		<%
		    } else {
		%>
		<%
		    if (request.getAttribute("uc6-authorized") != null
		                && !(Boolean) request.getAttribute("uc6-authorized")) {
		%>
		<table width="330px" align="center">
			<tr bgcolor="red">
				<td align="center"><label><font color="white"><b>Authorization
								Failed!</b></font></label></td>
			</tr>
		</table>
		<br />
		<%
		    }
		%>
		<table width="330px" align="center">
			<tr bgcolor="#FF6633">
				<td valign="top"><label><font color="white"><b>Access
								User</b></font></label></td>
				<td valign="top"><input type="text" name="user" maxlength="15"
					size="15"></td>
			</tr>
		</table>
		<%
		    }
		%>

		<input type="hidden" name="uc" value="uc6" />
		<table width="450px" align="center">
			<tr>
				<td style="text-align: center"><input type="submit"
					value="Submit"></td>
			</tr>
		</table>
	</form>
	<br />
	<table width="450px" align="center">
		<tr>
			<td style="text-align: center"><a
				href="hierarchical-resources.jsp">Try Again</a></td>
		</tr>
		<tr>
			<td style="text-align: center"><a href="index.jsp">Home</a></td>
		</tr>
	</table>
</body>
</html>
