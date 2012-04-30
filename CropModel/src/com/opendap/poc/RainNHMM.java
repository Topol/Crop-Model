package com.opendap.poc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import ucar.ma2.Array;
import ucar.ma2.ArrayDouble;
import ucar.ma2.Index;
import ucar.nc2.NCdump;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;
import ucar.nc2.dataset.NetcdfDataset;

public class RainNHMM  {
	
	public RainNHMM(){}
	public Vector WriteRain(String folderPath, String fname)
	{
		String filename = this.getClass().getResource("/Data/188/2012-03-21/"+ fname+".cdf").getPath();
		System.out.println("filename for NHMM:" + filename );
		Vector vec = new Vector();
		
		NetcdfFile ncfile = null;
		  	try {
		  		 ncfile = NetcdfDataset.openFile(filename, null);  
		  		
		  	} catch (Exception e) {e.printStackTrace();} 
			try{
				  String varName = "aprod"; 
				  Variable v = ncfile.findVariable(varName);
				  FileOutputStream fout = null;
				  //if (null == v) return;
				  try {
					  Array data = v.read();
					  String parent = new File(this.getClass().getResource("/boo.txt").getFile()).getParent();
					  File dir = new File(parent, folderPath);
					  File writeFile = new File(dir,"HMM_"+fname+".txt");
					  fout = new FileOutputStream(writeFile);
					  System.out.println("filenamesHMM: " + writeFile);
					  Writer output = new BufferedWriter(new FileWriter(writeFile));
					  int[] shape = data.getShape();
					  Index index = data.getIndex();
					  for (int i=0; i<shape[0]; i++) {
					    for (int j=0; j<shape[1]; j++) {
					      double dval = data.getDouble(index.set(i,j));
					      vec.add(dval);	
					      //System.out.println(dval);
					    }
					  }
					  for (int k = 0; k < vec.size(); k++){
							
					  		 	output.write(vec.get(k).toString());
					    		output.write("\n");
					    		output.flush();
							 } 		  
					  
					  } 
					  catch (Exception e) {
					   e.printStackTrace();
					  }
					}
				  catch (Exception e) {
					  e.printStackTrace();
				  }
				//doit(urlName);
			return vec;
	}
	
	public void WriteInputFiles(String folderPath, String fname)
	{
		      FileOutputStream fout = null;
		 try {
			  File inputFile = new File(this.getClass().getResource(fname).getFile()); 
			  BufferedReader input =  new BufferedReader(new FileReader(inputFile));
			  
			  String parent = new File(this.getClass().getResource("/boo.txt").getFile()).getParent();
			  File dir = new File(parent, folderPath);
			  File writeFile = new File(dir,fname);
			  fout = new FileOutputStream(writeFile);
			  Writer output = new BufferedWriter(new FileWriter(writeFile));
		      try {
		        String line = null; //not declared within while loop
		        System.out.println(fname);
		        while ((( line = input.readLine()) != null) ){
		        		output.write(line);
		        		output.write("\n");		        		
		        		output.flush();
		        }
		       
        		output.close();
		      }catch(Exception e){}
		 }catch(Exception e){}
		        					
	}
}





