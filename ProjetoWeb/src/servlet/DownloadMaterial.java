package servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ejb.BeanRemote;

@WebServlet("/DownloadMaterial")
public class DownloadMaterial extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private BeanRemote bean;
	private Logger logger = LoggerFactory.getLogger(DownloadMaterial.class);
	
	public DownloadMaterial() {
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
		if (tipoUtilizador != 3){
			logger.trace("[Servlet] Tentativa de acesso não autorizado a recursos protegidos");
			request.getRequestDispatcher("/Login.jsp").forward(request, response);
			return;
		}
		
		logger.trace("[Servlet] Pedido de download de material recebido");
		int bytesRead = -1;
		String idMaterial, pathFicheiro, mimeType, headerKey, headerValue;
		byte[] buffer = new byte[4096];
		File downloadFile = null;
		FileInputStream inStream = null;
		ServletContext context = null;
		OutputStream outStream = null;
		idMaterial = request.getParameter("id");
		session.setAttribute("infoMenuAluno", bean.infoAluno((int) session.getAttribute("idAluno")));
		pathFicheiro = bean.verificarMaterialPertenceAluno((int) session.getAttribute("idAluno"), Integer.parseInt(idMaterial));
		if (pathFicheiro != null){
			downloadFile = new File(pathFicheiro);
			inStream = new FileInputStream(downloadFile);
			context = getServletContext();
			mimeType = context.getMimeType(pathFicheiro);
			if (mimeType == null){
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);
			response.setContentLength((int) downloadFile.length());
			headerKey = "Content-Disposition";
			headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
			response.setHeader(headerKey, headerValue);
			outStream = response.getOutputStream();
			while ((bytesRead = inStream.read(buffer)) != -1){
				outStream.write(buffer, 0, bytesRead);
			}
			inStream.close();
			outStream.close();
		}
		else{
			request.setAttribute("mensagemErro", "O id do material selecionado é inválido");
			request.getRequestDispatcher("/MenuAluno.jsp").forward(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}