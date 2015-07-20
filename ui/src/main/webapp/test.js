$(document).ready(function() {
	$("#submit").click(submit);
});

function submit() {
	callService('/personService/rest/update');
}

function callService(serviceUrl, username, password) {
	var person = {
		firstName : $("#firstName").val(),
		lastName : $("#lastName").val(),
		organization : $("#organization").val(),
		active :  $("#active").val()=="true"
	};

	person = JSON.stringify(person);
	
	console.log("DATA: " + person);
	$.ajax({
		url : serviceUrl,
		data : person,
		dataType : 'json',
		contentType : 'application/json',
		type : 'POST',
		async : true,
		success : function(data) {
			alert("Name Submitted Successfully, response: "
					+ JSON.stringify(data));
		},
		error : function(data) {
			console.log("Error: " + data);
			alert("Something went wrong, check the javascript console for details.");
		}
	});
}