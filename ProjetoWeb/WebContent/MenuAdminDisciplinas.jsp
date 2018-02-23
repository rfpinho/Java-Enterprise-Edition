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
if (tipoUtilizador == 1){
%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Menu Administrador</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>

<body>
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand">Menu Administrador</a>
		    </div>
		    <ul class="nav navbar-nav">
			    <li class="active">
			    <li><a href="MenuAdminAlunos.jsp">Alunos</a></li>
			    <li><a href="MenuAdminProfessores.jsp">Professores</a></li>
			    <li><a href="MenuAdminDisciplinas.jsp">Disciplinas</a></li>
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
  	
	<!-- Registar, selecionar, editar e remover disciplina -->
	<div class="container">
		<h2>Disciplina</h2>
		<form class="form-horizontal" action="GerirDisciplina" method="post">
	    	<div class="form-group">
	      		<label class="control-label col-sm-2">Nome:</label>
	      		<div class="col-sm-4">
	      			<input type="text" name="nome" value="${disciplina.nome}" class="form-control" placeholder="Introduza o nome">
	      		</div>
	    	</div>
	    	<div class="form-group">
	      		<label class="control-label col-sm-2">Informação:</label>
	      		<div class="col-sm-4">
	      			<input type="text" name="informacoes" value="${disciplina.informacoes}" class="form-control" placeholder="Introduza a informação">
	      		</div>
	    	</div>
	    	<div class="form-group">
	      		<label class="control-label col-sm-2">Nome professor:</label>
	      		<div class="col-sm-4">
	      			<input type="text" name="nomeProfessor" value="${disciplina.professor.nome}" class="form-control" placeholder="Introduza o professor">
	      		</div>
	    	</div>
	    	
			<div class="container">
				<input type="submit" class="btn btn-success" name="action" value="Registar"/>
			</div>
			<br><br>
			
			<div class="form-group">
				<div class="col-sm-4">
					<input type="number" name="id" value="${disciplina.id}" class="form-control" placeholder="Introduza o id da disciplina">
				</div>
				<input type="submit" class="btn btn-default" name="action" value="Selecionar"/>
	    	</div>
			<div class="container">
				<input type="submit" class="btn btn-primary" name="action" value="Editar"/>
				<input type="submit" class="btn btn-danger" name="action" value="Remover"/>
			</div>
		</form>
	</div>
	<br>
	
	<!-- Tabela com a lista de disciplinas -->
	<div class="container">
		<h2>Listagem das disciplinas</h2>
		<table class="table table-striped" border="2" cellpadding="5">
			<th>Id</th>
			<th>Nome</th>
			<th>Informações</th>
			<th>Professor</th>
			
			<c:forEach items="${listaDisciplinas}" var="disciplina">
	 			<tr>
					<td>${disciplina.id}</td>
					<td>${disciplina.nome}</td>
					<td>${disciplina.informacoes}</td>
					<td>${disciplina.professor.nome}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<br><br>
	
	<!-- Verificar se o administrador selecionou uma disciplinaa -->
	<c:if test="${not empty disciplina.id}">
		
		<div class="container" align="center">
			<hr style="height:2px; background-color:#333;"/>
	  		<h2>Alunos da disciplina ${disciplina.nome}</h2>
		</div>
	  	<br>
		
		<!-- Inscrever aluno -->
		<div class="container">
			<form class="form-horizontal" action="GerirDisciplina" method="post">
				<div class="form-group">
					<div class="col-sm-4">
						<input type="number" name="idAluno" class="form-control" placeholder="Introduza o id do aluno" required>
					</div>
					<div class="col-sm-4">
						<input type="submit" class="btn btn-success" name="action" value="Inscrever"/>
					</div>
				</div>
			</form>
		</div>
		<br>
		
		<!-- Tabela com os alunos da disciplina selecionada -->
		<div class="container">
			<table class="table table-striped" border="2" cellpadding="5">
				<th>Id</th>
				<th>Nome</th>
				<th>Data nascimento</th>
				<th>Email institucional</th>
				<th>Email alternativo</th>
				<th>Morada</th>
				<th>Telefone</th>
				<th>Ano entrada curso</th>
				
				<c:forEach items="${listaAlunosDisciplina}" var="aluno">
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
		
		<!-- Desinscrever aluno -->
		<div class="container">
			<form class="form-horizontal" action="GerirDisciplina" method="post">
				<div class="form-group">
					<div class="col-sm-4">
						<input type="number" name="idAluno" class="form-control" placeholder="Introduza o id do aluno" required>
					</div>
					<div class="col-sm-4">
						<input type="submit" class="btn btn-danger" name="action" value="Desinscrever"/>
					</div>
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