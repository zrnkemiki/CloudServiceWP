function printNav(page) {
	var loggedType = "";
	$.ajax({
		type: "POST",
		url: "rest/user/logged",
		dataType: "json",
		success: function(logged) {
			$("#log-bar").empty();
			$("#myNavbarList").empty();
			var navItems = "";
			var logbar = '<li><a onclick="logout()" id="logout-btn"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>';
			$("#log-bar").append(logbar);
			loggedType = logged.state;
			if (loggedType == "ADMIN") {
				if (page == "index.html" || page == "flight.html") {
					navItems += '<li><a href="admin-profile.html">Profile</a></li>';
				    navItems += '<li><a href="add-destination.html">New Destination</a></li>';
				    navItems += '<li><a href="edit-destination.html">Edit Destination</a></li>';
				    navItems += '<li><a href="archive-destination.html">Archive Destination</a></li>';
				    navItems += '<li><a href="add-flight.html">New Flight</a></li>';
				    navItems += '<li><a href="edit-flight.html">Edit Flight</a></li>';
				    navItems += '<li><a href="delete-flight.html">Delete Flight</a></li>';
				    navItems += '<li><a href="activate-user.html">Activate User</a></li>';
				    navItems += '<li><a href="block-user.html">Block User</a></li>';
					$("#myNavbarList").append(navItems);
				}
			} else if (loggedType == "ACTIVE") {
				if (page == "index.html" || page == "flight.html") {
					navItems += '<li><a href="user-profile.html">My Profile</a></li>';
					navItems += '<li><a href="reservations.html">My Reservations</a></li>';
					$("#myNavbarList").append(navItems);
				} else if (page == "reservations.html") {
					navItems += '<li><a href="user-profile.html">My Profile</a></li>';
					navItems += '<li class="active"><a href="reservations.html">My Reservations</a></li>';
					$("#myNavbarList").append(navItems);
				}
			}
		},
		error: function(response, xhr) {
			var logbar = '<li><a href="authentication.html"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>';
			$("#log-bar").append(logbar);
		}
	})
}

function logout() {
	$.ajax({
		type: "POST",
		url: "rest/logout",
		success: function(response) {
			window.location.replace("index.html");
		},
		error: function() {
			alert("Something went wrong during logout....");
		}
	});
}

function restrict() {
	var page = getLocation();
	$.ajax({
		type: "POST",
		url: "rest/user/logged",
		async: false,
		dataType: "json",
		success: function(logged) {
			if (logged.state != "ADMIN") {
				if (page == "activate-user.html" || page == "add-destination.html" || page == "add-flight.html" ||
						page == "admin-profile.html" || page == "archive-destination.html" || page == "block-user.html" ||
						page == "delete-flight.html" || page == "edit-destination.html" || page == "edit-flight.html") {
					alert("Unauthorized! Please log in as administrator to access this page!");
					window.location.replace("authentication.html");
				}
			} else if (logged.state == "ADMIN") {
				if (page == "user-profile.html" || page == "reservations.html" || page == "flight.html") {
					alert("Unauthorized! Please log in as user to access this page!");
					window.location.replace("authentication.html");
				}
			}
		},
		error: function(response) {
			if (page == "activate-user.html" || page == "add-destination.html" || page == "add-flight.html" ||
						page == "admin-profile.html" || page == "archive-destination.html" || page == "block-user.html" ||
						page == "delete-flight.html" || page == "edit-destination.html" || page == "edit-flight.html" ||
						page == "reservations.html" || page == "user-profile.html") {
				alert("Unauthorized! Please log in to access this page!");
				window.location.replace("authentication.html");
			}
		}
		
	})
}

function getLocation() {
	var path = window.location.pathname;
	var page = path.split("/")[2];
	return page;
}

