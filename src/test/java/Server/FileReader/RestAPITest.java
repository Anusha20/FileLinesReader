
package Server.FileReader;

import org.junit.Assert;

import junit.framework.TestCase;

/** testing basic rest output**/
public class RestAPITest extends TestCase{
	
	public void setup(){
		try {
			String[] args ={"InputFile"};
			AppMain.main(args);
		} catch (Exception e) {
			
		}
	}

	
	public void testmakePositiveRestCall(){
		int status =new RestClient().getLineNumber("2");
		Assert.assertEquals(202, status);
	}
	
	
	public void testInvalidRestCall(){
		int status =new RestClient().getLineNumber("1000001");
		Assert.assertEquals(413, status);
	}
	
	
	public void testNumerousCalls(){
		for(int i =1 ;i<10;i++){
			int status =new RestClient().getLineNumber(""+i);
			
		}
	}
	
}
