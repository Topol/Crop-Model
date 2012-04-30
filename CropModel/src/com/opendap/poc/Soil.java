package com.opendap.poc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.StringTokenizer;
import java.util.Vector;
import java.io.*;

import ucar.ma2.Array;
import ucar.ma2.Index;
import ucar.nc2.NCdump;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;
import ucar.nc2.dataset.NetcdfDataset;

public class Soil {
	
	public Soil(){}
	
	public Vector NumberofSoils(String pathname)
	{
		//String filename = pathname +"/soil.cdf";  
		String aFile = this.getClass().getResource("/Data/187/2012-03-21/soil.cdf").getPath();
		//String filename = "/Data/187/2012-03-21/soil.cdf";
		 Vector uniqueVals = new Vector();
		 Vector soilVector = new Vector();
		  NetcdfFile ncfile = null;
		  	try {
			    ncfile = NetcdfDataset.openFile(aFile, null);  
		  	} catch (IOException ioe) {ioe.printStackTrace();} 
			try{
				  String varName = "aprod"; 
				  Variable v = ncfile.findVariable(varName);
				  //if (null == v) return 0;
				  try {
					  Array data = v.read();
					  Array data2D = data;
					  Vector vec = new Vector();
					  
					  int[] varShape = v.getShape();
					  int[] origin = new int[3];				  
					  int[] size = new int[] {1, varShape[1]};				  
					  for (int i = 0; i < varShape[0]; i++) {
					    origin[0] = i;
					    data2D = v.read(origin, size).reduce(0);
					    for (int j = 0; j < data2D.getSize(); j++ ){
					    Object y2k = data2D.getObject(j);
					    if (!y2k.toString().equals("NaN")){vec.add(y2k);}
					    }
					    for (int k = 0; k < vec.size(); k++){
				            if (!uniqueVals.contains(vec.get(k).toString()))
				                uniqueVals.add(vec.get(k).toString());				        
					    }			    
					    					    
					  } 
					    soilVector.add(uniqueVals.size());
					    for (int j = 0; j < uniqueVals.size(); j++){
				        soilVector.add(uniqueVals.get(j));    				        
					    }
					    
				  }catch(Exception e){}
			}catch(Exception e){}
			return soilVector;
					  
	}
	
	public String SoilData(String pathname,double lat, double lon)
	{
		String Soil = "";
		//Getting the Soil Information 	
		String filename = pathname +"/soil.cdf";  
		  NetcdfFile ncfile = null;
		  	try {
			    ncfile = NetcdfDataset.openFile(filename, null);  
			     } catch (IOException ioe) {} 
			try{
				  String varName = "aprod"; 
				  Variable v = ncfile.findVariable(varName);
				  
				  if (null == v) return "";
				  try {
					  Array data = v.read();
					  Array data2D = data;
					  Vector vec = new Vector();
					  Vector uniqueVals = new Vector();
					  int[] varShape = v.getShape();
					  int[] origin = new int[3];				  
					  int[] size = new int[] {1, varShape[1]};				  
					  for (int i = 0; i < varShape[0]; i++) {
					    origin[0] = i;
					    data2D = v.read(origin, size).reduce(0);
					    for (int j = 0; j < data2D.getSize(); j++ ){
					    Object y2k = data2D.getObject(j);
					    if (!y2k.toString().equals("NaN")){vec.add(y2k);}
					    }				    				    		        
					  }  
					  for (int k = 0; k < vec.size(); k++){
				            if (!uniqueVals.contains(vec.get(k).toString()))
				                uniqueVals.add(vec.get(k).toString());				        
					    }
					   // System.out.println(uniqueVals);//uniqueVals have the information of soil.
					 
		/*Converting the soil into crop model format */					    
		/* build connection to the database */
		try	{
			Connection conn = null;
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/IndiaAgriculture?" +"user=root&password=root_pwd");
		    ResultSet results;
			PreparedStatement sql;
			ReadWithScanner rds = new ReadWithScanner();
			for (int k = 0; k  < uniqueVals.size(); k++){
				//System.out.println(k);
				String sqlsmt = "select FAOSoilInfo.SandTop, FAOSoilInfo.SiltTop, FAOSoilInfo.ClayTop from FAOSoilInfo,FAOSoilMaps where FAOSoilInfo.SoilType = FAOSoilMaps.SoilType and FAOSoilMaps.id ="+ uniqueVals.get(k).toString();
				sql = conn.prepareStatement(sqlsmt);
				results = sql.executeQuery();
				while (results.next()){
				float sand = Float.parseFloat(results.getString(1));
				float silt = Float.parseFloat(results.getString(2));
				float clay = Float.parseFloat(results.getString(3));
				//System.out.println(uniqueVals.get(k));
						
				if (silt < 40 && clay > 40 && sand<45){
				//System.out.println("Clay");
				Soil = "Clay";}
				if (silt > 40 && silt < 60 && sand < 20 && clay > 40 && clay < 60){
				//System.out.println("Silty Clay");
				Soil = "SiltyClay";}
				if (clay > 35 && clay < 55 && sand > 45 && sand < 65 &&  silt < 20){
				//System.out.println("Sandy Clay");
				Soil = "SandyClay";}
				if (clay > 20 && clay <35 && sand >45 && silt > 20 && silt <28 ){
				//System.out.println("Sandy Clay Loam");
				Soil = "SandyClayLoam";}
				if (sand > 20 && sand < 45 && silt > 15 && silt < 53 && clay >28 && clay < 40){
				//System.out.println("Clay Loam");
				Soil = "ClayLoam";}
				if (clay >28 && clay <40 && silt >40 && silt <72 && sand < 20){
				//System.out.println("Silty Clay Loam");
				Soil = "SiltyClayLoam";}
				if (clay < 28 && silt > 50 && sand < 50){
				//System.out.println("Silt Loam");
				Soil = "SiltLoam";}
				if (silt > 80 && sand < 20 && clay < 12){
				//System.out.println("Silt");
				Soil = "Silt";}
				if ( silt > 28 && silt < 50 && sand > 22 && sand < 53 && clay > 7 && clay < 27){
				//System.out.println("Loam");
				Soil = "Loam";}
				if (clay < 20 && sand < 85 && sand > 43 && silt < 50){
				//System.out.println("Sandy Loam");
				Soil = "Sandy Loam";}
				if(clay <15 && sand > 70 && silt < 30){
				//System.out.println("Loamy");
				Soil = "Loamy";}
				if (clay < 10 && sand > 85 && silt < 15){
				//System.out.println("Loamy Sand");
				Soil = "LoamySand";}
				}
				//System.out.println(Soil);
				
				//if(!Soil.equals(""))
				//rds.WriteCropInput(Soil, pathname, lat, lon);
				
			}
			return(Soil);
		}
		catch (Exception e){
			System.out.println(e);
		}

					    
					     int[] shape = data.getShape();
						  Index index = data.getIndex();
						  //System.out.println(shape[0]);
						  //System.out.println(shape[1]);				 			  
						  //NCdump.printArray(data, varName, System.out, null);
					  } catch (IOException ioe) {
					   System.out.println(ioe);
					  }
					}
				  catch (Exception e) {
					  System.out.println(" Test" + e);
				  }
				//doit(urlName);
			return Soil;
	}
	
	public String GetSoilByID(String SoilID)
	{
		String Soil = "";
		File aFile = new File(this.getClass().getResource("/SoilType.tsv").getFile());
		try	{
			 BufferedReader input =  new BufferedReader(new FileReader(aFile));
			 String line = null; //not declared within while loop
			     while (( line = input.readLine()) != null){
			    	
			          if(line.contains (SoilID.substring(0, 3)))
			          {
			        	    StringTokenizer st = new StringTokenizer(line,"\t");
			        	    st.nextToken();
			        	    float sand = Float.parseFloat(st.nextElement().toString());
						    float silt = Float.parseFloat(st.nextElement().toString());
						    float clay = Float.parseFloat(st.nextElement().toString());
			        	    Vector vec = new Vector();			        	  						
			        	    //System.out.println(sand +"\t"+ silt + "\t"+ clay);
			        	    if (silt < 40 && clay > 40 && sand<45){
							//System.out.println("Clay");
							Soil = "Clay";}
							else if (silt > 40 && silt < 60 && sand < 20 && clay > 40 && clay < 60){
							//System.out.println("Silty Clay");
							Soil = "Silty Clay";}
							else if (clay > 35 && clay < 55 && sand > 45 && sand < 65 &&  silt < 20){
							//System.out.println("Sandy Clay");
							Soil = "Sandy Clay";}
							else if (clay > 20 && clay <35 && sand >45 && silt > 20 && silt <28 ){
							//System.out.println("Sandy Clay Loam");
							Soil = "Sandy Clay Loam";}
							else if (sand > 20 && sand < 45 && silt > 15 && silt < 53 && clay >28 && clay < 40){
							//System.out.println("Clay Loam");
							Soil = "Clay Loam";}
							else if (clay >28 && clay <40 && silt >40 && silt <72 && sand < 20){
							//System.out.println("Silty Clay Loam");
							Soil = "Silty Clay Loam";}
							else if (clay < 28 && silt > 50 && sand < 50){
							//System.out.println("Silt Loam");
							Soil = "Silt Loam";}
							else if (silt > 80 && sand < 20 && clay < 12){
							//System.out.println("Silt");
							Soil = "Silt";}
							else if ( silt > 28 && silt < 50 && sand > 22 && sand < 53 && clay > 7 && clay < 27){
							//System.out.println("Loam");
							Soil = "Loam";}
							else if (clay < 20 && sand < 85 && sand > 43 && silt < 50){
							//System.out.println("Sandy Loam");
							Soil = "Sandy Loam";}
							else if(clay <15 && sand > 70 && silt < 30){
							//System.out.println("Loamy");
							Soil = "Loamy";}
							else if (clay < 10 && sand > 85 && silt < 15){
							//System.out.println("Loamy Sand");
							Soil = "Loamy Sand";}
							else
							Soil = "Clay";
			        	    
			          }
			     }
			
		}
		catch (Exception e){
			System.out.println(e);
		}


			return Soil;
	}
			


}
