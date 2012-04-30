package com.opendap.poc;
/**
 *
 */

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.DocumentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.ParserFactory;


import java.io.File;
import java.util.*;
import org.w3c.dom.*;
import java.io.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import javax.xml.*;

//import sun.jdbc.odbc.JdbcOdbcDriver;
import java.sql.*;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;
import java.lang.*;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.nio.channels.FileChannel;


/**
 * @author Arindam Bhattacharjee
 *
 */
public class ServerContentDownloader {


	private static String servletURLPattern_rainfall = null;

	private static String servletURLPattern_soil = null;

	private static String servletURLPattern_Tmax = null;

	private static String servletURLPattern_Tmin = null;

	private static String servletURLPattern_Coordinates = null;
	
	private static String servletURLPattern_GCM = null;
	
	private static String servletURLPattern_TmaxAvgNR = null;
	private static String servletURLPattern_TmaxAvgR = null;
	private static String servletURLPattern_TminAvgNR = null;
	private static String servletURLPattern_TminAvgR = null;
	private static String servletURLPattern_SRadNR = null;
	private static String servletURLPattern_SRadR = null;

	private boolean useProxy = false;

	private String defaultProxyHost = "10.66.184.116";

	private String defaultProxyPort = "80";

	private String proxyHost = null;

	private String proxyPort = null;

	private String folder = null;

	/**
	 * @throws IOException
	 *
	 */
	public ServerContentDownloader() throws IOException {
		Authenticator.setDefault(new MyAuthenticator());
		configure();
	}

	/**
	 * @param args
	 */
	/*public static void main(String[] args) throws IOException{
	   	Authenticator.setDefault(new MyAuthenticator());
		try {
			ServerContentDownloader serverContentDownloader =  new ServerContentDownloader();
			serverContentDownloader.createAndSaveUrl();
			DODS dds = new DODS();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 *
	 * @throws IOException
	 */
	private void configure()  throws IOException {
		//Load the properties file
		Properties properties = new Properties();
		// Read the properties file
		properties.load(this.getClass().getResourceAsStream("/com/opendap/poc/ServerContentDownloader.properties"));

		useProxy = Boolean.parseBoolean(properties.getProperty("UseProxy"));
		if(useProxy){
			// Read the Proxy Host
			proxyHost = properties.getProperty("ProxyHost");
			System.out.println("ProxyHost : " + proxyHost);
			if(null == proxyHost || proxyHost.trim().length() < 1){
				proxyHost = defaultProxyHost;
				System.out.println("Using DefaultProxyHost : " + proxyHost);
			}
			// Read the Proxy Port
			proxyPort = properties.getProperty("ProxyPort");
			System.out.println("\nProxyPort : " + proxyPort);
			if(null == proxyPort || proxyPort.trim().length() < 1){
				proxyPort = defaultProxyPort;
				System.out.println("Using DefaultProxyPort : " + proxyPort);
			}
		}
		// Read the HTTP URL
		servletURLPattern_rainfall = properties.getProperty("servletURLPattern_rainfall");
		System.out.println("servletURLPattern : " + servletURLPattern_rainfall);
		servletURLPattern_soil = properties.getProperty("servletURLPattern_soil");
		System.out.println("servletURLPattern : " + servletURLPattern_soil);
		servletURLPattern_Tmax = properties.getProperty("servletURLPattern_Tmax");
		System.out.println("servletURLPattern : " + servletURLPattern_Tmax);
		servletURLPattern_Coordinates = properties.getProperty("servletURLPattern_Coordinates");
		System.out.println("servletURLPattern : " + servletURLPattern_Coordinates);
		servletURLPattern_Tmin = properties.getProperty("servletURLPattern_Tmin");
		System.out.println("servletURLPattern : " + servletURLPattern_Tmin);
		servletURLPattern_GCM = properties.getProperty("servletURLPattern_GCM");
		System.out.println("servletURLPattern : " + servletURLPattern_GCM);
		servletURLPattern_TmaxAvgNR = properties.getProperty("servletURLPattern_TmaxAvgNR");
		System.out.println("servletURLPattern: " + servletURLPattern_TmaxAvgNR);
		servletURLPattern_TmaxAvgR = properties.getProperty("servletURLPattern_TmaxAvgR");
		System.out.println("servletURLPattern: " + servletURLPattern_TmaxAvgR);
		servletURLPattern_TminAvgNR = properties.getProperty("servletURLPattern_TminAvgNR");
		System.out.println("servletURLPattern: " + servletURLPattern_TminAvgNR);
		servletURLPattern_TminAvgR = properties.getProperty("servletURLPattern_TminAvgR");
		System.out.println("servletURLPattern: " + servletURLPattern_TminAvgR);
		servletURLPattern_SRadNR = properties.getProperty("servletURLPattern_SRadNR");
		System.out.println("servletURLPattern: " + servletURLPattern_SRadNR);
		servletURLPattern_SRadR = properties.getProperty("servletURLPattern_SRadR");
		System.out.println("servletURLPattern: " + servletURLPattern_SRadR);
		

		//Read folder name
		folder = properties.getProperty("folder");
		System.out.println("Folder : " + folder);
		System.out.println("\n************************************************\n");

	}


	/**
	 * @param DistrictID 
	 * @throws Exception 
	 *
	 *
	 */
	public void createAndSaveUrl(String Path, String DistrictID) throws Exception{

		Properties properties = new Properties();
		properties.load(this.getClass().getResourceAsStream("/com/opendap/poc/ServerContentDownloader.properties"));
		String parent = new File(this.getClass().getResource("/boo.txt").getFile()).getParent();
		String folderProperty = properties.getProperty("folder");
		
		
		//String folderPath = folderProperty +"/" + DistrictID + "/" + Path; 
		String folderPath = "Data/188/2012-03-21";
		
		/* build connection to the database */
			try{
				File dir = new File(parent, folderPath);
				dir.mkdirs();
				new File(dir, "newboo3.txt").createNewFile();
				//System.out.println(dir.getAbsolutePath());
			}catch(Exception e){
				e.printStackTrace();
			}
			try	{
			int id = 1;
			//String DistrictID ="188";
			/*Get the district coordinates */
			File aFile = new File(this.getClass().getResource("/DistrictCoordinates.txt").getFile());
			String LatLong = "";
			double maxLat = 0.0;
			double minLat = 0.0;
			double maxLon = 0.0;
			double minLon =0.0;
 			
 			Double GCMLat = 0.0;
 			Double GCMLon = 0.0;
 			double GCMLatMin = 0.0; 
 			double GCMLatMax = 0.0;
 			double GCMLonmin = 0.0;
 			double GCMLonMax = 0.0;
 			
 			Vector LatVec = new Vector();
			Vector LonVec = new Vector();
			try	{
				 BufferedReader input =  new BufferedReader(new FileReader(aFile));
				 String line = null; //not declared within while loop
				     while (( line = input.readLine()) != null){
				    	 StringTokenizer sta = new StringTokenizer(line.toString(),"@");
			    		 //sta.nextToken();
			    		 if(sta.nextToken().toString().equals(DistrictID)){  	
				    		    sta.nextToken();
				    		 	LatLong = sta.nextToken();
				    		 	LatLong = LatLong.replace("MULTIPOLYGON(((", "");
								LatLong = LatLong.replace(")))", "");
								LatLong = LatLong.replace("))", ",");
								LatLong = LatLong.replace("((", "");
								//defining the area of the resterization
								StringTokenizer st = new StringTokenizer(LatLong,",");
				            	StringTokenizer stRaster = new StringTokenizer(LatLong,",");
				            	while(stRaster.hasMoreElements() ){
									String next = stRaster.nextElement().toString();
									StringTokenizer st3 = new StringTokenizer(next);
									Double Lat = Double.parseDouble(st3.nextElement().toString());
									Double Lon = Double.parseDouble(st3.nextElement().toString());
									LatVec.add(Lat);
									LonVec.add(Lon);
				    			}
								maxLat = Double.parseDouble(Collections.max(LatVec).toString());
				    			minLat = Double.parseDouble(Collections.min(LatVec).toString());
				    			maxLon = Double.parseDouble(Collections.max(LonVec).toString());
				    			minLon = Double.parseDouble(Collections.min(LonVec).toString());
				    			
				    			GCMLat = (maxLat+minLat)/2;
				    			GCMLon = (maxLon + minLon)/2;
				    			GCMLatMin = GCMLat - 5; 
				    			GCMLatMax = GCMLat + 5;
				    			GCMLonmin = GCMLon - 5;
				    			GCMLonMax = GCMLon + 5;
				    			
				          }
				     }
			}catch(Exception e){}
				        			
    		int dist = Integer.parseInt(DistrictID);
			id = dist;
			//the id in database is 1 more than the normal id
			id++;
			String filename = "";
			String urlString = "";
			double Lon = 0.0;
			double Lat = 0.0;
			/*Get the grid co-ordinates of the region */
			filename =  "GridCoordinates.tsv";
			urlString = servletURLPattern_Coordinates.replace("{{minLat}}", String.valueOf(minLat))
						.replace("{{maxLat}}",String.valueOf(maxLat)).replace("{{minLon}}", String.valueOf(minLon)).
						replace("{{maxLon}}", String.valueOf(maxLon)).replace("{{id}}",String.valueOf(id));
		    System.out.println("urlString :" + urlString);
		    saveUrl(filename , urlString);
			
			/* Get the soil parameters from the Datalibrary */
			filename = "soil.cdf";
			urlString = servletURLPattern_soil.replace("{{minLat}}", String.valueOf(minLat))
						.replace("{{maxLat}}",String.valueOf(maxLat)).replace("{{minLon}}", String.valueOf(minLon)).
						replace("{{maxLon}}", String.valueOf(maxLon)).replace("{{id}}",String.valueOf(id));
		    System.out.println("urlString :" + urlString);
		    saveUrl(filename , urlString);
		    
		    /*Call the soil function to convert the soil into ID*/
		    //Soil sol = new Soil();			
			
			/*Get the rainfall co-ordinates for the dataset */
			GridCoordinates grid = new GridCoordinates();
			Vector vec = grid.Coordinates(folderPath);
			Vector rainParameters = new Vector();
			Vector NHMMrain = new Vector();
			RainNHMM rnhmm = new RainNHMM();
			System.out.println ("Vector Size =" + vec.size());
			for(int i = 0; i <vec.size();i++){
				System.out.println(i);
				String pathname = "";
				
				Lon = Double.parseDouble(vec.get(i).toString());
				Lat = Double.parseDouble(vec.get(i+1).toString());
				i++;
				
				/*this call saves the rainfall values*/
				pathname = "rainfall"+"-"+Lat+"-"+Lon;
				filename = pathname+".cdf";
			    urlString = servletURLPattern_rainfall.replace("{{Lat}}", String.valueOf(Lat))
							.replace("{{Lon}}",String.valueOf(Lon)).replace("{{id}}",String.valueOf(id));
			    System.out.println("urlString :" + urlString);
			    saveUrl(filename , urlString);
			    
			     /* this methods builds up the rainfall files for HMM use*/
				 pathname = "rainfall"+"-"+Lat+"-"+Lon;
				 rainParameters = rnhmm.WriteRain(folderPath,pathname);
				 NHMMrain.add(rainParameters);
				
				 /*this call saves the Tmax values when it rained*/
				 pathname = "TmaxR"+Lat+"-"+Lon;
				 filename = pathname+".tsv";
				 urlString = servletURLPattern_TmaxAvgR.replace("{{Lat}}", String.valueOf(Lat))
								.replace("{{Lon}}",String.valueOf(Lon));
				 System.out.println("urlString :" + urlString);
				 saveUrl(filename , urlString);
				    
			     /*this call saves the Tmax values when it did not rain*/
				   pathname = "TmaxNR"+Lat+"-"+Lon;
				   filename = pathname+".tsv";
				   urlString = servletURLPattern_TmaxAvgNR.replace("{{Lat}}", String.valueOf(Lat))
									.replace("{{Lon}}",String.valueOf(Lon));
				   System.out.println("urlString :" + urlString);
				   saveUrl(filename , urlString);
				  
			      /*this call saves the Tmin values when it rained*/
				   pathname = "TminR"+Lat+"-"+Lon;
				   filename = pathname+".tsv";
				   urlString = servletURLPattern_TminAvgR.replace("{{Lat}}", String.valueOf(Lat))
									.replace("{{Lon}}",String.valueOf(Lon));
				   System.out.println("urlString :" + urlString);
				   saveUrl(filename , urlString);
				   
			       /*this call saves the Tmin values when it not rained*/
				   pathname = "TminNR"+Lat+"-"+Lon;
				   filename = pathname+".tsv";
				   urlString = servletURLPattern_TminAvgNR.replace("{{Lat}}", String.valueOf(Lat))
									.replace("{{Lon}}",String.valueOf(Lon));
				   System.out.println("urlString :" + urlString);
				   saveUrl(filename , urlString);
				   
			       /*this call saves the SRad values when it not rained*/ 
				   pathname = "SRadNR"+Lat+"-"+Lon;
				   filename = pathname+".tsv";
				   urlString = servletURLPattern_SRadNR.replace("{{Lat}}", String.valueOf(Lat))
									.replace("{{Lon}}",String.valueOf(Lon));
				   System.out.println("urlString :" + urlString);
				   saveUrl(filename , urlString);
				   
				   /* this call saves the SRad values when it rained */
				   pathname = "SRadR"+Lat+"-"+Lon;
				   filename = pathname+".tsv";
				   urlString = servletURLPattern_SRadR.replace("{{Lat}}", String.valueOf(Lat))
									.replace("{{Lon}}",String.valueOf(Lon));
				   System.out.println("urlString :" + urlString);
				   saveUrl(filename , urlString);
				   
				   /**/
				  
		      }
			
			/* this call defines the input parameters to HMM */
			rnhmm.WriteInputFiles(folderPath, "sim_nhmm_ind_delexp_params.txt");
			rnhmm.WriteInputFiles(folderPath, "lrn_nhmm_ind_delexp_params.txt");

			/**************Builds up the rainfall file to be read by NHMM *************************/
			/*File dir = new File(parent, folderPath);
			File writeFile = new File(dir,"HMM_Input.txt");
			FileOutputStream fout = new FileOutputStream(writeFile);
			Writer output = new BufferedWriter(new FileWriter(writeFile));
			System.out.println(((Vector) NHMMrain.get(0)).size());
			for(int k = 0; k <((Vector) NHMMrain.get(0)).size();k++)
				{
					output.write(((Vector) NHMMrain.get(0)).get(k) + "\t" +((Vector) NHMMrain.get(1)).get(k) + "\t" + ((Vector) NHMMrain.get(2)).get(k));
					output.write("\n");
				}
			
			/******************************************************************/
			
			/* this call downloads the GCM data */
		    /*for(int iYear=1982;iYear<2010;iYear++)
		    {
			filename = "GCM_"+iYear+".txt";
			
			urlString = servletURLPattern_GCM.replace("{{year}}", String.valueOf(iYear)).replace("{{minLat}}", String.valueOf(GCMLatMin))
							.replace("{{maxLat}}",String.valueOf(GCMLatMax)).replace("{{minLon}}", String.valueOf(GCMLonmin)).
							replace("{{maxLon}}", String.valueOf(GCMLonMax));
			 System.out.println("urlString :" + urlString);
			 saveUrl(filename , urlString);
		    }
		    
		    /*this builds up a single input to GCM and keeps it in a single file*/
		 /*   Vector vGCM = new Vector();
		    for(int iYear=1982;iYear<2010;iYear++)
		    {
		    	System.out.println(folderPath+ "/GCM_"+iYear+".txt");
		    	File aGCM = new File(this.getClass().getResource("/" +folderPath+ "/GCM_"+iYear+".txt").getFile());
		    	BufferedReader input =  new BufferedReader(new FileReader(aGCM));
		    	String line = null; //not declared within while loop
		         while (( line = input.readLine()) != null){
		        	 System.out.println(line);
		        	 vGCM.add(line);
		         }
		    }
		      File dir = new File(parent, folderPath);
			  File writeFile = new File(dir,"GCM.txt");
			  FileOutputStream fout = new FileOutputStream(writeFile);
			  Writer outputGCM = new BufferedWriter(new FileWriter(writeFile));
		       for (int k = 0; k < vGCM.size(); k++){
		    	 outputGCM.write(vGCM.get(k).toString());
		    	 outputGCM.write("\n");
		    	 outputGCM.flush();
       			}
		       outputGCM.close();
		       
			 /*Call the HMM simulations at this point */
			 
			 /*Once the dataset for HMM is run...call the NHMM to run at this point*/
			 ReadNHMMOutput rd = new ReadNHMMOutput();
			 
			 for(int i = 0; i <vec.size();i++)
				{
				 Lon = Double.parseDouble(vec.get(i).toString());
				 Lat = Double.parseDouble(vec.get(i+1).toString());
				 i++;
				}
				 	
			 
			 /*Once the NHMM is ready call the ReadNHMMOutput to build the input rainfall file*/
			 
			 /* At this point Call the 
			/* Write the input files for the crop models */
			/*	rnhmm.WriteInputFiles(folderPath, "/DATA.CDE");
				
				rnhmm.WriteInputFiles(folderPath, "/DETAIL.CDE");
				
				rnhmm.WriteInputFiles(folderPath, "/DSMODEL.ERR");
				
				rnhmm.WriteInputFiles(folderPath, "/GCOEFF.CDE");
				
				rnhmm.WriteInputFiles(folderPath, "/GRSTAGE.CDE");
				
				rnhmm.WriteInputFiles(folderPath, "/JDATE.CDE");
				
				rnhmm.WriteInputFiles(folderPath, "/PEST.CDE");
				
				rnhmm.WriteInputFiles(folderPath, "/SGCER040.CUL");
				
				rnhmm.WriteInputFiles(folderPath, "/SGCER040.ECO");
				
				rnhmm.WriteInputFiles(folderPath, "/SGCER040.SPE");
				
				rnhmm.WriteInputFiles(folderPath, "/Simulation.cde");
				
				rnhmm.WriteInputFiles(folderPath, "/SOIL.CDE");
				
				rnhmm.WriteInputFiles(folderPath, "/WEATHER.CDE");
				
				rnhmm.WriteInputFiles(folderPath, "/soil.sol");
				
				rnhmm.WriteInputFiles(folderPath, "/GATI0301.WTH");
				
				//rnhmm.WriteInputFiles(folderPath2, "GCM.txt");
				
				//rnhmm.WriteInputFiles(folderPath2, "DSSATCSM40");
				
				rnhmm.WriteInputFiles(folderPath, "/1-21.WTH");*/
				
				/********Read the crop Model File into the Folder*********/
				/*FileChannel ic = new FileInputStream("DSSATCSM40").getChannel();
				System.out.println(folderPath);
				FileChannel oc = new FileOutputStream(folderPath+"/DSSATCSM40").getChannel();
				ic.transferTo(0,ic.size(),oc);
				ic.close();
				oc.close();*/
				
			//}
			//System.out.println(HMMInput);

			

		  /*  filename = folderPath+ "/Tmax.cdf";
			urlString = servletURLPattern_Tmax.replace("{{minLat}}", String.valueOf(minLat))
						.replace("{{maxLat}}",String.valueOf(maxLat)).replace("{{minLon}}", String.valueOf(minLon)).
						replace("{{maxLon}}", String.valueOf(maxLon)).replace("{{id}}",String.valueOf(id));
		    //System.out.println("urlString :" + urlString);
		    saveUrl(filename , urlString);

		    /*filename = folderPath2+ "/[X+Y]Tmin.tsv";
			urlString = servletURLPattern_Tmin.replace("{{minLat}}", String.valueOf(minLat))
						.replace("{{maxLat}}",String.valueOf(maxLat)).replace("{{minLon}}", String.valueOf(minLon)).
						replace("{{maxLon}}", String.valueOf(maxLon)).replace("{{id}}",String.valueOf(id));
		    //System.out.println("urlString :" + urlString);
		    saveUrl(filename , urlString);*/
		    
		    
		    

			//}//end of while
		//}//end of if


		}
		/*catch(ClassNotFoundException Exception)	{
			System.out.println("error occoured during loading the driver");
		}*/
		catch(Exception e){
		System.out.println("error occoured during connecting " + e);
		e.printStackTrace();
		}
	}

	/**
	 *
	 * @param filename
	 * @param urlString
	 */
	private void saveUrl(String filename, String urlString){
		BufferedInputStream in = null;
		FileOutputStream fout = null;
		try
		{
			try {
				HttpURLConnection conn = null;
				if(useProxy){
					System.getProperties().put("http.proxyHost", proxyHost);
					System.getProperties().put("http.proxyPort", proxyPort);
				}
				URL url = new URL(urlString);
				conn = (HttpURLConnection) url.openConnection();
				boolean isConnected = (conn.getContentLength() > 0);
				System.out.println("isConnected: " + isConnected);
				in = new BufferedInputStream(conn.getInputStream());
				String parent = new File(this.getClass().getResource("/boo.txt").getFile()).getParent();
				String folderPath = "Data/188/2012-03-21"; 
				File dir = new File(parent, folderPath);
				File newSm = new File(dir,filename);
				fout = new FileOutputStream(newSm);
				//System.out.println("filenamesdd: " + newSm);
				byte data[] = new byte[1024];
				int count;
				while ((count = in.read(data, 0, 1024)) != -1)
				{
					fout.write(data, 0, count);
				}
				fout.flush();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		finally
		{
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (fout != null)
				try {
					fout.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	   static final String kuser = "ab3466"; // your account name

	  static final String kpass = "fqqlwe"; // your password for the account

		static class MyAuthenticator extends Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
		System.err.println("Feeding username and password for " + getRequestingScheme());
		return (new PasswordAuthentication(kuser, kpass.toCharArray()));
	}
}

}
