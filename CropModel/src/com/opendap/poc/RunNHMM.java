package com.opendap.poc;

import java.io.*; 
import java.util.Properties;

public class RunNHMM  {

	public RunNHMM (){}
	
	public void RunNHMMModel (String pathname, String DistrictID)
	{
		try{ 
			System.out.println("Here");
			//Properties properties = new Properties();
			// Read the properties file
			//File file = new File("Data/2011-08-08/187");
			
			File aFile = new File( this.getClass().getResource("/Data/187/2012-03-21/").getPath());
			//File aFile = new File(this.getClass().getResource("/DistrictCoordinates.txt").getFile());
			//System.out.println("here");
			//File file = new File("/Users/Arindam/Desktop/HMMTool-2.1/c++");
			//File file = new File("Data/"+pathname+"/"+DistrictID);
			Process p=Runtime.getRuntime().exec("./mvnhmm  ./lrn_nhmm_ind_delexp_params.txt",null,aFile);
			//p.waitFor(); 
			BufferedReader reader=new BufferedReader(new InputStreamReader(p.getInputStream()));
			
			String line=reader.readLine(); 
			while(line!=null) { 
				System.out.println(line); 
				line=reader.readLine(); 
			}
			Process q=Runtime.getRuntime().exec("./mvnhmm ./sim_nhmm_ind_delexp_params.txt",null,aFile); 
			
			//Process q=Runtime.getRuntime().exec("./Data/2011-07-11/461/DSSATCSM40 D ./Data/2011-07-11/461/DSSAT40.INP"); 
			//Process q=Runtime.getRuntime().exec("./mvnhmm sim_nhmm_ind_delexp_params.txt"); 
			//q.waitFor();
			
			BufferedReader readerq=new BufferedReader(new InputStreamReader(q.getInputStream())); 		
			String lineq=readerq.readLine(); 
			while(lineq!=null) { 
				System.out.println(lineq); 
				lineq=readerq.readLine(); 
			}
		} 
		catch(IOException e1) {e1.printStackTrace();} 
		//catch(InterruptedException e2) {} 
		System.out.println("Done"); 
		//return filename;
		
	}
	
}

