/*
	customer.js
*/

// TRYING THIS NOW...


$(document).ready(function () {
	"use strict";
	
	var myURL = "http://localhost:8080/views/customer";
	console.log(myURL);
	$.get(myURL)
		.done(function(r) {
			console.log(r);
		})
		.fail(function(err) {
			console.log("Failed to query " + myURL);
		})
		.always(function() {
			myURL = null
		});

})
			
// OLD CODE

/*

$(document).ready(function () {
	"use strict";
	


//Process Customer Request
	
	$("#customerForm").on("submit", function() {
		var searchFirstName = $("#searchFirstName").val();
		var myURL = "http://localhost:3000/customer?searchFirstName=";
		myURL += searchFirstName;
		myURL += "&Submit=Submit";
	
		$.get(myURL)
		.done(function(r) {
//			console.log("from done:" + r);
			customerResult.text(r);
			var ob = JSON.parse(r);
			console.log("Object Parsed from JSON: " + ob.firstName);
//			displayResults(r.items);
		})
		.fail(function(err) {
			console.log("Failed to query the URL. Error:" + err);
		})
		.always(function() {
			myURL = null
			searchFirstName = null
		});
		return false;
	});
	
	
	function displayResults(results) {
		customerResult.empty();
		$.each(results, function(i, item) {
			var newResult = $(item);
			console.log("newResult:" + newResult);

			newResult.hover(function () {
				//make it darker
				$(this).css("background-color","lightgray");
			}, function () {
				//reverse
				$(this).css("background-color","transparent");
			})

			customerResult.append(newResult);
		});
	}
	
	
})

*/
