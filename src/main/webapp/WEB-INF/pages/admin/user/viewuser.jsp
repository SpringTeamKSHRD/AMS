<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View User</title>
<!-- CSS Library -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/lib/bootstrap.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/lib/jasny-bootstrap.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/lib/font-awesome-4.3.0/css/font-awesome.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/lib/sidebar.css" />
<!-- End CSS Library -->

<!-- Custom StyleSheet -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/default.css">
<!-- End Custom StyleSheet -->

</head>
<body>
	<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-2">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" id="menu-toggle"><i class="fa fa-list"></i></a>
			<a class="navbar-brand">Admin Page</a>
		</div>

		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-2">
			<ul class="nav navbar-nav navbar-right">
				<li><a href="${pageContext.request.contextPath}/"><i
						class="fa fa-home fa-lg"></i> Home Page</a></li>
				<c:choose>
					<c:when test="${not login}">
						<li><a href="${pageContext.request.contextPath}/help"><i
						class="fa fa-question-circle fa-lg"></i> Help </a></li>
						<li><a href="${pageContext.request.contextPath}/login"><i
								class="fa fa-sign-in fa-lg"></i> Login </a></li>
					</c:when>
					<c:otherwise>
						<li><a href="#"><i class="fa fa-user fa-lg"></i> Hi,
								${name } </a></li>
						<li><a href="${pageContext.request.contextPath}/help"><i
						class="fa fa-question-circle fa-lg"></i> Help </a></li>
						<li><a href="${pageContext.request.contextPath}/logout"><i
								class="fa fa-sign-out fa-lg"></i> Logout </a></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
	</div>
	</nav>
	<div id="wrapper" class="main">
		<div id="sidebar-wrapper">
			<ul class="sidebar-nav">
				<li><a href="#"><i class="fa fa-user fa-lg"></i> Profile</a>
				<li><a href="#"><i class="fa fa-users fa-lg"></i> User</a>
					<ul>
						<li class="active"><a href="${pageContext.request.contextPath}/admin/user"><i class="fa fa-list fa-lg"></i> List</a></li>
						<li><a href="${pageContext.request.contextPath}/admin/user/add"><i class="fa fa-plus fa-lg"></i> Add</a></li>
						<li><a href="#"><i class="fa fa-search fa-lg"></i> Search</a></li>
					</ul>
				</li>
				<li><a href="#"><i class="fa fa-newspaper-o fa-lg"></i> Article</a>
					<ul>
						<li><a href="#"><i class="fa fa-list fa-lg"></i> List</a></li>
						<li><a href="#"><i class="fa fa-plus fa-lg"></i> Add</a></li>
						<li><a href="#"><i class="fa fa-search fa-lg"></i> Search</a></li>
					</ul>
				</li>
			</ul>
		</div>
		<div id="page-content-wrapper">
			<div class="container-fluid">
				<div class="row col-sm-12 center-block card">
					<h3>Users Information</h3>
					<hr>
					<div>
			   			<select class="form-control" name="selector" onchange="selectChange()" style="width:200px;">
						  <option value="5">5</option>
						  <option value="10">10</option>
						  <option value="25">25</option>
						  <option value="50">50</option>
						  <option value="100">100</option>
					   </select>
				   </div><br/>
				   
					<div id="show"></div>
					<!-- pagination -->
					<div id="content"></div>
		   			<div id="page-selection"></div>
		
				</div>
			</div>
		</div>
	</div>
	<!-- Modal -->
	<div id="myModal" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header"
					style="background-color: #009688; color: white;">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">User Information</h4>
				</div>
				<div>
					<div>
						<img id="image" style="width: 200px; height: 200px;" />
					</div>
					<div id="showdetail" class="modal-body">
						<!-- Some text in the modal. -->
					</div>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	
	

	<!-- Javascript Library -->
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/js/lib/jquery-2.1.4.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/js/lib/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/js/lib/jasny-bootstrap.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/js/lib/bootpag.js"></script>
	<!-- End Javascript Library -->

	<!-- Custom Javascript -->
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/js/default.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/js/admin/user/viewuser.js"></script>
	<!-- End Custom Javascript -->
</body>
</html>