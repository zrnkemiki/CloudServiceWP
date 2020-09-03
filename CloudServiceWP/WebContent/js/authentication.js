$(document).ready(function() {
	console.log("READY");
	printNav("authentication.html");
})


$("#login-submit").click(function() {
      var credentials = {};
      credentials.email = $("#email-login").val();
      credentials.password = $("#password-login").val();
      
      $.ajax({
          type: "POST",
          url: "rest/authenticate",
          data: JSON.stringify(credentials),
          dataType: "json",
          success: function(loggedUser) {
              alert("Logged in user:  " + loggedUser.email);
              window.location.replace("index.html");
          },
          error: function(response) {
              if (response.status == '403') {
                  alert("Forbidden.");
              } else {
                  alert("Invalid username/password");
              }
              window.location.reload();  
          },
          contentType: "application/json"
      })
  });

