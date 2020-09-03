function printNav(page) {
	var loggedType = "";
	$.ajax({
		type: "GET",
		url: "rest/users/getLoggedUser",
		dataType: "json",
		success: function(logged) {
			$("#log-bar").empty();
			$("#myNavbarList").empty();
			var navItems = "";
			var logbar = '<li><a onclick="logout()" id="logout-btn"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>';
			$("#log-bar").append(logbar);
			loggedType = logged.userType;
			if (loggedType == "ADMIN") {
				if (page == "index.html" || page == "emergency.html") {
					navItems += '<li><a href="report.html">Report</a></li>';
				    navItems += '<li><a href="adminProfile.html">Profile</a></li>';
				    navItems += '<li><a href="activateVolunteer.html">Activate Volunteer</a></li>';
				    navItems += '<li><a href="blockVolunteer.html">Block Volunteer</a></li>';
				    navItems += '<li><a href="archiveEmergency.html">Archive Emergency</a></li>';
				    navItems += '<li><a href="addTerritory.html">Add Territory</a></li>';
				    navItems += '<li><a href="deleteTerritory.html">Delete Territory</a></li>';
				    navItems += '<li><a href="editTerritory.html">Edit Territory</a></li>';
					$("#myNavbarList").append(navItems);
					
				} else if (page == "report.html") {
					navItems += '<li class="active"><a href="report.html">Report</a></li>';
				    navItems += '<li><a href="adminProfile.html">Profile</a></li>';
				    navItems += '<li><a href="activateVolunteer.html">Activate Volunteer</a></li>';
				    navItems += '<li><a href="blockVolunteer.html">Block Volunteer</a></li>';
				    navItems += '<li><a href="archiveEmergency.html">Archive Emergency</a></li>';
				    navItems += '<li><a href="addTerritory.html">Add Territory</a></li>';
				    navItems += '<li><a href="deleteTerritory.html">Delete Territory</a></li>';
				    navItems += '<li><a href="editTerritory.html">Edit Territory</a></li>';
				    $("#myNavbarList").append(navItems);
				}
				
			} else if (loggedType == "VOLUNTEER") {
				if (page == "index.html" || page == "emergency.html") {
					navItems += '<li><a href="volunteerProfile.html">Profile</a></li>';
					navItems += '<li><a href="report.html">Report</a></li>';
					navItems += '<li><a href="myEmergencies.html">My Emergencies</a></li>';
					$("#myNavbarList").append(navItems);
				} else if (page == "report.html") {
					navItems += '<li><a href="volunteerProfile.html">Profile</a></li>';
					navItems += '<li class="active"><a href="report.html">Report</a></li>';
					navItems += '<li><a href="myEmergencies.html">My Emergencies</a></li>';
					$("#myNavbarList").append(navItems);
				} else if (page == "myEmergencies.html") {
					navItems += '<li><a href="volunteerProfile.html">Profile</a></li>';
					navItems += '<li><a href="report.html">Report</a></li>';
					navItems += '<li class="active"><a href="myEmergencies.html">My Emergencies</a></li>';
					$("#myNavbarList").append(navItems);
				}
			}
		},
		error: function(response) {
			var logbar = '<li><a href="authentication.html"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>';
			$("#log-bar").append(logbar);
			var navItems = '<li><a href="report.html">Report</a></li>';
			$("#myNavbarList").append(navItems);
		}
	})
}

function logout() {
	$.ajax({
		type: "POST",
		url: "rest/users/logout",
		success: function(response) {
			window.location.replace("index.html");
		},
		error: function() {
			alert("nije uspeo logout");
		}
	});
}

function restrict() {
	var page = getLocation();
	
	$.ajax({
		type: "GET",
		url: "rest/users/getLoggedUser",
		async: false,
		dataType: "json",
		success: function(logged) {
			if (logged.userType == "VOLUNTEER") {
				if (page == "activateVolunteer.html" || page == "addTerritory.html" || page == "adminProfile.html" ||
						page == "archiveEmergency.html" || page == "assignVolunteer.html" || page == "blockVolunteer.html" ||
						page == "deleteTerritory.html" || page == "editTerritory.html") {
					alert("Ulogovani ste kao volonter - NEMATE PRISTUP OVIM STRANICAMA!");
					window.location.replace("index.html");
				}
			} else if (logged.userType == "ADMIN") {
				if (page == "volunteerProfile.html" || page == "myEmergencies.html") {
					alert("Ulogovani ste kao admin - NEMATE PRISTUP OVIM STRANICAMA!");
					window.location.replace("index.html");
				}
			}
		},
		error: function(response) {
			if (page == "activateVolunteer.html" || page == "addTerritory.html" || page == "adminProfile.html" ||
						page == "archiveEmergency.html" || page == "assignVolunteer.html" || page == "blockVolunteer.html" ||
						page == "deleteTerritory.html" || page == "editTerritory.html" || page == "volunteerProfile.html" ||
						page == "myEmergencies.html") {
				alert("Niste ulogovani - NEMATE PRISTUP!");
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

