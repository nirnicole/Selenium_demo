package demo;

public class Tester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ZengoCase obj = new ZengoCase();

		try {
			obj.launchBrowser("chrome", "C:\\Users\\nir\\Documents\\eclipse workspace\\AA-Selenium\\Drivers\\");
			obj.testCase1();
			obj.closeBrowser();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
