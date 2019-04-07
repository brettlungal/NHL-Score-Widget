import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;


public class HTTPRequest {
	
	private int teamId;
	
	public HTTPRequest( int teamId ) {
		try {
			HTTPGetRequest(teamId);
		}catch ( Exception e ) {
			System.out.println("oops, there was an issue fetching the team data");
		}
		
	}

	public void HTTPGetRequest(int teamId) throws InterruptedException {
		//return an array of scores?
		try {

			String url = "https://statsapi.web.nhl.com/api/v1/schedule?teamId="+teamId; // testing with id 3, but jets is
																					// id 52 dont forget to change
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			int responceCode = con.getResponseCode();
			System.out.println("Sending GET request to URL: " + url);
			System.out.println("Responce code: " + responceCode);
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

			String inputLine;
			StringBuffer responce = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				System.out.println(inputLine);
				responce.append(inputLine);
			}
			in.close();
			
			
		} catch (Exception c) {
			System.out.println(c);
		}

	}

}
