import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class Main {
	static String baseUrl = "https://api.clever.com";
	public static void main(String[] args) {
		Gson gson = new Gson();
		String sectionPage = doGet("/v1.2/districts/4fd43cc56d11340000000005/sections");
		RootElement root = gson.fromJson(sectionPage, RootElement.class);
		Paging paging = gson.fromJson(root.getPaging(), Paging.class);
		int iteration = Integer.parseInt(paging.getTotal());
		int sectionCount = Integer.parseInt(paging.getCount());
		int stuCount = 0;
		for(int i=1;i<=iteration;i++) {
			TypeToken<ArrayList<DataUri>> tokenDataUri = new TypeToken<ArrayList<DataUri>>() {};
			ArrayList<DataUri> dataUri = gson.fromJson(root.getData(), tokenDataUri.getType());
			for(DataUri du : dataUri) {
				Data data = gson.fromJson(du.getData(), Data.class);
				stuCount += data.getStudents().length;
			}
			TypeToken<ArrayList<Link>> tokenLinks = new TypeToken<ArrayList<Link>>() {};
			ArrayList<Link> links = gson.fromJson(root.getLinks(), tokenLinks.getType());
			sectionPage = doGet(links.get(1).getUri());
			root = gson.fromJson(sectionPage, RootElement.class);
		}
		double avgStuPerSec = stuCount/sectionCount;
		System.out.println("Average students in section = "+ avgStuPerSec);
		
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
