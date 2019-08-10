package com.thebe.webService.service;

public interface RestService {

	/**
	 * Rest GET method to get data based in Web Service URL
	 * @param uri Web Service URl
	 * @return Json Object of retrieved Data
	 * @throws Exception
	 */
	public String methodGET(String uri) throws Exception;
	
	/**
	 * Rest POST method to posy data based in Web Service URL
	 * @param uri Web Service URl
	 * @param jsonData Json Object to be sent
	 * @return Respond data of Json Object 
	 * @throws Exception
	 */
	public String methodPOST(String uri, String jsonData) throws Exception;
	
}
