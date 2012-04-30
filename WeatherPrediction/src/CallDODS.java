import com.opendap.poc.DODS;


public class CallDODS {

	public CallDODS(){}
	
	public void ReadData()
	{
		DODS alpha = new DODS();
	    try {
			alpha.BuildDODS();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
