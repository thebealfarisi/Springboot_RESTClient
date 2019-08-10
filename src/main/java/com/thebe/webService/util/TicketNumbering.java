package com.thebe.webService.util;

public class TicketNumbering {

	public static String generateTicketNumber(Integer orderId) {
		String result = "";
		
		if (orderId < 10 && orderId > 0) {
			result = "T00000" + orderId;
		} else if (orderId < 100 && orderId >= 10) {
			result = "T0000" + orderId;
		} else if (orderId < 1000 && orderId >= 100) {
			result = "T000" + orderId;
		} else if (orderId < 10000 && orderId >= 1000) {
			result = "T00" + orderId;
		} else if (orderId < 100000 && orderId >= 10000) {
			result = "T0" + orderId;
		} else {
			result = "T" + orderId;
		}
		
		return result;
	}
	
}
