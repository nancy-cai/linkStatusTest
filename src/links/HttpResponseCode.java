package links;

import com.jayway.restassured.response.Response;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static com.jayway.restassured.RestAssured.given;
import java.util.ArrayList;

public class HttpResponseCode {
	static HttpResponseCode htp;
    static WebDriver driver;
    static Response response;
    static String dbUrl;
    static String username;
    static String password;
    static String query;
    static Connection con;
    static Statement stmt;
    static String baseurl;
    static ResultSet rs;
    static String WestpacId;
    
    
    public static void main(String args[]) throws ClassNotFoundException, SQLException {
    	htp = new HttpResponseCode();
        htp.checkBrokenLinks();
    }

    
    
    public static ResultSet connectToDb() throws ClassNotFoundException, SQLException {
    	 dbUrl = "";
    	 username ="";
    	 password = "";

   	    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		con = DriverManager.getConnection(dbUrl, username, password);
		stmt = con.createStatement();	
		query = "select";
		rs= stmt.executeQuery(query);
		System.out.println(rs);
		return 	rs;
 		
    }
 
    public static void checkBrokenLinks() throws SQLException, ClassNotFoundException {
  
        htp = new HttpResponseCode();
    	htp.connectToDb();
    	
    	baseurl = "";
    	ArrayList<String> brokenLinks= new ArrayList<String>();
        while (rs.next()) {
			WestpacId = rs.getString(1);			
			String link = baseurl+ WestpacId;	
			response = given().get(link).then().extract().response();
			 
            if(200 != response.getStatusCode()) {
            	brokenLinks.add(WestpacId);
                System.out.println(link + " gave a response code of " + response.getStatusCode());
            }
 		}
        System.out.println(brokenLinks);
   
    }
  
}
//http://www.testingexcellence.com/how-to-get-response-status-code-with-selenium-webdriver/
