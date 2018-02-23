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
import ejb.ProfessorDTO;

@WebServlet("/GerirProfessor")
public class GerirProfessor extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
    private BeanRemote bean;
	private Logger logger = LoggerFactory.getLogger(GerirProfessor.class);
	
    public GerirProfessor() {
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
		
		String acao, idProfessor, nome, dataNascimento, emailInstitucional, password, emailAlternativo, morada, telefone, categoria, gabinete, telefoneInterno, salario;
		boolean sucesso = false;
		ProfessorDTO professor = null;
		idProfessor = request.getParameter("id");
 		acao = request.getParameter("action");
        nome = request.getParameter("nome");
        dataNascimento = request.getParameter("dataNascimento");
        emailInstitucional = request.getParameter("emailInstitucional");
        password = request.getParameter("password");
        emailAlternativo = request.getParameter("emailAlternativo");
        morada = request.getParameter("morada");
        telefone = request.getParameter("telefone");
        categoria = request.getParameter("categoria");
        gabinete = request.getParameter("gabinete");
        telefoneInterno = request.getParameter("telefoneInterno");
        salario = request.getParameter("salario");
        if ("Registar".equals(acao)){
        	logger.trace("[Servlet] Pedido de registo de novo professor recebido");
        	if (!nome.trim().equals("") && !dataNascimento.trim().equals("") && !emailInstitucional.trim().equals("") && !password.trim().equals("") && !emailAlternativo.trim().equals("") && !morada.trim().equals("") && !telefone.trim().equals("") && !categoria.trim().equals("") && !gabinete.trim().equals("") && !telefoneInterno.trim().equals("") && !salario.trim().equals("")){
        		if (bean.verificarEmailProfessorExiste(emailInstitucional) == true){
	        		sucesso = bean.registarProfessor(nome, dataNascimento, emailInstitucional, password, emailAlternativo, morada, telefone, categoria, gabinete, telefoneInterno, salario);
	        		if (sucesso == true){
	        			request.setAttribute("mensagemSucesso", "O professor " + nome + " foi registado");
	        		}
	        		else{
	        			request.setAttribute("mensagemErro", "Não foi possível registar o professor " + nome);
	        		}
        		}
        		else{
        			request.setAttribute("mensagemErro", "O email " + emailInstitucional + " fornecido já existe");
        		}
        	}
        	else{
        		request.setAttribute("mensagemErro", "Certifique que preencheu todos os campos");
        	}
        }
        else if ("Selecionar".equals(acao)){
        	logger.trace("[Servlet] Pedido de selecionar professor recebido");
        	if (idProfessor.trim().equals("") == false){
        		professor = bean.obterProfessor(Integer.parseInt(idProfessor));
        		if (professor == null){
        			request.setAttribute("mensagemErro", "O professor com o id " + idProfessor + " não foi encontrado");
        		}
    		}
        	else{
        		request.setAttribute("mensagemErro", "Id do professor não introduzido");
            }
        }
        else if ("Editar".equals(acao)){
        	logger.trace("[Servlet] Pedido de edição do perfil de professor recebido");
        	if (!idProfessor.trim().equals("") && !nome.trim().equals("") && !dataNascimento.trim().equals("") && !emailInstitucional.trim().equals("") && !emailAlternativo.trim().equals("") && !morada.trim().equals("") && !telefone.trim().equals("") && !categoria.trim().equals("") && !gabinete.trim().equals("") && !telefoneInterno.trim().equals("") && !salario.trim().equals("")){
        		sucesso = bean.editarProfessor(Integer.parseInt(idProfessor), nome, dataNascimento, emailInstitucional, password, emailAlternativo, morada, telefone, categoria, gabinete, telefoneInterno, salario);
        		if (sucesso == true){
        			request.setAttribute("mensagemSucesso", "O professor " + nome + " foi editado");
        		}
        		else{
        			request.setAttribute("mensagemErro", "Não foi possível editar o professor " + nome);
        		}
        	}
        	else{
        		request.setAttribute("mensagemErro", "Certifique que preencheu todos os campos (mudar a password é opcional)");
        	}
        }
        else if ("Remover".equals(acao)){
        	logger.trace("[Servlet] Pedido de remoção de professor recebido");
        	if (idProfessor.trim().equals("") == false){
        		sucesso = bean.removerProfessor(Integer.parseInt(idProfessor));
        		if (sucesso == true){
        			request.setAttribute("mensagemSucesso", "O professor foi eliminado");
        		}
        		else{
        			request.setAttribute("mensagemErro", "Não foi possível eliminar o professor");
        		}
    		}
        	else{
        		request.setAttribute("mensagemErro", "Id do professor não introduzido");
            }
        }
        session.setAttribute("professor", professor);
        session.setAttribute("listaAlunos", bean.obterTodosAlunos());
        session.setAttribute("listaProfessores", bean.obterTodosProfessores());
        session.setAttribute("listaDisciplinas", bean.obterTodasDisciplinas());
        request.getRequestDispatcher("/MenuAdminProfessores.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}