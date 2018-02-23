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
import ejb.AlunoDTO;
import ejb.BeanRemote;

@WebServlet("/GerirAluno")
public class GerirAluno extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private BeanRemote bean;
	private Logger logger = LoggerFactory.getLogger(GerirAluno.class);
	
    public GerirAluno() {
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
		
		String acao, idAluno, nome, dataNascimento, emailInstitucional, password, emailAlternativo, morada, telefone, anoEntradaCurso, idDisciplina;
		boolean sucesso;
		AlunoDTO aluno = null;
		acao = request.getParameter("action");
		idAluno = request.getParameter("id");
        nome = request.getParameter("nome");
        dataNascimento = request.getParameter("dataNascimento");
        emailInstitucional = request.getParameter("emailInstitucional");
        password = request.getParameter("password");
        emailAlternativo = request.getParameter("emailAlternativo");
        morada = request.getParameter("morada");
        telefone = request.getParameter("telefone");
        anoEntradaCurso = request.getParameter("anoEntradaCurso");
        if ("Registar".equals(acao)){
        	logger.trace("[Servlet] Pedido de registo de novo aluno recebido");
        	if (!nome.trim().equals("") && !dataNascimento.trim().equals("") && !emailInstitucional.trim().equals("") && !password.trim().equals("") && !emailAlternativo.trim().equals("") && !morada.trim().equals("") && !telefone.trim().equals("") && !anoEntradaCurso.trim().equals("")){
        		if (bean.verificarEmailAlunoExiste(emailInstitucional) == true){
	        		sucesso = bean.registarAluno(nome, dataNascimento, emailInstitucional, password, emailAlternativo, morada, telefone, anoEntradaCurso);
	        		if (sucesso == true){
	        			request.setAttribute("mensagemSucesso", "O aluno " + nome + " foi registado");
	        		}
	        		else{
	        			request.setAttribute("mensagemErro", "Não foi possível registar o aluno " + nome);
	        		}
        		}
        		else{
        			request.setAttribute("mensagemErro", "O email fornecido " + emailInstitucional + " já existe");
        		}
        	}
        	else{
        		request.setAttribute("mensagemErro", "Certifique que preencheu todos os campos");
        	}
        }
        else if ("Selecionar".equals(acao)){
        	logger.trace("[Servlet] Pedido de selecionar aluno recebido");
        	if (idAluno.trim().equals("") == false) {
        		aluno = bean.obterAluno(Integer.parseInt(idAluno));
        		if (aluno == null){
        			request.setAttribute("mensagemErro", "O aluno com o id " + idAluno + " não foi encontrado");
        		}
    		}
        	else{
        		request.setAttribute("mensagemErro", "Id do aluno não introduzido");
            }
        }
        else if ("Editar".equals(acao)){
        	logger.trace("[Servlet] Pedido de edição do perfil de aluno recebido");
        	if (!idAluno.trim().equals("") && !nome.trim().equals("") && !dataNascimento.trim().equals("") && !emailInstitucional.trim().equals("") && !emailAlternativo.trim().equals("") && !morada.trim().equals("") && !telefone.trim().equals("") && !anoEntradaCurso.trim().equals("")){
        		sucesso = bean.editarAluno(Integer.parseInt(idAluno), nome, dataNascimento, emailInstitucional, password, emailAlternativo, morada, telefone, anoEntradaCurso);
        		if (sucesso == true){
        			request.setAttribute("mensagemSucesso", "O aluno " + nome + " foi editado");
        		}
        		else{
        			request.setAttribute("mensagemErro", "Não foi possível editar o aluno " + nome);
        		}
        	}
        	else{
        		request.setAttribute("mensagemErro", "Certifique que preencheu todos os campos (alterar a password é opcional)");
        	}
        }
        else if ("Remover".equals(acao)){
        	logger.trace("[Servlet] Pedido de remoção de aluno recebido");
        	if (idAluno.trim().equals("") == false){
        		sucesso = bean.removerAluno(Integer.parseInt(idAluno));
        		if (sucesso == true){
        			if (session.getAttribute("idDisciplina") != null){
        				idDisciplina = (String) session.getAttribute("idDisciplina");
        				session.setAttribute("listaAlunosDisciplina", bean.obterTodosAlunosOrdemCrescente(Integer.parseInt(idDisciplina)));
        			}
            		request.setAttribute("mensagemSucesso", "O aluno foi eliminado");
        		}
        		else{
        			request.setAttribute("mensagemErro", "Não foi possível eliminar o aluno");
        		}
    		}
        	else{
        		request.setAttribute("mensagemErro", "Id do aluno não introduzido");
            }
        }
        session.setAttribute("aluno", aluno);
        session.setAttribute("listaAlunos", bean.obterTodosAlunos());
        session.setAttribute("listaProfessores", bean.obterTodosProfessores());
        session.setAttribute("listaDisciplinas", bean.obterTodasDisciplinas());
        request.getRequestDispatcher("/MenuAdminAlunos.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}