package com.thebe.webService.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thebe.webService.model.Customer;
import com.thebe.webService.model.Order;
import com.thebe.webService.model.Param;
import com.thebe.webService.model.Ticket;
import com.thebe.webService.service.RestService;
import com.thebe.webService.util.TicketNumbering;

@Controller
public class MainController {
	
	@Autowired
	RestService restService;
	
	@RequestMapping(value = "/showOrders", method = {RequestMethod.GET, RequestMethod.POST})
	public String getAllOrder(ModelMap model, HttpServletRequest request) {
		String uri = "http://localhost:9090/orders";
		
		try {
			String resultData = restService.methodGET(uri);
			
			ObjectMapper mapper = new ObjectMapper();
			
			List<Order> ordersData = Arrays.asList(mapper.readValue(resultData, Order[].class));
			
			for (Order order : ordersData) {
				String uriCustomer = "http://localhost:9090/customerId_" +  order.getCustomerId();
				String uriTicket = "http://localhost:9090/ticketId_" +  order.getTicketId();
				
				String customerData = restService.methodGET(uriCustomer);
				String ticketData = restService.methodGET(uriTicket);
				
				Customer customer = new ObjectMapper().readValue(customerData, Customer.class);
				Ticket ticket = new ObjectMapper().readValue(ticketData, Ticket.class);
				
				order.setCustomerName(customer.getName());
				order.setFilmName(ticket.getFilm());
				
				order.setTicketNo(TicketNumbering.generateTicketNumber(order.getOrderId()));
			}
			
			model.addAttribute("order", new Order());
			model.addAttribute("orderList", ordersData);
			model.addAttribute("message", resultData);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return "listOrder";
		
	}
	
	@RequestMapping(value = "/getCustomers", method = {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> getCustomers(@RequestBody Param param) {
		List<Customer> result = new ArrayList<Customer>();
		
		try {
			if (StringUtils.isNotBlank(param.getName())) {
				String uri = "http://localhost:9090/customerName_" + param.getName();
				String resultData = restService.methodGET(uri);
				
				ObjectMapper mapper = new ObjectMapper();
				
				result = Arrays.asList(mapper.readValue(resultData, Customer[].class));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getTickets", method = {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> getTickets(@RequestBody Param param) {
		List<Ticket> result = new ArrayList<Ticket>();
		
		try {
			if (StringUtils.isNotBlank(param.getName())) {
				String uri = "http://localhost:9090/ticketName_" + param.getName();
				String resultData = restService.methodGET(uri);
				
				ObjectMapper mapper = new ObjectMapper();
				
				result = Arrays.asList(mapper.readValue(resultData, Ticket[].class));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/postOrder", method = {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> postOrder(@RequestBody Param param) {
		Order result = new Order();
		
		try {
			if (param.getTicketId() != null) {
				String uri = "http://localhost:9090/order_" + param.getBuy() + "&ticket_" + param.getTicketId() + "&customer_" + param.getCustomerId();
				String resultData = restService.methodPOST(uri, "");
				
				ObjectMapper mapper = new ObjectMapper();
				
				result = (mapper.readValue(resultData, Order.class));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
}
