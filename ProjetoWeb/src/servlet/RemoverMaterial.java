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

@WebServlet("/RemoverMaterial")
public class RemoverMaterial extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private BeanRemote bean;
	private Logger logger = LoggerFactory.getLogger(RemoverMaterial.class);
	
    public RemoverMaterial() {
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
		
		logger.trace("[Servlet] Pedido de remoção de material recebido");
		String idMaterial, nomeFicheiro;
		idMaterial = request.getParameter("id");
		nomeFicheiro = bean.removerMaterial(Integer.parseInt(idMaterial), Integer.parseInt((String) session.getAttribute("idDisciplina")));
		if (nomeFicheiro != null){
			request.setAttribute("mensagemSucesso", "O material " + nomeFicheiro + " foi removido");
		}
		else{
			request.setAttribute("mensagemErro", "Não foi possível remover o material");
		}
		session.setAttribute("listaAlunos", bean.obterTodosAlunosOrdemCrescente(Integer.parseInt((String) session.getAttribute("idDisciplina"))));
		session.setAttribute("listaMateriais", bean.obterTodosMateriaisDisciplina(Integer.parseInt((String) session.getAttribute("idDisciplina"))));
		request.getRequestDispatcher("/MenuProfessorDisciplinas.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}