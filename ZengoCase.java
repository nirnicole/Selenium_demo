package demo;

import java.util.ArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

/*
 * 
 * Automation home assigment.
 * note that you should install selenium (4) and import web drivers to this project folder to run it.
 * 
 * 
 */

public class ZengoCase {
	
	//declare a web driver
	WebDriver driver;
	
	// explicit tell the launcher your prefferd wec driver, note that in our package we included only chrome.
	public void launchBrowser(String driver_name, String folder_path) {
		
		String  browser_driver = "webdriver."+ driver_name+".driver";	// property_name
		String driver_path = folder_path+driver_name+"driver.exe";	//value
		
		System.setProperty(browser_driver, driver_path);
		this.driver = new ChromeDriver();
		this.driver.get("https://zengo.com");
	}

	/*
	 * Test Scenario:
		1. Launch the ZenGo website www.zengo.com
		2. Verify that the site is displayed successfully
		3. Get to the menu item “Features” and press “Buy”
		4. Verify that you were redirected to https://zengowallet.banxa.com/ 
		5. Verify that ZenGo logo is displayed successfully 
		6. Get back to Home page
		7. Close browser
	 * 
	 */
	public void testCase1() throws InterruptedException {
			
		int TIME_OUT = 1000;
		
		
		//(step 1 is implicid)
		//step 2: verify site display.
		System.out.print("ZenGo site verification:\t");
		System.out.println(pageCheck("ZenGo"));
		Thread.sleep(TIME_OUT);
		
		// step 3:	got to featurs->buy
		// flow:	
		//			1. if menu bar is expanded - hover over the features tab and click on buy.
		//			2. if not - click on the hamburger button (to display the menu) and than on the features and buy tabs.
		WebElement menu_bar = this.driver.findElement(By.id("menu-item-13191"));
		if(!menu_bar.isDisplayed()) {
			this.driver.findElement(By.xpath("//span[@class='hamburger-box']")).click();
			Thread.sleep(TIME_OUT);
			this.driver.findElement(By.xpath("//header/div[@id='main-navigation-wrapper']/nav[@id='nav']/ul[1]/li[1]/button[1]")).click();
			Thread.sleep(TIME_OUT);
			this.driver.findElement(By.xpath("//li[@id='menu-item-10474']")).click();
			Thread.sleep(TIME_OUT);
		} 
		else{		
			Actions builder = new Actions(this.driver);
			WebElement hoverElement = this.driver.findElement(By.id("menu-item-13191"));
			builder.moveToElement(hoverElement).perform();
			Thread.sleep(TIME_OUT);
			this.driver.findElement(By.xpath("//header/div[@id='main-navigation-wrapper']/nav[@id='nav']/ul[1]/li[1]/ul[1]/li[1]/a[1]")).click();
			Thread.sleep(TIME_OUT);			
		}

		// step 4: save current tabs in a list and switch to the new tab, than check if it is valid.
	    ArrayList<String> tabs = new ArrayList<String> (this.driver.getWindowHandles());
	    this.driver.switchTo().window(tabs.get(1));
		Thread.sleep(TIME_OUT);
		System.out.print("Redirection verification:\t");
		System.out.println(redirectionCheck("https://zengowallet.banxa.com/"));
		
		// step 5: time the logo check a bit longer so the page could load properly.
		Thread.sleep(TIME_OUT*5);
		System.out.print("Logo image verification:\t");
		System.out.println(logoCheck(this.driver.findElement(By.xpath("//body/div[@id='app-main']/div[1]/div[1]/div[1]/div[1]/img[2]"))));
		this.driver.close();	//the new tab, "buy" domain.
	    this.driver.switchTo().window(tabs.get(0));	//back to original tab.
		Thread.sleep(TIME_OUT);
		this.driver.findElement(By.xpath("//header/div[1]/p[1]/a[1]/img[1]")).click();
	
		//wait a bit so we can admire the landing page of ZenGo.
		Thread.sleep(TIME_OUT*10);
		
	}
	
	/*
	 * if a title has been found and it contains the company name its valid.
	 * note: open to add here some more tests later.
	 */
	public boolean pageCheck(String expected_title) {

		String title = this.driver.getTitle();		
		boolean loaded=true;
		
		if(!title.contains(expected_title)) {
			System.out.println("Page: " + title + "loaded unexpectedly.");
		    loaded = false;
		}
		
		return loaded;
	}
	
	/*
	 * if the destination url contains the current url (at least its domain, it may redirect again after opening to more specific branch), accept.
	 */
	public boolean redirectionCheck(String dest_url) {

		boolean loaded=false;
		String current_url = this.driver.getCurrentUrl();

		if(dest_url.contains(current_url))
			loaded=true;
		
		return loaded;
	}
	
	/*
	 * 
	 */
	public boolean logoCheck(WebElement image) {
		
		boolean loaded=false;
		Object result = ((JavascriptExecutor)this.driver).executeScript(
				   "return arguments[0].complete && "+
				   "typeof arguments[0].naturalWidth != \"undefined\" && "+
				   "arguments[0].naturalWidth > 0", image);

				    if (result instanceof Boolean) {
				      loaded = (Boolean) result;
				    }
	return loaded;
	}

	/*
	 * quit the tabs of this browser.
	 */
	public void closeBrowser() {
		
	    ArrayList<String> tabs = new ArrayList<String> (this.driver.getWindowHandles());
	    
	    for(String tab : tabs) {
		    this.driver.switchTo().window(tab);	//back to original tab.
			this.driver.close();	//the new tab, "buy" domain.	    	
	    }
	    
		this.driver.quit();
	}
}

