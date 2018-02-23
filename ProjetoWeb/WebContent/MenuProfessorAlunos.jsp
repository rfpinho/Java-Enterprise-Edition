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
	
	<!-- Tabela com a lista de disciplinas -->
	<div class="container">
		<h2>Listagem das disciplinas</h2>
		<table class="table table-striped" border="2" cellpadding="5" style="width:50%">
			<th>Id</th>
			<th>Nome</th>
			
			<c:forEach items="${listaDisciplinas}" var="disciplina">
	 			<tr>
					<td>${disciplina.id}</td>
					<td>${disciplina.nome}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	
	<!-- Selecionar disciplina -->
	<div class="container">
		<form class="form-horizontal" action="SelecionarDisciplinaListagem" method="post">
			<div class="form-group">
				<div class="col-sm-4">
					<input type="number" name="id" class="form-control" placeholder="Introduza o id da disciplina" required>
				</div>
				<div class="col-sm-4">
					<button class="btn btn-default" type="submit"> Selecionar disciplina</button>
				</div>
			</div>
		</form>
	</div>
	<br>
	
	<!-- Verificar se o professor leciona a disciplina selecionada -->
	<c:if test="${not empty idDisciplina2}">
	
		<!-- Tabela com a lista dos alunos -->
		<div class="container">
			<h2>Listagem dos alunos da disciplina ${nomeDisciplina2}</h2>
			<table class="table table-striped" border="2" cellpadding="5">
				<th>Id</th>
				<th>Nome</th>
				<th>Data nascimento</th>
				<th>Email institucional</th>
				<th>Email alternativo</th>
				<th>Morada</th>
				<th>Telefone</th>
				<th>Ano entrada curso</th>
				
				<c:forEach items="${listaAlunos}" var="aluno">
		 			<tr>
						<td>${aluno.id}</td>
						<td>${aluno.nome}</td>
						<td>${aluno.dataNascimento}</td>
						<td>${aluno.emailInstitucional}</td>
						<td>${aluno.emailAlternativo}</td>
						<td>${aluno.morada}</td>
						<td>${aluno.telefone}</td>
						<td>${aluno.anoEntradaCurso}</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		
		<!-- Listagem dos alunos por ordem crescente e decrescente -->
		<div class="container">
			<form class="form-horizontal" action="ListarAlunosDisciplina" method="post">
				<div class="container">
					<input type="submit" class="btn btn-primary" name="action" value="Ordem crescente"/>
					<input type="submit" class="btn btn-danger" name="action" value="Ordem decrescente"/>
				</div>
			</form>
		</div>
	</c:if>
</body>
</html>

<%
}
else{
	request.getRequestDispatcher("/Login.jsp").forward(request, response);
}
%>