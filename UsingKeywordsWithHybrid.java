package testing.automation.webapp;

import java.awt.Desktop.Action;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UsingKeywordsWithHybrid {

	static WebDriver driver;
	static Alert a;
	static Actions acts;
	static Action act;
	static XSSFWorkbook wb;
	static XSSFSheet cmdSh;
	static XSSFSheet dataSh;
	static FileInputStream fin;
	static File fileDataSheet;


	public static void setup() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		acts = new Actions(driver);
		//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

	}

	public static void getApp() {
		driver.get("https://practicetestautomation.com/practice");

	}



	public static void tearDown() throws InterruptedException {

		Thread.sleep(1000);
		driver.quit();
	}
	
	public static void executeAction(String keyword) throws InterruptedException {
	    switch (keyword.toLowerCase()) {
	        case "gotohome":
	        	driver.findElement(By.id("menu-item-43")).click();
	        	System.out.println("GoToHome executed");
	            break;

	        case "gotopractice":
	            driver.findElement(By.id("menu-item-20")).click();
	            System.out.println("GoToPracticePage executed");
	            break;

	        case "gototestloginpage":
	            System.out.println("Navigating to Login Section...");
	            driver.findElement(By.partialLinkText("Login")).click();
	            System.out.println("GoToLoginPage executed");
	            break;
	            
	        case "performlogintest":
	        	
	        	
	        	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        	wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Test Login Page")));
	        	driver.findElement(By.partialLinkText("Login")).click();
	        	
	        	System.out.println("--- Starting Data-Driven Login Sequence ---");
	            for(int i = 1; i <= dataSh.getLastRowNum(); i++) {
	            	
	                // 1. Find Element Fields
	                WebElement userField = driver.findElement(By.name("username"));
	                WebElement passField = driver.findElement(By.name("password"));

	                // 2. Clear and Send Keys
	                userField.clear(); 
	                userField.sendKeys(dataSh.getRow(i).getCell(0).getStringCellValue());
	                passField.clear();
	                passField.sendKeys(dataSh.getRow(i).getCell(1).getStringCellValue());
	                
	                driver.findElement(By.id("submit")).click();
	                
	                // 3. Wait and Validate
	                Thread.sleep(1000); // just to show the execution no practical usage
	                
	                String expTitle = "Logged In Successfully | Practice Test Automation";
	                if(expTitle.equals(driver.getTitle())) {
	                    System.out.println("Row " + i + " Result: PASS");
	                    
	                    driver.navigate().back();
	                } else {
	                    System.out.println("Row " + i + " Result: FAIL");
	                    
	                }
	            }
	            break;
	        default:
	            System.out.println("Invalid Keyword: " + keyword);
	    }
	}
	
	public static void performCommands() throws IOException, InterruptedException {

		fileDataSheet = new File("FILE PATH HERE");  // add the accurate file path
		fin = new FileInputStream(fileDataSheet);
		wb = new XSSFWorkbook(fin);
		cmdSh = wb.getSheet("Commands"); // sheet having the commands (keywords)
		dataSh = wb.getSheet("Login"); // sheet having the data

	    System.out.println("--- Starting Keyword Execution ---");

	    for (int i = 1; i < cmdSh.getLastRowNum()+1; i++) {
	        
	        String command = getCellData(i, 0);
	        
	        System.out.println("Step " + i + ": Executing " + command);
	        
	        executeAction(command);
	    }
	 }
	public static String getCellData(int rowNum, int colNum) {
	    DataFormatter formatter = new DataFormatter();
	    if (cmdSh.getRow(rowNum) == null || cmdSh.getRow(rowNum).getCell(colNum) == null) {
	        return ""; 
	    }
	    return formatter.formatCellValue(cmdSh.getRow(rowNum).getCell(colNum));
	}

	public static void main(String[] args) throws InterruptedException, IOException {
		setup();
		getApp();
		performCommands();
		tearDown();
		
	}
}
