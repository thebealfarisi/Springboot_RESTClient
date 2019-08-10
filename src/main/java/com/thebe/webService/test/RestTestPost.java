package com.thebe.webService.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestTestPost {

	public static void main(String[] args) {
		try {
			URL url = new URL("http://localhost:8080/thebe/restUser");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			
			String input = "{\"firstName\": \"thebe\",\"lastName\": \"aink\",\"username\": \"thebe\",\"password\": \"thebe\",\"phoneNumber\": \"thebe\",\"address\": \"thebe\",\"description\": \"thebe\",\"deletedStatus\": \"0\",\"roleId\": \"1\"}";
			
			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();
			
			if (conn.getResponseCode() != 201) {
				throw new RuntimeException("Failed: HTTP error code: " + conn.getResponseCode());
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			String output;
			System.out.println("Output from Server \r\n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
			conn.disconnect();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
}
