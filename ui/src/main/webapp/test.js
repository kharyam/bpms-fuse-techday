$(document).ready(function() {
	$("#submit").click(submit);
});

function submit() {
	callService('/personService/rest/update');
}

function callService(serviceUrl, username, password) {
	alert("Call to fuse is not implemented! Please update test.js by following the directions in README.md");
}