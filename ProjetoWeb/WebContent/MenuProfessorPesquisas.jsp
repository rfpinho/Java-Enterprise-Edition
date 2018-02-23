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
if (tipoUtilizador == 2){
%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Menu Professor</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>

<body>
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand">Menu Professor</a>
		    </div>
		    <ul class="nav navbar-nav">
			    <li class="active">
			    <li><a href="MenuProfessorDisciplinas.jsp">Disciplinas Materiais</a></li>
			    <li><a href="MenuProfessorAlunos.jsp">Disciplinas Alunos</a></li>
			    <li><a href="MenuProfessorPesquisas.jsp">Pesquisar Aluno</a></li>
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
	
	<!-- Pesquisar aluno por id, nome, email -->
	<div class="container">
		<h2>Pesquisar aluno</h2>
		<form class="form-horizontal" action="PesquisarAluno" method="post">
			<div class="form-group">
				<div class="col-sm-4">
					<input type="number" name="id" class="form-control" placeholder="Introduza o id do aluno">
				</div>
				<input type="submit" class="btn btn-default" name="action" value="Pesquisar por id"/>
	    	</div>
			<div class="form-group">
				<div class="col-sm-4">
					<input type="text" name="nome" class="form-control" placeholder="Introduza o nome do aluno">
				</div>
				<input type="submit" class="btn btn-default" name="action" value="Pesquisar por nome"/>
	    	</div>
	    	<div class="form-group">
				<div class="col-sm-4">
					<input type="text" name="email" class="form-control" placeholder="Introduza o email do aluno">
				</div>
				<input type="submit" class="btn btn-default" name="action" value="Pesquisar por email"/>
	    	</div>
		</form>
	</div>
	<br>
	
	<!-- Informação do aluno pesquisado -->
	<div class="container">
	 	<div class="col-sm-7">
	 		<label for="textarea">Informação do aluno:</label>
	 		<textarea class="form-control" id="textarea" rows="10" disabled="true">${infoAluno}</textarea>
	 	</div>
	</div>
</body>
</html>

<%
}
else{
	request.getRequestDispatcher("/Login.jsp").forward(request, response);
}
%>