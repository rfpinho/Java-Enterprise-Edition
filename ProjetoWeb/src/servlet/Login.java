package servlet;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ejb.BeanRemote;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private BeanRemote bean;
	private Logger logger = LoggerFactory.getLogger(Login.class);
	
	public Login() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.trace("[Servlet] Pedido de login recebido");
		HttpSession session = request.getSession();
		int resultado, id;
		String email, password;
		email = request.getParameter("email");
		password = request.getParameter("password");
		resultado = bean.login(email, password);
		session.setAttribute("login", resultado);
		if (resultado == 1){
	    	session.setAttribute("listaAlunos", bean.obterTodosAlunos());
	        session.setAttribute("listaProfessores", bean.obterTodosProfessores());
	        session.setAttribute("listaDisciplinas", bean.obterTodasDisciplinas());
	    	request.getRequestDispatcher("/MenuAdminAlunos.jsp").forward(request, response);
		}
		else if (resultado == 2){
			id = bean.obterIdProfessor(email);
			session.setAttribute("idProfessor", id);
			session.setAttribute("listaDisciplinas", bean.obterTodasDisciplinasProfessor(id));
			session.setAttribute("idDisciplina", "");
			request.getRequestDispatcher("/MenuProfessorDisciplinas.jsp").forward(request, response);
		}
		else if (resultado == 3){
			id = bean.obterIdAluno(email);
			session.setAttribute("idAluno", id);
			session.setAttribute("infoMenuAluno", bean.infoAluno(id));
			request.getRequestDispatcher("/MenuAluno.jsp").forward(request, response);
		}
		else{
			request.setAttribute("mensagemErro", "Login ou password inválidos");
    		request.getRequestDispatcher("/Login.jsp").forward(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}