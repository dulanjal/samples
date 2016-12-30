<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Reverse Lookup</title>
</head>

<% 
ArrayList<String[]> allowedResources = null;
 Object resultObj = request.getAttribute("allowedResources");
 if (resultObj != null) {
     allowedResources = (ArrayList<String[]>)resultObj;
 }
%>

<body bgcolor="#F0F0F0">
	<h1 align="center">Reverse Lookup</h1>
	<hr />
	<form name="reverseLookUpForm" method="post" action="controller">
		<br />
		<table width="550px" align="center">
			<tr>
				<td><label>Tenant:</label></td>
				<td><input type="text" name="tenant"></td>
			</tr>
			<tr>
				<td><label>Subject Type</label></td>
				<td><select
					onchange="document.getElementById('subjectId').value = 
                    (this.selectedIndex === 0) ? 'urn:oasis:names:tc:xacml:1.0:subject:subject-id' : 'http://wso2.org/claims/role';">
						<option value="user">User</option>
						<option value="role">Role</option>
				</select></td>
			</tr>
			<tr>
				<td><label>User / Role Name*:</label></td>
				<td><input type="text" name="subject"></td>
			</tr>
			<tr>
				<td><label>Subject Id*:</label></td>
				<td><input type="text" id="subjectId" name="subjectId"
					value="urn:oasis:names:tc:xacml:1.0:subject:subject-id"></td>
			</tr>
			<tr>
				<td>Action:</td>
				<td><input type="text" name="entitlementAction"></td>
			</tr>
			<tr>
				<td>ResourceName:</td>
				<td><input type="text" name="resource"></td>
			</tr>
			<tr>
				<td />
				<td>
					<table>
						<tr>
							<td><input type="checkbox" name="recursive" value="false"></td>
							<td><label>Search through child resources</label></td>
						</tr>
					</table>
				<td />
			</tr>
			<tr>
				<td />
				<td><input type="submit" value="Submit"></td>
			</tr>
		</table>
		<input type="hidden" name="uc" value="uc8" />
	</form>
	<br />

	<% if (allowedResources != null && !allowedResources.isEmpty()) { %>
	<table border="0" bgcolor="#ff6600" align="center">
		<tr bgcolor="#ffffff">
			<th>Resource</th>
			<th>Allowed Actions</th>
		</tr>
		<%
	        for (String[] resourceEntry : allowedResources) {
	            String userName = resourceEntry[0];
	            String resources = resourceEntry[1];
	    	%>
		<tr bgcolor="#ffffff">
			<td><%=userName%> </a></td>
			<td><%=resources%></td>
		</tr>
		<%}%>
	</table>
	<%}%>
	<br />
	<table width="450px" align="center">
		<tr>
			<td style="text-align: center"><a href="index.jsp">Home</a></td>
		</tr>
	</table>
</body>
</html>
