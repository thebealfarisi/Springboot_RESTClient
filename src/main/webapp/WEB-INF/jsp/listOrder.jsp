<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title></title>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.1/css/select2.min.css"
	rel="stylesheet" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.1/js/select2.js"></script>
</head>
<body>
	<div align="center">
		<h3>Ticket Order</h3>
		<table>
			<tr>
				<td>
					Customer
				</td>
				<td>
					<select id="customerId" name="customerId" required></select>
				</td>
				<td>
					Film
				</td>
				<td>
					<select id="ticketId" name="ticketId" required></select>
				</td>
			</tr>
			<tr>
				<td>
					Name
				</td>
				<td>
					<input class="form-control" id="name" readonly />
				</td>
				<td>
					Film Name
				</td>
				<td>
					<input class="form-control" id="film" readonly />
				</td>
			</tr>
			<tr>
				<td>
					Phone
				</td>
				<td>
					<input class="form-control" id="phone" readonly />
				</td>
				<td>
					Time
				</td>
				<td>
					<input class="form-control" id="time" readonly />
				</td>
			</tr>
			<tr>
				<td>
					Email
				</td>
				<td>
					<input class="form-control" id="email" readonly />
				</td>
				<td>
					Buy
				</td>
				<td>
					<input class="form-control" id="buy" required />
				</td>
			</tr>
		</table>
		<div class="col-lg-12">
			<input type="button" id="save" value="Save"> &nbsp; 
			<input type="reset">
		</div>
		<table>
			<tr>
				<td>Ticket No</td>
				<td>Customer</td>
				<td>Film</td>
				<td>Total Ticket</td>
			</tr>
			<c:forEach items="${orderList }" var="order" varStatus="status">
				<tr>
					<td>${order.ticketNo }</td>
					<td>${order.customerName }</td>
					<td>${order.filmName }</td>
					<td>${order.buy }</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>

<script>
	$(document).ready(function() {
       	$('#customerId').select2({
		  	ajax: {
		    	url			: "${pageContext.request.contextPath}/getCustomers",
		    	dataType	: 'json',
		    	type		: "POST",
		    	delay		: 250,
		    	timeout		: 10000,
	            contentType	: "application/json",
	            data		: function(params) {
	                return JSON.stringify({
	                	name: params.term,
	                });
	            },
	            processResults: function (data, params) {
	            	console.log(data)
				    return {
	            	    results: $.map(data, function(obj) {
	            	        return { id: obj.customerId, text: obj.name, email: obj.email, phone: obj.phone, name: obj.name };
	            		})
	            	}
			    }
		  	},
		  	width: '192px',
		    escapeMarkup: function (markup) { return markup; }, 
		    minimumInputLength: 1,
		    templateResult: formatRepo,
            templateSelection: formatRepoSelection,
            allowClear: true,
            placeholder: "Select a Customer"
		});
		
       	function formatRepo (repo) {
           	if (repo.loading) return "Searching...";

       	  	var markup = "<div class='clearfix'>" + repo.text + "</div>";
       	  	return markup;
       	}

       	function formatRepoSelection (repo) {
       		$('#name').val(repo.name);
       		$('#phone').val(repo.phone);
       		$('#email').val(repo.email);
           	return repo.text;
       	}
       	
       	$('#ticketId').select2({
		  	ajax: {
		    	url			: "${pageContext.request.contextPath}/getTickets",
		    	dataType	: 'json',
		    	type		: "POST",
		    	delay		: 250,
		    	timeout		: 10000,
	            contentType	: "application/json",
	            data		: function(params) {
	                return JSON.stringify({
	                	name: params.term,
	                });
	            },
	            processResults: function (data, params) {
	            	console.log(data)
				    return {
	            	    results: $.map(data, function(obj) {
	            	        return { id: obj.ticketId, text: obj.film, film: obj.film, time: obj.startTime + "-" + obj.finishTime };
	            		})
	            	}
			    }
		  	},
		  	width: '192px',
		    escapeMarkup: function (markup) { return markup; }, 
		    minimumInputLength: 1,
		    templateResult: formatRepo1,
            templateSelection: formatRepoSelection1,
            allowClear: true,
            placeholder: "Select a Customer"
		});
		
       	function formatRepo1 (repo) {
           	if (repo.loading) return "Searching...";

       	  	var markup = "<div class='clearfix'>" + repo.text + "</div>";
       	  	return markup;
       	}

       	function formatRepoSelection1 (repo) {
       		$('#time').val(repo.time);
       		$('#film').val(repo.film);
           	return repo.text;
       	}
       	
       	$('#save').click(function(){
       		var buy = $('#buy').val();
       		var ticket = $('#ticketId').val();
       		var customer = $('#customerId').val();
       		var data = {
       				"customerId": customer,
       				"ticketId": ticket,
       				"buy": buy
       		}
       		
			console.log(data)
			
  			$.ajax({
	            type		: "POST",
	            contentType	: "application/json; charset=utf-8",
	            url			: "${pageContext.request.contextPath}/postOrder",
	            data 		: JSON.stringify(data),
	            cache		: false,
	            timeout		: 600000,
	            success		: function (data) {
		            location.reload();
	            },
	            error: function (e) {
	            	//responseMessageError(e);
	            }
			});  
       		
       	});
       	
	});

</script>