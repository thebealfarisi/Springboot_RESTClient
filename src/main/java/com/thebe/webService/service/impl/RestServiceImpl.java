package com.thebe.webService.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thebe.webService.service.RestService;

@Service("restService")
public class RestServiceImpl implements RestService {

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	public String methodGET(String uri) throws Exception {
		// TODO Auto-generated method stub
		String result = "";
		try {
			URL url = new URL(uri);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			result = br.readLine();
			
			conn.disconnect();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	public String methodPOST(String uri, String jsonData) throws Exception {
		// TODO Auto-generated method stub
		String result = "";
		
		try {
			URL url = new URL(uri);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			
			OutputStream os = conn.getOutputStream();
			os.write(jsonData.getBytes());
			os.flush();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			result = br.readLine();
			
			conn.disconnect();
 			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return result;
	}

}
