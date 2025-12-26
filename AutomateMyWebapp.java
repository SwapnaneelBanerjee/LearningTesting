package testing.automation.practice;
import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.Select;

public class AutomateMyWebapp {

	static Alert al;
	static WebDriver driver;

	static public void setup() {
		driver = new EdgeDriver();
		driver.manage().window().maximize();

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(45));
	}

	static public void getApp() throws InterruptedException {
		driver.get("url"); // url of MyWEbapp

		al = driver.switchTo().alert();

		System.out.println(al.getText());

		Thread.sleep(500);

		al.dismiss();

	}

	static public void navigate() throws InterruptedException {

		String parentWin = driver.getWindowHandle();

		driver.findElement(By.partialLinkText("Go to Google")).click();
		Thread.sleep(1000);
		driver.findElement(By.partialLinkText("Go to Yahoo")).click();
		Thread.sleep(1000);
		driver.findElement(By.partialLinkText("Go to Rediff")).click();
		Thread.sleep(1000);

		Set<String> wins = driver.getWindowHandles();

		int i = 0;

		for(String win : wins) {
			i++;

			if(i == 2) {
				
				driver.switchTo().window(win);
				
				System.out.println(driver.getTitle());
				break;
			}
		}

		Thread.sleep(1000);

		driver.switchTo().window(parentWin);

		Thread.sleep(500);

		System.out.println("Back to parent win: " + driver.getTitle());
	}

	public static void tearDown() {
		//System.out.println("From----> tear down");
		driver.quit();
	}
	
	public static void selectFromDropdown() throws InterruptedException {
		Select car = new Select(driver.findElement(By.id("cars")));
		
		List<WebElement> cars = car.getOptions();
		
		System.out.println("The number of cars " + cars.size());
		System.out.println("The available cars: ");
		
		for(WebElement i:cars){
			System.out.println(i.getText());
		}
		
		car.selectByValue("mercedes");
		Thread.sleep(2500);
		
	}

	public static void main(String[] args) throws InterruptedException {
		setup();
		getApp();
		navigate();
		selectFromDropdown();
		tearDown();
	}

}

