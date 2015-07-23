# Person Service UI

A simple ui (single html page and javascript page) that calls the person service (Fuse camel route) with the fields input by the user. It uses jquery to make a restful POST call to the person service.

![UI](images/ui.png)

# Workshop
## Update the UI to call the Person Service

1. In JBoss Developer Studio, open ui -> Deployed Resources -> webapp -> test.js Service. Notice the relative url passed into the callService function is the location of the fuse/camel rest services that was just implemented:

    ```javascript
    callService('/personService/rest/update');
    ```  
2. The callService function currently just displays a message saying that it is not implemented. Delete the call to alert.
3. First we need to construct a person javascript object, matching the structure of our Person object defined in the data model.  The values for the fields are extracted from the html page using jquery. Then we convert this object to a json string and log it to the javascript console. Do this with the following javascript code:

    ```javascript
    var person = {
      firstName : $("#firstName").val(),
      lastName : $("#lastName").val(),
      organization : $("#organization").val(),
      active :  $("#active").val()=="true"
    };

    person = JSON.stringify(person);
    console.log("DATA: " + person);
    ```
4. Next we use the jquery ajax function to make the rest call. Add the folowing javascript code and note the parameters passed into the ajax call:

    ```javascript
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
    ```

The completed javascript file should look as follows:

  ```javascript
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
  ```

You should now be able to test the call to fuse / bpms via the UI.
