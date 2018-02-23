<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
int tipoUtilizador;
try{
	tipoUtilizador = Integer.parseInt(session.getAttribute("login").toString());
} catch (NullPointerException e){
	tipoUtilizador = -1;
}
if (tipoUtilizador == 3){
%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Menu Aluno</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>

<body>
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand">Menu Aluno</a>
		    </div>
		    <ul class="nav navbar-nav">
			    <li class="active"><a href="MenuAluno.jsp"> Home</a></li>
		    </ul>
		    <ul class="nav navbar-nav navbar-right">
		    	<li><a href="Logout"><span class="glyphicon glyphicon-log-in"></span> Logout</a></li>
		    </ul>
		</div>
	</nav>
	
	<!-- Mensagens de sucesso/erro -->
	<div class="container">
		<c:if test="${not empty mensagemSucesso}">
			<div class="alert alert-success">
				<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
				<strong>Sucesso!</strong> ${mensagemSucesso}
			</div>
		</c:if>
		<c:if test="${not empty mensagemErro}">
			<div class="alert alert-danger">
				<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
				<strong>Erro!</strong> ${mensagemErro}
			</div>
		</c:if>
	</div>
  	
    <!-- Tabela com os materiais das disciplinas do aluno -->
	<div class="container">
		<h2>Listagem dos materiais disponíveis</h2>
		<table class="table table-striped" border="2" cellpadding="5">
			<th>Id</th>
			<th>Nome disciplina</th>
			<th>Nome ficheiro</th>
			<th>Diretoria</th>
			
			<c:forEach items="${infoMenuAluno}" var="material">
	 			<tr>
					<td>${material.id}</td>
					<td>${material.disciplina.nome}</td>
					<td>${material.nomeFicheiro}</td>
					<td>${material.diretoria}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<br>
	
	<!-- Download do material -->
	<div class="container">
		<form class="form-horizontal" action="DownloadMaterial" method="post">
			<div class="form-group">
				<div class="col-sm-4">
					<input type="number" name="id" value="" class="form-control" placeholder="Introduza o id do material" required>
				</div>
				<input type="submit" class="btn btn-success" name="action" value="Download Material"/>
	    	</div>
		</form>
	</div>
</body>
</html>

<%
}
else{
	request.getRequestDispatcher("/Login.jsp").forward(request, response);
}
%>