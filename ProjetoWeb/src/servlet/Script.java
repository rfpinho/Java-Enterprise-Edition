package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ejb.BeanRemote;

//http://localhost:8080/ProjetoWeb/Script?parametro=1
@WebServlet("/Script")
public class Script extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private BeanRemote bean;
	
    public Script() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int parametro = Integer.parseInt(request.getParameter("parametro"));
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		if (parametro == 1){
			bean.registarAdministrador("Admin", "admin@pt", "admin");
			bean.registarAluno("Aluno1", "2016-11-05", "aluno1@pt", "aluno1", "aluno1@en", "ruaAluno1", "963647123", "2016");
			bean.registarAluno("Aluno2", "2016-11-10", "aluno2@pt", "aluno2", "aluno2@en", "ruaAluno2", "963647321", "2015");
			bean.registarAluno("Aluno3", "2016-11-15", "aluno3@pt", "aluno3", "aluno3@en", "ruaAluno3", "963647987", "2014");
			bean.registarProfessor("Professor1", "2000-01-01", "professor1@pt", "professor1", "professor1@en", "ruaProfessor1", "963647123", "Auxiliar", "A1.1", "234851321", "2500.50");
			bean.registarProfessor("Professor2", "2000-01-02", "professor2@pt", "professor2", "professor2@en", "ruaProfessor2", "963647987", "Regente", "A2.1", "234851789", "2000");
			bean.registarDisciplina("IPRP", "Introdução à programação em python", "Professor1");
			bean.registarDisciplina("PPP", "Introdução à programação em C", "Professor2");
			bean.uploadMaterial("Imagem F1.png", System.getProperty("user.dir") + "\\MateriaisIS\\Imagem F1.png", 7);
			bean.uploadMaterial("Mundial Pilotos.txt", System.getProperty("user.dir") + "\\MateriaisIS\\Mundial Pilotos.txt", 7);
			bean.uploadMaterial("Mundial Construtores.txt", System.getProperty("user.dir") + "\\MateriaisIS\\Mundial Construtores.txt", 7);
			bean.uploadMaterial("F1 Power Unit.pdf", System.getProperty("user.dir") + "\\MateriaisIS\\F1 Power Unit.pdf", 8);
			bean.uploadMaterial("F1 Technical Regulations 2016.pdf", System.getProperty("user.dir") + "\\MateriaisIS\\F1 Technical Regulations 2016.pdf", 8);
			bean.inscreverAluno(7, 2);
			bean.inscreverAluno(7, 3);
			bean.inscreverAluno(7, 4);
			bean.inscreverAluno(8, 2);
			bean.inscreverAluno(8, 4);
			out.println("<h1>Registos inseridos</h1>");
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}