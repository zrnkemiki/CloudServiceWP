function printNav(page) {
	var loggedType = "";
	$
			.ajax({
				type : "POST",
				url : "rest/user/logged",
				dataType : "json",
				success : function(logged) {
					$("#log-bar").empty();
					$("#myNavbarList").empty();
					var navItems = "";
					var logbar = '<li><a onclick="logout()" id="logout-btn"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>';
					$("#log-bar").append(logbar);
					loggedType = logged.userType;
					if (loggedType == "SUPERADMIN") {
						if (page == "index.html" || page == "user-edit.html"
								|| page == "users-view.html"
								|| page == "organizations-view.html"
								|| page == "vms-view.html") {
							navItems += '<li><a href="my-profile.html">My Profile</a></li>';
							navItems += '<li><a href="users-view.html">View users</a></li>';
							navItems += '<li><a href="organizations-view.html">View organizations</a></li>';
							navItems += '<li><a href="vms-view.html">View Virtual machines</a></li>';
							navItems += '<li><a href="disks-view.html">View Disks</a></li>';
							navItems += '<li><a href="categories-view.html">View Categories</a></li>';
							$("#myNavbarList").append(navItems);
						}
					} else if (loggedType == "ADMIN") {
						if (page == "index.html" || page == "user-edit.html"
								|| page == "users-view.html"
								|| page == "organizations-view.html"
								|| page == "vms-view.html") {
							navItems += '<li><a href="my-profile.html">My Profile</a></li>';
							navItems += '<li><a href="users-view.html">View users</a></li>';
							navItems += '<li><a href="organization-edit.html?id='
									+ logged.organization.id
									+ '">My organization</a></li>';
							navItems += '<li><a href="vms-view.html">View Virtual machines</a></li>';
							navItems += '<li><a href="disks-view.html">View Disks</a></li>';
							navItems += '<li><a href="categories-view.html">View Categories</a></li>';
							$("#myNavbarList").append(navItems);
						}
					} else if (loggedType == "USER") {
						if (page == "index.html" || page == "my-profile.html"
								|| page == "vms-view.html") {
							navItems += '<li><a href="my-profile.html">My Profile</a></li>';
							navItems += '<li><a href="vms-view.html">View Virtual machines</a></li>';
							$("#myNavbarList").append(navItems);
						}
					}
				},
				error : function(response, xhr) {
					var logbar = '<li><a href="authentication.html"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>';
					$("#log-bar").append(logbar);
				}
			})
};

function logout() {
	console.log("logout");
	$.ajax({
		type : "POST",
		url : "rest/logout",
		success : function(response) {
			window.location.replace("index.html");
		},
		error : function() {
			alert("Something went wrong during logout....");
		}
	});
}

function restrict() {
	var page = getLocation();
	$
			.ajax({
				type : "POST",
				url : "rest/user/logged",
				async : false,
				dataType : "json",
				success : function(logged) {
					loggedType = logged.userType;
					if (loggedType == "ADMIN") {
						if (page == "add-organization.html"
								|| page == "categories-view.html"
								|| page == "add-category.html") {
							alert("Unauthorized! Please log in as super - administrator to access this page!");
							window.location.replace("index.html");
						}
					} else if (loggedType == "USER") {
						if (page == "add-organization.html"
								|| page == "categories-view.html"
								|| page == "add-user.html"
								|| page == "organizations-view.html"
								|| page == "add-disk.html"
								|| page == "categories-view.html"
								|| page == "add-category.html") {
							alert("Unauthorized! Please log in as admin / super admin to access this page!");
							window.location.replace("authentication.html");
						}
					} else if (loggedType == null) {
						alert("Unauthorized! Please log in access this page!");
						window.location.replace("authentication.html");
					}

				},
				error : function(response) {
					alert("Unauthorized! Please log in access this page!");
					window.location.replace("authentication.html");
				}

			})
}

function getLocation() {
	var path = window.location.pathname;
	var page = path.split("/")[2];
	return page;
}

function searchAndFilter() {
	var input, filter, table, tr, td, i, txtValue;
	input = document.getElementById("search-box");
	filter = input.value.toUpperCase();
	table = document.getElementById("main-table");
	tr = table.getElementsByTagName("tr");
	for (i = 0; i < tr.length; i++) {

		td = tr[i].getElementsByTagName("td")[0];
		if (td) {
			txtValue = td.textContent || td.innerText;
			if (txtValue.toUpperCase().indexOf(filter) > -1) {
				tr[i].style.display = "";
			} else {
				tr[i].style.display = "none";
			}
		}
	}

	var min_core_filter, max_core_filter, min_ram_filter, max_ram_filter, min_gpu_filter, max_gpu_filter;
	var table, tr, td_core, td_ram, td_gpu, i;
	min_core_filter = document.getElementById("min-cores").value;
	max_core_filter = document.getElementById("max-cores").value;
	min_ram_filter = document.getElementById("min-ram").value;
	max_ram_filter = document.getElementById("max-ram").value;
	min_gpu_filter = document.getElementById("min-gpu").value;
	max_gpu_filter = document.getElementById("max-gpu").value;

	tr = table.getElementsByTagName("tr");
	for (i = 1; i < tr.length; i++) {

		if (tr[i].style.display == "none") {
			continue;
		}

		td_c = tr[i].getElementsByTagName("td")[2];
		td_core = td_c.textContent || td_c.innerText;
		td_core = Number(td_core);
		td_r = tr[i].getElementsByTagName("td")[3];
		td_ram = td_r.textContent || td_r.innerText;
		td_ram = Number(td_ram);
		td_g = tr[i].getElementsByTagName("td")[4];
		td_gpu = td_g.textContent || td_g.innerText;
		td_gpu = Number(td_gpu);

		// nema filtera
		if (min_core_filter == "" && max_core_filter == ""
				&& min_ram_filter == "" && max_ram_filter == ""
				&& min_gpu_filter == "" && max_gpu_filter == "") {
			tr[i].style.display = "";
			return;
		}

		if (min_core_filter != "" || max_core_filter != "") {
			// postavljen samo MAX CORE filter
			if (min_core_filter == "") {
				if (td_core <= Number(max_core_filter)) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
				// postavljen samo MIN CORE filter
			} else if (max_core_filter == "") {
				if (td_core >= Number(min_core_filter)) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
				// postavljeni i MIN i MAX
			} else {
				if (td_core >= Number(min_core_filter)
						&& td_core <= Number(max_core_filter)) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
			}
		}

		if (min_ram_filter != "" || max_ram_filter != "") {
			// postavljen samo MAX RAM filter
			if (min_ram_filter == "") {
				if (td_ram <= Number(max_ram_filter)) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
				// postavljen samo MIN RAM filter
			} else if (max_ram_filter == "") {
				if (td_ram >= Number(min_ram_filter)) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
				// postavljena oba filtera
			} else {
				if (td_ram >= Number(min_ram_filter)
						&& td_ram <= Number(max_ram_filter)) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
			}
		}

		if (min_gpu_filter != "" || max_gpu_filter != "") {
			// postavljen samo MAX GPU filter
			if (min_gpu_filter == "") {
				if (td_gpu <= Number(max_gpu_filter)) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
				// postavljen samo MIN GPU filter
			} else if (max_gpu_filter == "") {
				if (td_gpu >= Number(min_gpu_filter)) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
				// postavljena oba filtera
			} else {
				if (td_gpu >= Number(min_gpu_filter)
						&& td_gpu <= Number(max_gpu_filter)) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
			}
		}

	}

}

function search() {
	var input, filter, table, tr, td, i, txtValue;
	input = document.getElementById("search-box");
	filter = input.value.toUpperCase();
	table = document.getElementById("main-table");
	tr = table.getElementsByTagName("tr");
	for (i = 0; i < tr.length; i++) {

		td = tr[i].getElementsByTagName("td")[0];
		if (td) {
			txtValue = td.textContent || td.innerText;
			if (txtValue.toUpperCase().indexOf(filter) > -1) {
				tr[i].style.display = "";
			} else {
				tr[i].style.display = "none";
			}
		}
	}
}

function filter() {
	var min_core_filter, max_core_filter, min_ram_filter, max_ram_filter, min_gpu_filter, max_gpu_filter;
	var table, tr, td_core, td_ram, td_gpu, i;
	min_core_filter = document.getElementById("min-cores").value;
	max_core_filter = document.getElementById("max-cores").value;
	min_ram_filter = document.getElementById("min-ram").value;
	max_ram_filter = document.getElementById("max-ram").value;
	min_gpu_filter = document.getElementById("min-gpu").value;
	max_gpu_filter = document.getElementById("max-gpu").value;
	table = document.getElementById("main-table");
	tr = table.getElementsByTagName("tr");
	for (i = 1; i < tr.length; i++) {

		td_c = tr[i].getElementsByTagName("td")[2];
		td_core = td_c.textContent || td_c.innerText;
		td_core = Number(td_core);
		td_r = tr[i].getElementsByTagName("td")[3];
		td_ram = td_r.textContent || td_r.innerText;
		td_ram = Number(td_ram);
		td_g = tr[i].getElementsByTagName("td")[4];
		td_gpu = td_g.textContent || td_g.innerText;
		td_gpu = Number(td_gpu);

		// nema filtera
		if (min_core_filter == "" && max_core_filter == ""
				&& min_ram_filter == "" && max_ram_filter == ""
				&& min_gpu_filter == "" && max_gpu_filter == "") {
			tr[i].style.display = "";
			return;
		}

		if (min_core_filter != "" || max_core_filter != "") {
			// postavljen samo MAX CORE filter
			if (min_core_filter == "") {
				if (td_core <= Number(max_core_filter)) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
				// postavljen samo MIN CORE filter
			} else if (max_core_filter == "") {
				if (td_core >= Number(min_core_filter)) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
				// postavljeni i MIN i MAX
			} else {
				if (td_core >= Number(min_core_filter)
						&& td_core <= Number(max_core_filter)) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
			}
		}

		if (min_ram_filter != "" || max_ram_filter != "") {
			// postavljen samo MAX RAM filter
			if (min_ram_filter == "") {
				if (td_ram <= Number(max_ram_filter)) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
				// postavljen samo MIN RAM filter
			} else if (max_ram_filter == "") {
				if (td_ram >= Number(min_ram_filter)) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
				// postavljena oba filtera
			} else {
				if (td_ram >= Number(min_ram_filter)
						&& td_ram <= Number(max_ram_filter)) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
			}
		}

		if (min_gpu_filter != "" || max_gpu_filter != "") {
			// postavljen samo MAX GPU filter
			if (min_gpu_filter == "") {
				if (td_gpu <= Number(max_gpu_filter)) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
				// postavljen samo MIN GPU filter
			} else if (max_gpu_filter == "") {
				if (td_gpu >= Number(min_gpu_filter)) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
				// postavljena oba filtera
			} else {
				if (td_gpu >= Number(min_gpu_filter)
						&& td_gpu <= Number(max_gpu_filter)) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
			}
		}

	}

}
