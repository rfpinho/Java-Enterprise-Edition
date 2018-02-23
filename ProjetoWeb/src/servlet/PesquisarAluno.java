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

@WebServlet("/PesquisarAluno")
public class PesquisarAluno extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private BeanRemote bean;
	private Logger logger = LoggerFactory.getLogger(PesquisarAluno.class);
	
    public PesquisarAluno() {
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
		
		String acao, infoAluno = "";
		acao = request.getParameter("action");
		if ("Pesquisar por id".equals(acao)){
			logger.trace("[Servlet] Pedido de pesquisa de aluno por id recebido");
			if ((request.getParameter("id")).trim().equals("") == false) {
				infoAluno = bean.pesquisarAlunoId(Integer.parseInt(request.getParameter("id")));
				if (infoAluno != null){
					request.setAttribute("infoAluno", infoAluno);
				}
				else{
					request.setAttribute("mensagemErro", "Aluno com o id " + request.getParameter("id") + " não foi encontrado");
				}
    		}
        	else{
        		request.setAttribute("mensagemErro", "Id do aluno não introduzido");
            }
        }
        else if ("Pesquisar por nome".equals(acao)){
        	logger.trace("[Servlet] Pedido de pesquisa de aluno por nome recebido");
        	if ((request.getParameter("nome")).trim().equals("") == false){
	        	infoAluno = bean.pesquisarAlunoNome(request.getParameter("nome"));
	        	if (infoAluno != null){
					request.setAttribute("infoAluno", infoAluno);
				}
				else{
					request.setAttribute("mensagemErro", "Aluno com o nome " + request.getParameter("nome") + " não foi encontrado");
				}
        	}
        	else{
        		request.setAttribute("mensagemErro", "Nome do aluno não introduzido");
        	}
        }
        else if ("Pesquisar por email".equals(acao)){
        	logger.trace("[Servlet] Pedido de pesquisa de aluno por email recebido");
        	if ((request.getParameter("email")).trim().equals("") == false){
	        	infoAluno = bean.pesquisarAlunoEmailInstitucional(request.getParameter("email"));
	        	if (infoAluno != null){
					request.setAttribute("infoAluno", infoAluno);
				}
				else{
					request.setAttribute("mensagemErro", "Aluno com o email " + request.getParameter("email") + " não foi encontrado");
				}
        	}
        	else{
        		request.setAttribute("mensagemErro", "Email do aluno não introduzido");
        	}
        }
		request.getRequestDispatcher("/MenuProfessorPesquisas.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}