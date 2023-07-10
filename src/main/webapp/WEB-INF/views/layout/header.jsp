<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<sec:authorize access="isAuthenticated()">
	<!-- userDetails 에 접근 -->
	<sec:authentication property="principal" var="principal" />
	<!-- principal이란 변수에 세션 정보(principalDetails) 담을거임 -->
</sec:authorize>

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Photogram</title>
<!-- font awesome -->
<script src="https://kit.fontawesome.com/cc6f9eef40.js" crossorigin="anonymous"></script>
<!-- bootstrap -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
<!-- 제이쿼리 -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<!-- 제이쿼리 ui - 친구찾기 -->
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<!-- Style -->
<link rel="stylesheet" href="/css/style.css">
<link rel="stylesheet" href="/css/story.css">
<link rel="stylesheet" href="/css/popular.css">
<link rel="stylesheet" href="/css/profile.css">
<link rel="stylesheet" href="/css/upload.css">
<link rel="stylesheet" href="/css/update.css">
<link rel="shortcut icon" href="/images/insta.svg">
<style type="text/css">
.red{
	color: #ff0000;
}

.subscribe__item {
	cursor: pointer;
}
</style>

<!-- Fontawesome -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />

<!-- Fonts -->
<link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@100;200;300;400;500;600;700&display=swap" rel="stylesheet">
</head>

<body>

	<!-- principalId 담아두는 곳 -->
	<input type="hidden" id="principalId" value="${principal.user.id }" />

	<header class="header">
		<div class="container">
			<a href="/image/story" class="logo"> <img src="/images/logo.jpg" alt="">
			</a>

			<nav class="navi">
				<ul class="navi-list">
					<li class="navi-item">
						<form class="d-flex" role="search" id="searchFriend">
							<input class="form-control me-2" type="search" id="inputSearch" placeholder="친구를 찾아보세요!" aria-label="Search">
						</form>
					</li>
					<li class="navi-item"><a href="/image/story"> <i class="fas fa-home"></i>
					</a></li>
					<li class="navi-item"><a href="/image/popular"> <i class="far fa-compass"></i>
					</a></li>
					<li class="navi-item"><a href="/user/${principal.user.id }"> <i class="far fa-user"></i>
					</a></li>
					<li class="navi-item"><a href="/collection"> <i class="fas fa-box"></i>
					</a></li>
				</ul>
			</nav>
		</div>
		<script src="/js/autocomplete.js"></script>
		<script src="/js/profile.js"></script>
	</header>