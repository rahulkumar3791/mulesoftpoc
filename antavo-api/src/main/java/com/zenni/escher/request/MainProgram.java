package com.zenni.escher.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import com.zenni.escher.EscherException;
import com.zenni.escher.request.AntavoRequestHeaders;

public class MainProgram {
	
	static String baseURL = "https://api.st2.antavo.com";
	static String eventsPath =  "/events" ;
	
	static String customerPath = "/customers/test-321";
	public static void post() throws ClientProtocolException, EscherException, IOException {
		String url = baseURL + eventsPath;
		String json = "{\"customer\":\"myfirstlogic1\",\"action\":\"opt_in\",\"data\":{\"email\":\"myfirstlogic1@zenni.com\",\"first_name\":\"Rajaram1\",\"last_name\":\"Pasupathy2\"}}";
		Header[] request = AntavoRequestHeaders.post(url,json,"api.st2.antavo.com","application/json","application/json");
		/*HttpClient client = HttpClientBuilder.create().build();
		HttpResponse response = client.execute(request);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		System.out.println(fetchResponse(response));*/
	}
	
	public static void main(String[] args) throws ClientProtocolException, EscherException, IOException {
		//get();
		//post();
	}
	
	public static Header[] get() throws ClientProtocolException, EscherException, IOException {
		String url = baseURL + customerPath;
		Header[] header = AntavoRequestHeaders.get(url,"api.st2.antavo.com","application/json");
		return header;
	}
	
	 private static String fetchResponse(HttpResponse response) throws IOException {
	        try (BufferedReader rd = new BufferedReader(
	                new InputStreamReader(response.getEntity().getContent()))) {
	            return rd.lines().collect(Collectors.joining("\n"));
	        }
	    }

}
