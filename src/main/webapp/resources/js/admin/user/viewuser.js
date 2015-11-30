var limit=5;
var cur_page;
var total_pages=10;

startPage();

function startPage() {
	//alert("start page");
	listData();
	$('#page-selection').bootpag({
	    total: total_pages,
	    //page: 1,
	    maxVisible: 5,
	    leaps: true,
	    firstLastUse: true,
	    first: 'First',
	    last: 'Last',
	    wrapClass: 'pagination',
	    activeClass: 'active',
	    disabledClass: 'disabled',
	    nextClass: 'next',
	    prevClass: 'prev',
	    lastClass: 'last',
	    firstClass: 'first'
	}).on("page", function(event, num){
		pagination(num);
	}); 
}

//pagination
function pagination(num_page){
	$.ajax({
		type : "GET",
		url : domainname + "api/admin/user/list/" + limit+"/"+num_page,
		dataType : 'json',
		data : null,
		success : function(data) {
			displayData(data);
		},
		error : function(data) {
			alert("Unsuccess: " + data.STATUS);
			console.log("ERROR..." + data);
		}
	});
}

function selectChange(){
	limit=$('select[name=selector]').val();
	startPage();
}

function listData(){
	$.ajax({
		type : "GET",
		url : domainname + "api/admin/user/list/" + limit,
		dataType : 'json',
		data : null,
		success : function(data) {
			//alert(data.TOTAL_PAGE);
			total_pages=data.TOTAL_PAGE;
			displayData(data);
		},
		error : function(data) {
			alert("Unsuccess: " + data.STATUS);
			console.log("ERROR..." + data);
		}
	});
}

function displayData(data) {
	var table = "<table class='table table-striped table-condensed table-hover'><thead><tr class='btn-primary'>"
			+ "<th>ID</th><th>Name</th><th>Gender</th>"
			+ "<th>email</th><th>username</th><th>password</th><th>enable</th><th>address</th><th>phone</th>"
			+ "<th>Image</th><th>Action</th></tr></thead>";

	for (var i = 0; i < data.RESPONSE_DATA.length; i++) {
		table += "<tr><td>"
				+ data.RESPONSE_DATA[i].id
				+ "</td><td>"
				+ data.RESPONSE_DATA[i].name
				+ "</td><td>"
				+ data.RESPONSE_DATA[i].gender
				+ "</td><td>"
				+ data.RESPONSE_DATA[i].email
				+ "</td><td>"
				+ data.RESPONSE_DATA[i].username
				+ "</td><td>"
				+data.RESPONSE_DATA[i].password
				+"</td><td>";
			if(data.RESPONSE_DATA[i].enable==true){
				table+="<input type='checkbox' onchange='changeStatus("+data.RESPONSE_DATA[i].id+")' checked />";
			}
			else{
				table+="<input type='checkbox' onchange='changeStatus("+data.RESPONSE_DATA[i].id+")' />";
			}
			table+="</td><td>"
				+ data.RESPONSE_DATA[i].address
				+ "</td><td>"
				+ data.RESPONSE_DATA[i].phone
				+ "</td><td><img class='img-rounded' style='width:50px; height:50px' src='"+domainname+"resources/upload/profile/"
				+data.RESPONSE_DATA[i].image+"' /></td>"
				+ "<td class='col-sm-3'><input type='button' value='Detail' class='btn btn-info'"
				+ "data-toggle='modal' data-target='#myModal' onclick='detailuser("
				+ data.RESPONSE_DATA[i].id
				+ ")' />&nbsp;&nbsp;"
				+ "<input type='button' value='Edit' class='btn btn-warning' onclick='edituser("
				+ data.RESPONSE_DATA[i].id
				+ ")' />&nbsp;&nbsp;"
				+ "<input type='button' value='Delete' class='btn btn-danger' onclick='deleteuser("
				+ data.RESPONSE_DATA[i].id + ")' />" + "</td></tr>";
	}
	table += "</table>";
	$("#show").html(table);
	//${pageContext.request.contextPath}/resources/upload/profile/
}

function detailuser(id) {
	//alert(id);
	var str = "";
	$.ajax({
		type : "GET",
		url : domainname + "api/admin/user/" + id,
		success : function(data) {
			alert("Success detail:" + data.STATUS);
			$("#image").attr(
					"src",
					domainname + "/resources/upload/profile/"
							+ data.RESPONSE_DATA.image);
			str = "Name: " + data.RESPONSE_DATA.name + "<br/>"
					+ "Gender: " + data.RESPONSE_DATA.gender + "<br/>"
					+ "Email: " + data.RESPONSE_DATA.email + "<br/>"
					+ "Username: " + data.RESPONSE_DATA.username
					+ "<br/>" + "Password: "
					+ data.RESPONSE_DATA.password + "<br/>"
					+ "address: " + data.RESPONSE_DATA.address
					+ "<br/>" + "Phone: " + data.RESPONSE_DATA.phone
					+ "<br/>";
			$("#showdetail").html(str);
		},
		error : function(data) {
			alert("Unsuccess:" + data.STATUS);
			console.log("ERROR..." + data);
		}
	});
}

function edituser(id) {
	location.href = domainname + "admin/user/edit/"
			+ id;
}

function deleteuser(id) {
	//alert("delete user.");
	$.ajax({
		type : "DELETE",
		url : domainname + "api/admin/user/" + id,
		success : function(data) {
			alert("Success: " + data.STATUS);
			startPage();
		},
		error : function(data) {
			alert("Unsuccess:" + data.STATUS);
			console.log("ERROR..." + data);
		}
	});
}
 
function changeStatus(id){
	//alert(id);
	$.ajax({
		type : "DELETE",
		url : domainname + "api/admin/user/" + id,
		success : function(data) {
			alert("Success: " + data.STATUS);
			startPage();
		},
		error : function(data) {
			alert("Unsuccess:" + data.STATUS);
			console.log("ERROR..." + data);
		}
	}); 
}
