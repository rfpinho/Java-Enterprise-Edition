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

@WebServlet("/SelecionarDisciplinaMateriais")
public class SelecionarDisciplinaMateriais extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private BeanRemote bean;
	private Logger logger = LoggerFactory.getLogger(SelecionarDisciplinaMateriais.class);
	
    public SelecionarDisciplinaMateriais() {
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
		
		logger.trace("[Servlet] Pedido selecao de disciplina recebido para os materiais");
		String idDisciplina, nomeDisciplina;
		idDisciplina = request.getParameter("id");
		nomeDisciplina = bean.verificarDisciplinaPertenceProfessor((int) session.getAttribute("idProfessor"), Integer.parseInt(idDisciplina));
		if (nomeDisciplina != null){
			session.setAttribute("idDisciplina", idDisciplina);
			session.setAttribute("nomeDisciplina", nomeDisciplina);
			session.setAttribute("listaMateriais", bean.obterTodosMateriaisDisciplina(Integer.parseInt((String) session.getAttribute("idDisciplina"))));
		}
		else{
			session.removeAttribute("idDisciplina");
			session.removeAttribute("nomeDisciplina");
			session.removeAttribute("listaMateriais");
			request.setAttribute("mensagemErro", "Id da disciplina selecionada inválido");
		}
		request.getRequestDispatcher("/MenuProfessorDisciplinas.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}