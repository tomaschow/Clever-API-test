import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {
	static String baseUrl = "https://api.clever.com/v1.2/";
	public static void main(String[] args) {
		String stuInput = doGet("districts/4fd43cc56d11340000000005/students");
		String stuCount = stuInput.substring(stuInput.indexOf("count"),stuInput.indexOf("},\"links\"")).substring(7);
		
		String sectionInput = doGet("districts/4fd43cc56d11340000000005/sections");
		String sectionCount = sectionInput.substring(sectionInput.indexOf("count"),sectionInput.indexOf("},\"links\"")).substring(7);
		
		double avgStudentNumberInSection = Double.parseDouble(stuCount)/Double.parseDouble(sectionCount);
		System.out.println("Students:"+stuCount+"  Sections:"+sectionCount+" Avg:"+avgStudentNumberInSection);
		
	}
	
	static String doGet(String query) {
		URL url;
		HttpURLConnection connection;
		BufferedReader reader;
		StringBuilder result = new StringBuilder();
		try {
			url = new URL(baseUrl + query);
			connection = (HttpURLConnection)url.openConnection();
			connection.setRequestProperty("Authorization", "Bearer DEMO_TOKEN");
			int responseCode = connection.getResponseCode();
			String line = "";
			if (responseCode >= HttpURLConnection.HTTP_OK) {
				InputStream inputStream = connection.getInputStream();
				String encoding = connection.getContentEncoding();
				encoding = (encoding == null ? "UTF-8" : encoding);

				reader = new BufferedReader(new InputStreamReader(inputStream));
				while ((line = reader.readLine()) != null) {
					result.append(line);
				}
				reader.close();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

}
