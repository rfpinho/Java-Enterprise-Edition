package servlet;

import java.io.File;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ejb.BeanRemote;

@WebServlet("/AdicionarMaterial")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class AdicionarMaterial extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private BeanRemote bean;
	private Logger logger = LoggerFactory.getLogger(AdicionarMaterial.class);
	
    public AdicionarMaterial() {
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
		
		logger.trace("[Servlet] Pedido de upload de material recebido");
		String nomeFicheiro, pathFicheiro;
		boolean sucesso;
		File f = null;
		Part part = null;
		part = request.getPart("file");
		nomeFicheiro = extractFileName(part);
		pathFicheiro = System.getProperty("user.dir") + "\\MateriaisIS\\" + nomeFicheiro;
		f = new File(pathFicheiro);
		if (f.exists()){
			f.delete();
			part.write(pathFicheiro);
			request.setAttribute("mensagemSucesso", "O ficheiro " + nomeFicheiro + " já existe nos materiais da disciplina, no entanto foi atualizado");
		}
		else{
			part.write(pathFicheiro);
			sucesso = bean.uploadMaterial(nomeFicheiro, pathFicheiro, Integer.parseInt((String) session.getAttribute("idDisciplina")));
			if (sucesso == true){
				request.setAttribute("mensagemSucesso", "O material " + nomeFicheiro + " foi adicionado");
			}
			else{
				request.setAttribute("mensagemErro", "Não foi possível adicionar o material " + nomeFicheiro);
			}
		}
        session.setAttribute("listaMateriais", bean.obterTodosMateriaisDisciplina(Integer.parseInt((String) session.getAttribute("idDisciplina"))));
        session.setAttribute("listaAlunos", bean.obterTodosAlunosOrdemCrescente(Integer.parseInt((String) session.getAttribute("idDisciplina"))));
        request.getRequestDispatcher("/MenuProfessorDisciplinas.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private String extractFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items){
			if (s.trim().startsWith("filename")){
				return s.substring(s.indexOf("=") + 2, s.length() - 1);
			}
		}
		return "";
	}
}