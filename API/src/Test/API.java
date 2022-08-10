package Test;

import static com.jayway.restassured.RestAssured.given;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

import com.jayway.restassured.response.Response;

public class API {
	
	static String filePath = "C://Users/SwatiDeepu/workspace/API/src/Data/input.json";
	static String strURL = "https://6143a99bc5b553001717d06a.mockapi.io/testapi/v1//Users";
	
	public static void main(String[] args) throws IOException {
		post();
		get();
	}
	
	//Get method
	public static void get() throws IOException {
		
		Response res = given()
		.header("Content-type", "application/json")
        .when()
        .get(strURL)
        .then()
        .extract().response();
		
		if(res.getStatusCode()==200){
			
			if(res.jsonPath().getString("employee_firstname").equals(getProperty("employee_firstname")) &&
	        res.jsonPath().getString("employee_lastname").equals(getProperty("employee_lastname")) && 
	        res.jsonPath().getString("employee_phonenumbe").equals(getProperty("employee_phonenumbe")))
			System.out.println("Success");
			
			else{
				System.out.println("failed");
				System.exit(0);
			}
		}
		else{
			System.out.println("failed");
			System.exit(0);
		}
	}
	
	//Post method
	public static void post() {
		// TODO Auto-generated method stub
		
		String data = readData(filePath);
		
		//Response res =  given().contentType("application/json").post(strURL, data);
		
		Response res = given()
		.header("Content-type", "application/json")
        .and()
        .body(data)
        .when()
        .post(strURL)
        .then()
        .extract().response();
		
		if(res.getStatusCode()==200) System.out.println("Success");
		else{
			System.out.println("Failed");
			//System.exit(0);
		}
	}
	
	//read JSON file
	public static String readData(String fileName){
		try {
			return FileUtils.readFileToString(new File(fileName), StandardCharsets.UTF_8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	//
	public static String getProperty(String str) throws IOException{
		
		 	FileReader reader=new FileReader("db.properties");  
	      
		    Properties p=new Properties();  
		    p.load(reader);  
		      
		    return p.getProperty(str);  
	}
}
