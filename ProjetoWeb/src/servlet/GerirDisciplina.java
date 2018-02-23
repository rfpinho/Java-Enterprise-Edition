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
import ejb.DisciplinaDTO;

@WebServlet("/GerirDisciplina")
public class GerirDisciplina extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private BeanRemote bean;
	private Logger logger = LoggerFactory.getLogger(GerirDisciplina.class);
	
    public GerirDisciplina() {
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
		if (tipoUtilizador != 1){
			logger.trace("[Servlet] Tentativa de acesso não autorizado a recursos protegidos");
			request.getRequestDispatcher("/Login.jsp").forward(request, response);
			return;
		}
		
		String acao, idDisciplina, nome, informacoes, nomeProfessor, idAluno;
		boolean sucesso = false;
		DisciplinaDTO disciplina = null;
		idDisciplina = request.getParameter("id");
		idAluno = request.getParameter("idAluno");
		acao = request.getParameter("action");
        nome = request.getParameter("nome");
        informacoes = request.getParameter("informacoes");
        nomeProfessor = request.getParameter("nomeProfessor");
        if ("Registar".equals(acao)){
        	logger.trace("[Servlet] Pedido de registo de nova disciplina recebido");
        	if (!nome.trim().equals("") && !informacoes.trim().equals("") && !nomeProfessor.trim().equals("")){
        		sucesso = bean.registarDisciplina(nome, informacoes, nomeProfessor);
        		if (sucesso == true){
        			request.setAttribute("mensagemSucesso", "A disciplina " + nome + " foi registada");
        		}
        		else{
        			request.setAttribute("mensagemErro", "Não foi possível registar a disciplina " + nome);
        		}
        	}
        	else{
        		request.setAttribute("mensagemErro", "Certifique que preencheu todos os campos corretamente");
        	}
        }
        else if ("Selecionar".equals(acao)){
        	logger.trace("[Servlet] Pedido de selecionar disciplina recebido");
        	if (idDisciplina.trim().equals("") == false) {
        		disciplina = bean.obterDisciplina(Integer.parseInt(idDisciplina));
        		if (disciplina != null){
        			session.setAttribute("idDisciplina", idDisciplina);
            		session.setAttribute("listaAlunosDisciplina", bean.obterTodosAlunosOrdemCrescente(Integer.parseInt(idDisciplina)));
        		}
        		else{
        			request.setAttribute("mensagemErro", "Id da disciplina inválido");
        		}
        	}
        	else{
        		request.setAttribute("mensagemErro", "Id da disciplina não introduzido");
        		session.setAttribute("listaAlunosDisciplina", null);
            }
        }
        else if ("Editar".equals(acao)){
        	logger.trace("[Servlet] Pedido de edição do perfil de disciplina recebido");
        	if (!idDisciplina.trim().equals("") && !nome.trim().equals("") && !informacoes.trim().equals("")){
           		sucesso = bean.editarDisciplina(Integer.parseInt(idDisciplina), nome, informacoes, nomeProfessor);
           		if (sucesso == true){
           			request.setAttribute("mensagemSucesso", "A disciplina " + nome + " foi editada");
           		}
           		else{
            		request.setAttribute("mensagemErro", "Não foi possível editar a disciplina " + nome);
            	}
        	}
        	else{
        		request.setAttribute("mensagemErro", "Certifique que preencheu todos os campos corretamente (professor é opcional)");
        	}
        }
		else if ("Remover".equals(acao)){
			logger.trace("[Servlet] Pedido de remoção de disciplina recebido");
			if (idDisciplina.trim().equals("") == false) {
        		sucesso = bean.removerDisciplina(Integer.parseInt(idDisciplina));
        		if (sucesso == true){
        			request.setAttribute("mensagemSucesso", "A disciplina foi eliminada");
        		}
        		else{
        			request.setAttribute("mensagemErro", "Não foi possível eliminar a disciplina");
        		}
    		}
        	else{
        		request.setAttribute("mensagemErro", "Id da disciplina não introduzido");
            }
		}
		else if ("Inscrever".equals(acao)){
			logger.trace("[Servlet] Pedido de inscrição de um aluno numa disciplina recebido");
			idDisciplina = (String) session.getAttribute("idDisciplina");
        	sucesso = bean.inscreverAluno(Integer.parseInt(idDisciplina), Integer.parseInt(idAluno));
        	if (sucesso == true){
       			request.setAttribute("mensagemSucesso", "O aluno foi inscrito na disciplina");
       		}
       		else{
       			request.setAttribute("mensagemErro", "Não foi possível inscrever o aluno na disciplina");
       		}
        	disciplina = bean.obterDisciplina(Integer.parseInt(idDisciplina));
        	session.setAttribute("listaAlunosDisciplina", bean.obterTodosAlunosOrdemCrescente(Integer.parseInt(idDisciplina)));
		}
		else if ("Desinscrever".equals(acao)){
			logger.trace("[Servlet] Pedido de desinscrição de um aluno numa disciplina recebido");
			idDisciplina = (String) session.getAttribute("idDisciplina");
        	sucesso = bean.desinscreverAluno(Integer.parseInt(idDisciplina), Integer.parseInt(idAluno));
        	if (sucesso == true){
       			request.setAttribute("mensagemSucesso", "O aluno foi desinscrito da disciplina");
       		}
       		else{
       			request.setAttribute("mensagemErro", "Não foi possível desinscrever o aluno da disciplina");
       		}
        	disciplina = bean.obterDisciplina(Integer.parseInt(idDisciplina));
        	session.setAttribute("listaAlunosDisciplina", bean.obterTodosAlunosOrdemCrescente(Integer.parseInt(idDisciplina)));
		}
        session.setAttribute("disciplina", disciplina);
        session.setAttribute("listaAlunos", bean.obterTodosAlunos());
        session.setAttribute("listaProfessores", bean.obterTodosProfessores());
        session.setAttribute("listaDisciplinas", bean.obterTodasDisciplinas());
        request.getRequestDispatcher("/MenuAdminDisciplinas.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
    public boolean validarListaIds(String strListaIds) {
        int i;
        String[] listaIds = (strListaIds.trim()).split(",");
        for (i = 0; i < listaIds.length; i = i + 1){
            try{
                Integer.parseInt(listaIds[i]);
            } catch (NumberFormatException e){
                return false;
            }
        }
        return true;
    }
}