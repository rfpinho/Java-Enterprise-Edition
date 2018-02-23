package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(Logout.class);
	
    public Logout() {
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
		if (tipoUtilizador == -1){
			logger.trace("[Servlet] Tentativa de acesso não autorizado a recursos protegidos");
			request.getRequestDispatcher("/Login.jsp").forward(request, response);
			return;
		}
		
		logger.trace("[Servlet] Pedido de logout recebido");
		session.invalidate();
		request.getRequestDispatcher("/Login.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}