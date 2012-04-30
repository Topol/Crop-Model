package com.opendap.poc;

import java.io.*; 
import java.util.Properties;

public class SimulateCrops {
	private String folder = null;
	
	public SimulateCrops(){}
	
	public String CallCropModel(String filename,String pathname,String DistrictID, String Crop)
	{	String CropOutput = "0";
		
			try{ 
			Properties properties = new Properties();
			// Read the properties file
			File file = new File("Data/2011-07-21/188");
			//File file = new File("Data/"+pathname+"/"+DistrictID);
			//Process p1=Runtime.getRuntime().exec("./DSSATCSM40 D Mo-23.5-73.5-0-SandyClLoam.INP",null,file);		
			//Process p1=Runtime.getRuntime().exec("./DSSATCSM40 D Mo-23.5-73.5-0-SandyLoam.INP",null,file);		
			Process p1=Runtime.getRuntime().exec("./DSSATCSM40 D "+filename,null,file); 			
			p1.waitFor(); 
			BufferedReader reader=new BufferedReader(new InputStreamReader(p1.getInputStream())); 
			String line=reader.readLine(); 
			
			
			while(line!=null) { 
				//System.out.println(line);
				if(line.contains("YIELD"))
				{
					System.out.println(line);
					//System.out.println(line.substring(39,43));
					CropOutput = line.substring(39,43);
				}
				//else 
					//CropOutput = "0";
				//CropYeild.concat(line);
				//if(line.contains("Invalid format in file.")) return "Error";
				//if(line.contains("End-of-file encountered in input file.")) return "Error";				
				line=reader.readLine();	
				//System.out.println(CropYeild);
			}
			
		} 
		catch(IOException e1) {return null;}
		catch(InterruptedException e2) {System.out.println(e2);} 
		catch(Exception e) {System.out.println(e);}
		//System.out.println("Done"); 
		return CropOutput;
		
	}
	
}
