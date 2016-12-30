<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Conflicting Entitlements</title>
</head>

<body bgcolor="#F0F0F0">
	<h1 align="center">Money Transfer</h1>
	<hr />
	<form name="loanform" method="post" action="controller">
		<br />
		<%
		    if (request.getAttribute("uc4-authorized") != null
		            && (Boolean) request.getAttribute("uc4-authorized")) {
		%>
		<table width="330px" align="center">
			<tr bgcolor="green">
				<td align="center"><label><font color="white"><b>Successfully
								Authorized</b></font></label></td>
			</tr>
		</table>
		<br /> <br />
		<table width="450px" align="center">
			<tr>
				<td valign="top"><label>From Account</label></td>
				<td valign="top"><input type="text" name="first_name"
					maxlength="50" size="30"></td>
			</tr>

			<tr>
				<td valign="top"><label>To Account</label></td>
				<td valign="top"><input type="text" name="last_name"
					maxlength="50" size="30"></td>
			</tr>
			<tr>
				<td valign="top"><label>Amount</label></td>
				<td valign="top"><input type="text" name="email" maxlength="80"
					size="30"></td>
			</tr>
			<tr>
				<td valign="top"><label>Comments</label></td>
				<td valign="top"><textarea name="comments" maxlength="1000"
						cols="30" rows="3"></textarea></td>
			</tr>
		</table>
		<%
		    } else {
		%>
		<%
		    if (request.getAttribute("uc4-authorized") != null
		                && !(Boolean) request.getAttribute("uc4-authorized")) {
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

		<input type="hidden" name="uc" value="uc4" />
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
			<td style="text-align: center"><a href="entitlement-overlap.jsp">Try
					Again</a></td>
		</tr>
		<tr>
			<td style="text-align: center"><a href="index.jsp">Home</a></td>
		</tr>
	</table>
</body>
</html>
