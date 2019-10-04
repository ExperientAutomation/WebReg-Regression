package Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

	Properties pro;
	
	public String LoginCredentails(String key) {
		
		String path = "N:\\QA\\R&DQA\\Selenium\\GlobalCredentials\\LoginCredentials.properties";

		FileReader fio;
		Properties objRepoProp = null;
		try {
			fio = new FileReader(path);
			objRepoProp = new Properties();
			objRepoProp.load(fio);
		} catch (Exception e) {

			e.printStackTrace();
		}

		return objRepoProp.getProperty(key);

	}

	public ConfigReader() {

		try {
			File src = new File(System.getProperty("user.dir")+"//Configuration//Config.property");
			FileInputStream fis = new FileInputStream(src);
			pro = new Properties();
			pro.load(fis);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}
	public String getuserID() {

		return pro.getProperty("userID");
	}
	public String getbrowser() {

		return pro.getProperty("browser");
	} 
	public String getpassword() {

		return pro.getProperty("password");
	} 
	
	public String getChromeFilePath() {
		return pro.getProperty("ChromeFilePath");
	}

	public String getflowname() {
		return pro.getProperty("flowname");
	}
	
	public String getshowcode() {

		return pro.getProperty("showcode");
	} 

	public String getCCNumber() {
		return pro.getProperty("CCNumber");
	}

	public String getflowcode1() {
		return pro.getProperty("flowcode1");
	}

	public String getFlowpath() {
		return pro.getProperty("flowpath");
	}

	public String geturl() {

		return pro.getProperty("url");
	}

	public String getattflowcode() {

		return pro.getProperty("attflowcode");
	}

	public String getattflowname() {

		return pro.getProperty("attflowname");
	}

	public String getwebregexcelpath() {

		return pro.getProperty("webregexcelpath");
	}
	
	public String getsswexcelpath() {

		return pro.getProperty("sswexcelpath");
	}

	public String gethouflowcode() {

		return pro.getProperty("houflowcode");
	}

	public String gethouflowname() {

		return pro.getProperty("houflowname");
	}
	public String gethouarrivaldate() {

		return pro.getProperty("houarrivaldate");
	}
	public String getflowopendate() {

		return pro.getProperty("flowopendate");
	}
	public String getflowenddate() {

		return pro.getProperty("flowenddate");
	}
	public String gethoudepdate() {

		return pro.getProperty("houdepdate");
	}
	public String getuniflowname() {

		return pro.getProperty("uniflowname");
	}

	public String getuniflowcode() {

		return pro.getProperty("uniflowcode");
	}
	
	public String getexhiflowcode() {

		return pro.getProperty("exhiflowcode");
	}

	public String getexhiflowname() {

		return pro.getProperty("exhiflowname");
	}

	public String getexhiblkflowcode() {

		return pro.getProperty("exhiblkflowcode");
	}

	public String getexhiblkflowname() {

		return pro.getProperty("exhiblkflowname");
	}
	
	public String getcompanyName() {

		return pro.getProperty("companyName");
	}
	
	public String getcompanyPassword() {

		return pro.getProperty("companyPassword");
	}

}
