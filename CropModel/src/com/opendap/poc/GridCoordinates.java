package com.opendap.poc;

import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;

public class GridCoordinates {
	
	public GridCoordinates(){}
	
	public Vector Coordinates(String folderPath)
	{
		Vector vecData = new Vector();
		File gridFile = new File(this.getClass().getResource("/Data/188/2012-03-21/GridCoordinates.tsv").getFile());
		
		try {
		      BufferedReader input =  new BufferedReader(new FileReader(gridFile));
		      try {
		        String line = null; //not declared within while loop
		        String Lat = input.readLine();		        
		        StringTokenizer st = new StringTokenizer(Lat,"\t"); 
		        Vector vecLat = new Vector();
		        while(st.hasMoreElements() ){
					vecLat.add(st.nextElement().toString());
		        }
				while (( line = input.readLine()) != null){
				StringTokenizer stLon = new StringTokenizer(line.toString(),"\t"); 
				String Lon = stLon.nextToken().toString();
				int i = 0;
				while(stLon.hasMoreElements() ){
					i++;
					String sgmt = stLon.nextToken().toString();
					if(Double.parseDouble(sgmt) == 1.0000){	
						vecData.add(Double.parseDouble(vecLat.get(i).toString()));
						vecData.add(Double.parseDouble(Lon.toString()));
					}
					
				}
		        
				}
		  		}catch(Exception e){}
		}catch(Exception e){}
		return vecData;
 }
}




