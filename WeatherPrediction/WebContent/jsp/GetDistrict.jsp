<%@ page import="java.io.*" %>
<%
StringBuilder sb = new StringBuilder();
try{
	
	String country = request.getParameter("cntryNo");
	String state = request.getParameter("stateNo");
	
	//obtain a reference to ServletContext
	ServletContext context = pageContext.getServletContext();
	BufferedReader input = new BufferedReader(new FileReader(context.getRealPath( "data/C_" + country + "_S_" + state + "_District.xml")));
	
	String line = "";
	while ((line = input.readLine()) != null) {
		sb.append(line);
	}
	input.close();
}catch (Exception e){
	sb = new StringBuilder();
	sb.append("<Districts><District><id>0</id><name>Data Not Found</name></District></Districts>");
}
response.setContentType("text/xml");
response.setHeader("Cache-Control", "no-cache");
response.setHeader("pragma","no-cache");

response.getWriter().write(sb.toString());

%> 