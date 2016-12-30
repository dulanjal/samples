<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>External Data</title>
</head>

<body bgcolor="#F0F0F0">
	<h1 align="center">Request Cheque Book</h1>
	<hr />
	<form name="loanform" method="post" action="controller">
		<br />
		<% if (request.getAttribute("uc7-authorized") != null && (Boolean)request.getAttribute("uc7-authorized")) { %>
		<table width="330px" align="center">
			<tr bgcolor="green">
				<td align="center"><label><font color="white"><b>Successfully
								Authorized</b></font></label></td>
			</tr>
		</table>
		<br /> <br />
		<table width="450px" align="center">
				<tr>
					<td valign="top"><label>Account Number *</label></td>
					<td valign="top"><input type="text" name="account_num"
						maxlength="50" size="30"></td>
				</tr>
				<tr>
					<td valign="top"><label>Number of Books *</label></td>
					<td valign="top"><input type="text" name="num_of_books"
						maxlength="50" size="30"></td>
				</tr>
				<tr>
					<td valign="top"><label>First Name *</label></td>
					<td valign="top"><input type="text" name="first_name"
						maxlength="50" size="30"></td>
				</tr>

				<tr>
					<td valign="top"><label>Last Name *</label></td>
					<td valign="top"><input type="text" name="last_name"
						maxlength="50" size="30"></td>
				</tr>
				<tr>
					<td valign="top"><label>Telephone Number</label></td>
					<td valign="top"><input type="text" name="telephone"
						maxlength="30" size="30"></td>
				</tr>
				<tr>
					<td />
					<td valign="top"><input type="checkbox" name="expressReceive"
						value="expressReceive"> Express Receive<br></td>
				</tr>
			</table>
		<% } else { %>
		<% if (request.getAttribute("uc7-authorized") != null && !(Boolean)request.getAttribute("uc7-authorized")) { %>
		<table width="330px" align="center">
			<tr bgcolor="red">
				<td align="center"><label><font color="white"><b>Authorization
								Failed!</b></font></label></td>
			</tr>
		</table>
		<br />
		<% } %>
		<table width="330px" align="center">
			<tr bgcolor="#FF6633">
				<td valign="top"><label><font color="white"><b>Access
								User</b></font></label></td>
				<td valign="top"><input type="text" name="user" maxlength="15"
					size="15"></td>
			</tr>
		</table>
		<% } %>

		<input type="hidden" name="uc" value="uc7" />
		<table width="450px" align="center">
			<tr>
				<td colspan="2" style="text-align: center"><input type="submit"
					value="Submit"></td>
			</tr>
		</table>
	</form>
	<br />
	<table width="450px" align="center">
		<tr>
			<td style="text-align: center"><a href="external-data.jsp">Try
					Again</a></td>
		</tr>
		<tr>
			<td style="text-align: center"><a href="index.jsp">Home</a></td>
		</tr>
	</table>
</body>
</html>
