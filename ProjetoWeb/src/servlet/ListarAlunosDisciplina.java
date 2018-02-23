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

@WebServlet("/ListarAlunosDisciplina")
public class ListarAlunosDisciplina extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private BeanRemote bean;
	private Logger logger = LoggerFactory.getLogger(ListarAlunosDisciplina.class);
    
    public ListarAlunosDisciplina() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int tipoUtilizador;
		try{
			tipoUtilizador = Integer.parseInt(session.getAttribute("login").toString());
		} catch (NullPointerException e){
			tipoUtilizador = -1;
		}
		if (tipoUtilizador != 2){
			logger.trace("[Servlet] Tentativa de acesso não autorizado a recursos protegidos");
			request.getRequestDispatcher("/Login.jsp").forward(request, response);
			return;
		}
		
		String acao = request.getParameter("action");
        if ("Ordem crescente".equals(acao)){
        	logger.trace("[Servlet] Pedido de listagem dos alunos de uma disciplina por ordem crescente recebido");
        	session.setAttribute("listaAlunos", bean.obterTodosAlunosOrdemCrescente(Integer.parseInt((String) session.getAttribute("idDisciplina2"))));
        }
        else if ("Ordem decrescente".equals(acao)){
        	logger.trace("[Servlet] Pedido de listagem dos alunos de uma disciplina por ordem decrescente recebido");
        	session.setAttribute("listaAlunos", bean.obterTodosAlunosOrdemDecrescente(Integer.parseInt((String) session.getAttribute("idDisciplina2"))));
        }
        request.getRequestDispatcher("/MenuProfessorAlunos.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}