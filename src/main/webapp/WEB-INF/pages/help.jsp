<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="mvc"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="author" content="Chhorn Elit">
<meta lang="en">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- CSS Library -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/lib/bootstrap.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/lib/font-awesome-4.3.0/css/font-awesome.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/lib/bootstrap-select.min.css">
<!-- End CSS Library -->

<!-- Custom StyleSheet -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/index.css">
<!-- End Custom StyleSheet -->
</head>
<body>
	<div id="main" class="container-fluid">
		<div class="col-sm-12 center-block">
			<div class="col-sm-12 card form-horizontal">
				<h1>Webpage</h1>
				<hr>
				<div class="table-responsive">

					<table
						class="table table-striped table-bordered table-condensed table-hover">
						<caption>http://localhost:8080/ArticleManagement/**</caption>
						<thead>
							<tr class="info">
								<th>relative_path</th>
								<th>http_verb</th>
								<th>example</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>/</td>
								<td>GET</td>
								<td><a href="${pageContext.request.contextPath}/">example</a></td>
							</tr>
							<tr>
								<td>/login</td>
								<td>GET</td>
								<td><a href="login">example</a></td>
							</tr>
							<tr>
								<td>/login</td>
								<td>POST</td>
								<td>formdata username password</td>
							</tr>
							<tr>
								<td>/logout</td>
								<td>GET</td>
								<td><a href="logout">example</a></td>
							</tr>
							<tr>
								<td>/help</td>
								<td>GET</td>
								<td><a href="help">example</a></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>

			<div class="col-sm-12 card form-horizontal">
				<h1>Login Web API</h1>
				<hr>
				<div class="table-responsive">

					<table
						class="table table-striped table-bordered table-condensed table-hover">
						<caption>http://localhost:8080/ArticleManagement/**</caption>
						<thead>
							<tr class="info">
								<th>relative_path</th>
								<th>http_verb</th>
								<th>example</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>/api/login</td>
								<td>POST</td>
								<td>{"username":"author","password":"1"}</td>
							</tr>
							<tr>
								<td>/api/autologin/admin</td>
								<td>GET</td>
								<td><a href="api/autologin/admin">example1</a></td>
							</tr>
							<tr>
								<td>/api/autologin/author</td>
								<td>GET</td>
								<td><a href="api/autologin/author">example1</a></td>
							</tr>
							<tr>
								<td>/api/logout</td>
								<td>GET</td>
								<td><a href="api/logout">example1</a></td>
							</tr>
							<tr>
								<td>/api/status</td>
								<td>GET</td>
								<td><a href="api/status">example1</a></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>

			<div class="col-sm-12 card form-horizontal">
				<h1>Admin Article Web API</h1>
				<hr>
				<div class="table-responsive">

					<table
						class="table table-striped table-bordered table-condensed table-hover">
						<caption>http://localhost:8080/ArticleManagement/api/admin/article/**</caption>
						<thead>
							<tr class="info">
								<th>relative_path</th>
								<th>http_verb</th>
								<th>example</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>/list/{limit}</td>
								<td>GET</td>
								<td><a href="api/admin/article/list/10">example1</a> <a
									href="api/admin/article/list/20">example2</a></td>
							</tr>
							<tr>
								<td>/list/{limit}/{page}</td>
								<td>GET</td>
								<td><a href="api/admin/article/list/10/1">example1</a> <a
									href="api/admin/article/list/10/2">example2</a></td>
							</tr>
							<tr>
								<td>/add</td>
								<td>POST</td>
								<td>{ "title": "The Pr", "userid": 2, "name": "author00",
									"content": "The Project Gutenberg EBook of War and Peace, by
									Leo Tolstoy", "pdate": "2015-11-17", "enable": true, "image":
									"some image" }</td>
							</tr>

							<tr>
								<td>/delete/{id}</td>
								<td>DELETE</td>
								<td></td>
							</tr>
							<tr>
								<td>/update</td>
								<td>PUT</td>
								<td>{ "id":"1", "title": "The Pr", "userid": 2, "name":
									"author00", "content": "The Project Gutenberg EBook of War and
									Peace, by Leo Tolstoy", "pdate": "2015-11-17", "enable": true,
									"image": "some image" }</td>
							</tr>
							<tr>
								<td>/search/{type}/{keyword}/{limit}/{page}</td>
								<td>GET</td>
								<td><a href="api/admin/article/search/title/a/5/1">example1</a>
									<a href="api/admin/article/search/title/a/5/2">example2</a></td>
							</tr>
							<tr>
								<td>/search/{type}/{keyword}/{limit}/</td>
								<td>GET</td>
								<td><a href="api/admin/article/search/title/a/5">example1</a>
									<a href="api/admin/article/search/title/a/10">example2</a></td>
							</tr>
							<tr>
								<td>/get/{id}</td>
								<td>GET</td>
								<td><a href="api/admin/article/get/1">example1</a> <a
									href="api/admin/article/get/2">example2</a></td>
							</tr>
							<tr>
								<td>/toggle/{id}</td>
								<td>GET</td>
								<td><a href="api/admin/article/toggle/1">example1</a> <a
									href="api/admin/article/toggle/2">example2</a></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>

			<div class="col-sm-12 card form-horizontal">
				<h1>Admin User Web API</h1>
				<hr>
				<div class="table-responsive">

					<table
						class="table table-striped table-bordered table-condensed table-hover">
						<caption>http://localhost:8080/ArticleManagement/api/admin/user/**</caption>
						<thead>
							<tr class="info">
								<th>relative_path</th>
								<th>http_verb</th>
								<th>example</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>/list/{limit}</td>
								<td>GET</td>
								<td><a href="api/admin/user/list/5">example1</a> <a
									href="api/admin/user/list/10">example2</a></td>
							</tr>
							<tr>
								<td>/list/{limit}/{page}</td>
								<td>GET</td>
								<td><a href="api/admin/user/list/5/1">example1</a> <a
									href="api/admin/user/list/5/2">example2</a></td>
							</tr>
							<tr>
								<td>/add</td>
								<td>POST</td>
								<td>{ "username": "author", "password": "1", "enable":
									true, "email": "author@gmail.com", "address": "sr", "phone":
									"2322", "name": "author00", "gender": "f", "image":
									"default.jpg" }</td>
							</tr>

							<tr>
								<td>/delete/{id}</td>
								<td>DELETE</td>
								<td></td>
							</tr>
							<tr>
								<td>/update</td>
								<td>PUT</td>
								<td>{ "id": 2, "username": "author", "password": "1",
									"enable": true, "email": "author@gmail.com", "address": "sr",
									"phone": "2322", "name": "author00", "gender": "f", "image":
									"default.jpg" }</td>
							</tr>
							<tr>
								<td>/search/{type}/{keyword}/{limit}/{page}</td>
								<td>GET</td>
								<td><a href="api/admin/user/search/username/admin/5/1">example1</a>
									<a href="api/admin/user/search/username/admin/5/2">example2</a></td>
							</tr>
							<tr>
								<td>/search/{type}/{keyword}/{limit}/</td>
								<td>GET</td>
								<td><a href="api/admin/user/search/username/admin/2">example1</a>
									<a href="api/admin/user/search/username/admin/5">example2</a></td>
							</tr>
							<tr>
								<td>/get/{id}</td>
								<td>GET</td>
								<td><a href="api/admin/user/get/1">example1</a> <a
									href="api/admin/user/get/2">example2</a></td>
							</tr>
							<tr>
								<td>/toggle/{id}</td>
								<td>GET</td>
								<td><a href="api/admin/user/toggle/1">example1</a> <a
									href="api/admin/user/toggle/2">example2</a></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>