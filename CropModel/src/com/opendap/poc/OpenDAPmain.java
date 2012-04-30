/**
 * 
 */
package com.opendap.poc;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;

import dods.dap.DAS;
import dods.dap.DConnect;
import dods.dap.DDS;
import dods.dap.DDSException;
import dods.dap.DODSException;
import dods.dap.parser.ParseException;

/**
 * @author Amitesh_Kumar01
 * 
 */
public class OpenDAPmain {

	/**
	 * @param args
	 * @throws dods.dap.parser.ParseException
	 * @throws DODSException
	 * @throws DDSException
	 */
	private static void doit(String urlName) throws IOException, DDSException, ParseException, DODSException {
		String filename = "dods.txt";
		FileOutputStream fout = new FileOutputStream(filename,true);// Append
		System.out.println("filename: " + filename);

		System.out.println("DODSV read =" + urlName);

		DConnect dodsConnection = new DConnect(urlName, true);

		// get the DDS
		DDS dds = dodsConnection.getDDS();
		//Console
		dds.print(System.out);
		//File
		dds.print(fout);

		// get the DAS
		DAS das = dodsConnection.getDAS();
		//Console
		das.print(System.out);
		//File
		das.print(fout);
	}

	public static void main(String args[]) throws IOException, DDSException, ParseException, DODSException {
		Authenticator.setDefault(new MyAuthenticator());
		String urlName =
		 "http://iridl.ldeo.columbia.edu/expert/SOURCES/.IMD/.RF0p5/.gridded/.daily/.v1p0/.rf/dup/SOURCES/.Features/.Political/.World/.1st_Order/.the_geom/objectid/%281033%29VALUES/rasterize/0.5/maskle/dataflag/0/maskle/mul/dods";
		// String urlName =
		// "http://iridl.ldeo.columbia.edu/expert/SOURCES/.WCRP/.GCOS/.GPCC/.FDP/.version4/.2p5/.prcp/SOURCES/.Features/.Political/.World/.Countries/.the_geom/objectid/146/VALUE%5BX/Y%5Dweighted-average/T/%28Jan%201961%29%28Dec%202007%29RANGE/yearly-anomalies/T/12/boxAverage/T/12/STEP/dods";
		//String urlName = "http://iridl.ldeo.columbia.edu/SOURCES/.IMD/.RF0p5/.gridded/.daily/.v1p0/.rf/a:/:a:/SOURCES/.Features/.Political/.World/.1st_Order/.the_geom/objectid/%281033%29VALUES/:a/rasterize/0.5/maskle/dataflag/0/maskle/mul/T/%28Jul-Sep%29VALUES/dods";

		doit(urlName);
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
