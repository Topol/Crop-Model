import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opendap.poc.*;

import java.io.PrintWriter;
import java.net.URL;
import java.util.Properties;

public class HelloWorld extends HttpServlet { 
  protected void doGet(HttpServletRequest request, 
      HttpServletResponse response) throws ServletException, IOException 
  {
    // reading the user input
    String Country= request.getParameter("CountryName");
    String State= request.getParameter("StateName");
    String District= request.getParameter("DistrictName");
    String Crop= request.getParameter("CropName");

    //Properties properties = new Properties();
	// Read the properties file
	//properties.load(this.getClass().getResourceAsStream("/com/opendap/poc/ServerContentDownloader.properties"));
    CallDODS obj = new CallDODS();
    obj.ReadData();
    PrintWriter out = response.getWriter();
    out.println (
    	"<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"+\"http://www.w3.org/TR/html4/loose.dtd\">\n" +"<html> \n" +
        "<head> \n" +
          "<meta http-equiv=\"Content-Type\" content=\"text/html;charset=ISO-8859-1\"> \n" +
          "<title> My first jsp  </title> \n" +
        "</head> \n" +
        "<body> \n" +
          "<font size=\"12px\">" +
            "Hello World <p>" + Country + "</p><p>" + State + "</p><p>"+ District+ "</p><p>"+ Crop + "</p><p>"+ 
          "</font> \n" +
        "</body> \n" +
      "</html>" 
    );  
  }  
}