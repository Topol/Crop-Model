<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Spring 3 MVC Series - Contact Manager</title>
	</head>
	<body>
		<form action="HelloServlet">
		<h2></h2>
    	<table>
			<tr>
				<td><form:label path="Country">Country</form:label></td>
					<td><SELECT name=CountryName> 
						<OPTION value="" selected>- Select -</OPTION> 
						<OPTION value=India>India</OPTION>
					</td>
			</tr>
			<tr>
				<td><form:label path="State">State</form:label></td>
					<td><SELECT name=StateName> 
						<OPTION value="" selected>- Select -</OPTION> 
						<OPTION value=Gujarat>Gujarat</OPTION> 
						</SELECT> 
				  </td>
			</tr>
			<tr>
				<td><form:label path="District">District</form:label></td>
					<td><SELECT name=DistrictName> 
						<OPTION value="" selected>- Select -</OPTION> 
						<OPTION value=Kheda>Kheda</OPTION> 
					</SELECT> 
				</td>
			</tr>
			<tr>
				<td><form:label path="Crop">Crop</form:label></td>
				<td><SELECT name=CropName> 
					<OPTION value="" selected>- Select -</OPTION> 
					<OPTION value=Sorghum>Sorghum</OPTION> 
					</SELECT> 
				</td>
			</tr>
			<tr>
				<td><form:label path="GCM">General Circulation Model</form:label></td>
				<td><SELECT name=GCMName> 
					<OPTION value="" selected>- Select -</OPTION> 
					<OPTION value=ECHAM4.5>ECHAM 4.5</OPTION> 
					</SELECT> 
				</td>
			</tr>
			<tr><td></td></tr>
			<tr>
				<td colspan="2">
					<input type="submit" value="Run Crop Model(Historical Yeild)"/>
				</td>
				<td colspan="2">
					<input type="submit" value="Run Simulations(NHMM Predictions)"/>
				</td>
			</tr>
		</table>	
		<br>
		<br>
		<br>

		<table>
			<tr>
				<td><form:label path="firstname"><b>Simulation Outputs</b></form:label></td>
			</tr>
		</table>
		<fieldset>
			<table>
				<tr>
					<td><a href="http://www.w3schools.com">Historical Crop Yield</a></td>
				</tr>
				<tr>
					<td><a href="http://www.w3schools.com">Rainfall NHMM Output</a></td>
				</tr>
				<tr>
					<td><a href="http://www.w3schools.com">Corellation with SSTs</a></td>
				</tr>
				<tr>
					<td><a href="http://www.w3schools.com">Corellation with Rainfall</a></td>
				</tr>
				<tr>
					<td><a href="http://www.w3schools.com">Corellation with u-wind</a></td>
				</tr>
				<tr>
					<td><a href="http://www.w3schools.com">Corellation with v-wind</a></td>
				</tr>
			</table>
		</fieldset>
			
		</form>
		</body>
		</html>
