$(document).ready(function() {
    	$("#submit").click(submit);
    });

    function submit() {
    	callService('/personService/rest/update');
    }

    function callService(serviceUrl, username, password) {
    	alert("The rest call to BPMS has not been implemented. Please follow the directions in README.md");
    }