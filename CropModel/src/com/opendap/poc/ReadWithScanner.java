package com.opendap.poc;
import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;

/** 
 Read and write a file using an explicit encoding.
 Removing the encoding from this code will simply cause the 
 system's default encoding to be used instead.  
*/
public final class ReadWithScanner {

	/**
	  * Fetch the entire contents of a text file, and return it in a String.
	  * This style of implementation does not throw Exceptions to the caller.
	  *
	  * @param aFile is a file which already exists and can be read.
	  */
	  
	  public void getDataFile(File aFile,File soilFile,String SoilName,String pathname, double lat, double lon ,int i,String WeatherFile, String year, String century) throws FileNotFoundException, IOException{
		    //...checks on aFile are elided
		    //reads from DSSAT.INP
		    StringBuilder contents = new StringBuilder();
		    System.out.println(pathname);
		    String depth ="";
		    String SoilID ="";
		    try {
		      String SoilType = "";
		      aFile = new File(this.getClass().getResource("/Data/187/2012-03-21/"+aFile.toString()).getFile());
		      BufferedReader input =  new BufferedReader(new FileReader(aFile));
		      BufferedReader inputSoil =  new BufferedReader(new FileReader(soilFile));
		      
		      Vector vec = new Vector();
		      Vector vDepth = new Vector();
		      if (SoilName.equals("Silty Clay")) SoilType = "SiltyClay";
			  else if (SoilName.equals("Sandy Clay")) SoilType = "SandyClay";
			  else if (SoilName.equals("Sandy Clay Loam")) SoilType = "SandyClLm";	
			  else if (SoilName.equals("Clay Loam")) SoilType = "ClayLoam";
			  else if (SoilName.equals("Silty Clay Loam")) SoilType = "SiltyClLm";
			  else if (SoilName.equals("Silt Loam")) SoilType = "SiltLoam";
			  else if (SoilName.equals("Sandy Loam")) SoilType = "SandyLoam";
			  else if (SoilName.equals("Loamy Sand")) SoilType = "LoamySand";
		      else SoilType = SoilName;
		      
		      String parent = new File(this.getClass().getResource("/boo.txt").getFile()).getParent();
		      File dir = new File(parent, pathname);
		      File writeFile = new File(dir, "Mo"+"-"+lat+"-"+lon+"-"+i+"-"+ SoilType+".INP");
		      FileOutputStream fout = new FileOutputStream(writeFile);
		      System.out.println("Scanner:  " + pathname+"Mo"+"-"+lat+"-"+lon+"-"+i+"-"+ SoilType+".INP");
		      
		      Writer output = new BufferedWriter(new FileWriter(writeFile));		      		      
		      
		      //***************This part writes the 1st part of the file ****//
		      try {
		    	  
		    	  String vSoilName = SoilName;
			        String line = null; //not declared within while loop
			         
			        while (( line = inputSoil.readLine()) != null){
			        	System.out.println("Hello World");  	 
			          if(line.contains(vSoilName))
			          {	 
			        	  StringTokenizer st = new StringTokenizer(line," ");
			        	  SoilID = st.nextElement().toString();			        	  
			        	  st.nextElement();st.nextElement();
			        	  depth = st.nextElement().toString();			        	  
			        	  String a = " GAPN930001  GA_tifton  XXXXX  "+depth+".  " + vSoilName;                              
			        	  String b = "*CULTIVAR";           
			        	  String c = "IB0055 PIONEER 850      IB0001 460.0 12.50  90.0 600.0   5.0   6.0";
			        	  String d = "49.00";
			        	  vec.add(a);
		        		  do{
			        		  line = inputSoil.readLine();			        		  
			        		  if(!line.contains("@") ) {
			        		  vec.add(line);
			        		  vDepth.add(line);
			        	  }
			        	  }while(line.length() != 0);
		        		  vec.add(b);
		        		  vec.add(c);
		        		  vec.add(d);
		        		  vSoilName = "noSoil";
		     			 }
			             //System.out.println("Here");
		        		 //break;
			          }
			        
			      }catch(Exception e){}
		  //***************This part writes the 2st part of the file ****//  
		      try {
		        String line = null; //not declared within while loop
		        while ((( line = input.readLine()) != null) ){
		        	if(line.contains("WEATHER"))
		        	{
		        		output.write("WEATHER        "+WeatherFile);
		        		output.write("\n");
		        		output.write("OUTPUT        Output");
		        		output.write("\n");
		        		input.readLine();
		        	}
		        	
		        	else if(line.contains("*SIMULATION CONTROL"))
		        	{
		        		input.readLine();//jump 1 line
		        		output.write("*SIMULATION CONTROL");
		        		output.write("\n");
		        		output.write("                   1     1     S "+century+year+"152  2150 N X IRRIGATION, GA_TIFTON MZCER");
		        		output.write("\n");
		        		
		        	}
		        	else if(line.contains("*IRRIGATION"))
		        	{
		        		output.write("*IRRIGATION");
		        		output.write("\n");
		        		output.write(input.readLine());
		        		output.write("\n");
		        		output.write("   "+century+year+"063 IR001  13.0");
		        		output.write("\n");
		        		input.readLine();
		        	}
		        	else if(line.contains("!AUTOMATIC MANAGEM "))
		        	{
		        		output.write("!AUTOMATIC MANAGEM  ");
		        		output.write("\n");
		        		output.write("               "+century+year+"152 "+century+year+"212   40.  100.   30.   40.   10.");
		        		output.write("\n");
		        		input.readLine();
		        		output.write(input.readLine());
		        		output.write("\n");
		        		output.write(input.readLine());
		        		output.write("\n"); 
		        		output.write(input.readLine());
		        		output.write("\n");  
		        		output.write("                     0 "+century+year+"057  100.    0.");
		        		output.write("\n");
		        		input.readLine();
		        	}
		        	
		        	else if(line.contains("*FIELDS"))
		        	{
		        		output.write("*FIELDS"); 
		        		input.readLine();
		        		input.readLine();//jump reading two lines
		        		output.write("\n");
		        		output.write("   GATI0002 GATI"+year+"01   0.0    0. DR000    0.  100. 00000        "+depth+". " + SoilName);
		        		//output.write("   GATI0002 GATI0301   0.0    0. DR000    0.  100. 00000        "+depth+". " + SoilName);
		        		//output.write("   GATI0002 GATI0301   0.0    0. DR000    0.  100. 00000        180.  Millhopper Fine Sand");
		        		output.write("\n");
		        		output.write("          29.63000       -82.37000     40.00               1.0  100.   1.0   0.0");
		        		output.write("\n");
		        	}
		        	else if(line.contains("*INITIAL CONDITIONS"))
		        	{
		        		output.write("*INITIAL CONDITIONS"); 
		        		//input.readLine();
		        		input.readLine();//jump reading one line
		        		output.write("\n");
		        		output.write("   SG    "+century+year+"152  100.    0.  1.00  1.00   0.0  1000  0.80  0.00  100.   15.");
		        		output.write("\n");
		        		for (int k = 2; k < vDepth.size(); k++){
		    	    		 output.write(vDepth.get(k).toString());
		    	    		 //System.out.println(vec.get(k).toString());
		    	    		 output.write("\n");
		    	    		 output.flush();
			        		}
		        		input.readLine();
		        		input.readLine();
		        		input.readLine();
		        		input.readLine();
		        		input.readLine();
		        		input.readLine();
		        		input.readLine();
		        		input.readLine();
		        		input.readLine();//jump 9 lines
		        	}
		        	else if(line.contains("*PLANTING DETAILS"))
		        	{
		        		input.readLine();
		        		output.write("*PLANTING DETAILS");
		        		output.write("\n");
		        		output.write("   "+century+year+"152     -99   7.2   7.2     S     R   61.    0.   7.0  -99.  -99. -99.0 -99.0   0.0");
		        		output.write("\n");
		        		
		        	}
		        	else if(line.contains("*SOIL"))
		        	{
		        		output.write("*SOIL");
		        		output.write("\n");
		        	    break;
		        	}
		        	else
		        	{
		        		output.write(line.toString());		        
		        		output.write("\n");		                	  
		        	}		        	
		        		
		       }
		        for (int k = 0; k < vec.size(); k++){
    	    		 output.write(vec.get(k).toString());
    	    		 output.write("\n");
    	    		 output.flush();
	        		}
		        output.close();
		      }catch(Exception e){}
		      	      	  		      
		      finally {
		        input.close();
		        output.close();
		        }
		    }
		    catch (IOException ex){
		      ex.printStackTrace();
		    }
		    //return contents.toString();
		  }

	  public void WriteCropInput(String soil,String pathname, double lat, double lon, int i, String WeatherFile, String year, String century , String Crop) {
		    try
		    {
		    	FileOutputStream fout;
		    	pathname = "/Data/187/2012-03-21/";
		    	String parent = new File(this.getClass().getResource("/boo.txt").getFile()).getParent();
		    	File dir = new File(parent, pathname);
	        	File testFile = new File(dir, "soil.sol");
	        	File inputFile = new File(dir, "DSSAT40_"+Crop+".INP");
	        	fout = new FileOutputStream(testFile);
	        	fout = new FileOutputStream(inputFile);
	        	
	        
		    	getDataFile(inputFile,testFile,soil,pathname,lat,lon,i,WeatherFile,year,century); 
		    	//getContents(testFile, soil,pathname,lat,lon,i,WeatherFile);
		    	//WriteWTHFile wthfile = new WriteWTHFile();
		    	String fname = "";
		    	
		    }
		    catch(Exception e){}
		  }
}