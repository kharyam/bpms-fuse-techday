$(document).ready(function() {
	$("#terminate").hide();
	$("#terminateSync").hide();
	
	$("#setCredentials").click(enableButtons);
	$("#terminate").click(terminate);
	$("#terminateSync").click(terminateSync);
});

function enableButtons(){
	var username = $("#username").val();
	var password = $("#password").val();
	
	console.log("Clicked");
	
	if (!username || !password) {
		console.log("hiding");
		$("#terminate").hide();
		$("#terminateSync").hide();
	} else {
		console.log("showing");
		$("#terminate").show();
		$("#terminateSync").show();
	}
}

function terminate() {
	callService('/person/terminate');
}

function terminateSync() {
	callService('/person/terminate-sync');
}

function callService(serviceUrl, username, password) {
	var person = {
		firstName : $("#firstName").val(),
		lastName : $("#lastName").val(),
		active : true
	};

	person = JSON.stringify(person);

	var username = $("#username").val();
	var password = $("#password").val();
	
	console.log("DATA: " + person);
	$.ajax({
		// url : 'https://jboss-fuse-poc0.it.anl.gov:8443/person/terminate',
		url : serviceUrl,
		data : person,
		dataType : 'json',
		contentType : 'application/json',
		type : 'POST',
		async : true,
		headers : {
			"Authorization" : "Basic " + btoa(username + ":" + password)
		},
		success : function(data) {
			alert("Name Submitted to FSW Successfully, response: "
					+ JSON.stringify(data));
		},
		error : function(data) {
			console.log("Error: " + data);
			alert("Something went wrong, check the javascript console for details.");
		}
	});
}